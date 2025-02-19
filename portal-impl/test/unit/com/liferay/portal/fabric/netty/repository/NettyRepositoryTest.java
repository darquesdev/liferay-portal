/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.fabric.netty.repository;

import com.liferay.petra.concurrent.AsyncBroker;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.fabric.netty.codec.serialization.AnnotatedObjectDecoder;
import com.liferay.portal.fabric.netty.fileserver.FileHelperUtil;
import com.liferay.portal.fabric.netty.fileserver.FileResponse;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileResponseChannelHandler;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileServerTestUtil;
import com.liferay.portal.fabric.netty.util.NettyUtilAdvice;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class NettyRepositoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@Before
	public void setUp() throws IOException {
		_channelPipeline = _embeddedChannel.pipeline();

		_repositoryPath = Paths.get("repository");

		FileHelperUtil.delete(_repositoryPath);

		FileServerTestUtil.registerForCleanUp(
			Files.createDirectory(_repositoryPath));

		_nettyRepository = new NettyRepository(_repositoryPath, Long.MAX_VALUE);

		_asyncBroker = _nettyRepository.getAsyncBroker();

		_channelPipeline.addLast(
			new FileResponseChannelHandler(
				_asyncBroker, _embeddedChannel.eventLoop()));
	}

	@After
	public void tearDown() {
		FileServerTestUtil.cleanUp();
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testConstructor() {
		try {
			new NettyRepository(null, Long.MAX_VALUE);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
			Assert.assertEquals(
				"Repository path is null", nullPointerException.getMessage());
		}

		try {
			new NettyRepository(Paths.get("Unknown"), Long.MAX_VALUE);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		NettyRepository nettyRepository = new NettyRepository(
			_repositoryPath, Long.MAX_VALUE);

		_channelPipeline.addLast(
			new FileResponseChannelHandler(
				nettyRepository.getAsyncBroker(),
				_embeddedChannel.eventLoop()));

		Assert.assertSame(_repositoryPath, nettyRepository.getRepositoryPath());
		Assert.assertEquals(Long.MAX_VALUE, nettyRepository.getFileTimeout);
		Assert.assertNotNull(nettyRepository.pathMap);

		Assert.assertTrue(
			_annotatedObjectDecoder.removeFirst() instanceof
				FileResponseChannelHandler);
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testDispose() throws Exception {
		Path remoteFilePath = Paths.get("remoteFile");

		Path tempFilePath = FileServerTestUtil.createFileWithData(
			Paths.get("tempFile"));

		Map<Path, Path> pathMap = _nettyRepository.pathMap;

		FileServerTestUtil.createFileWithData(tempFilePath);

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyRepository.class.getName(), Level.OFF)) {

			NoticeableFuture<Path> noticeableFuture = _nettyRepository.getFile(
				_embeddedChannel, remoteFilePath, null, false);

			FileResponse fileResponse = new FileResponse(
				remoteFilePath, System.currentTimeMillis(), 0, false);

			fileResponse.setLocalFile(tempFilePath);

			_asyncBroker.takeWithResult(remoteFilePath, fileResponse);

			Path localFilePath = noticeableFuture.get();

			Assert.assertNotNull(localFilePath);

			Assert.assertTrue(Files.notExists(tempFilePath));
			Assert.assertTrue(Files.exists(localFilePath));
			Assert.assertEquals(pathMap.toString(), 1, pathMap.size());
			Assert.assertSame(localFilePath, pathMap.get(remoteFilePath));

			_nettyRepository.dispose(false);

			Assert.assertTrue(Files.notExists(localFilePath));
			Assert.assertTrue(pathMap.toString(), pathMap.isEmpty());
			Assert.assertTrue(Files.exists(_repositoryPath));

			_nettyRepository.dispose(true);

			Assert.assertTrue(Files.notExists(_repositoryPath));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testGetFile() throws Exception {

		// With log, populate cache

		Path remoteFilePath = Paths.get("remoteFile");

		Path tempFilePath = FileServerTestUtil.createFileWithData(
			Paths.get("tempFile"));

		Map<Path, Path> pathMap = _nettyRepository.pathMap;

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyRepository.class.getName(), Level.FINEST)) {

			NoticeableFuture<Path> noticeableFuture1 = _nettyRepository.getFile(
				_embeddedChannel, remoteFilePath, null, false);

			NoticeableFuture<Path> noticeableFuture2 = _nettyRepository.getFile(
				_embeddedChannel, remoteFilePath, null, false);

			Assert.assertNotSame(noticeableFuture1, noticeableFuture2);

			FileResponse fileResponse = new FileResponse(
				remoteFilePath, System.currentTimeMillis(), 0, false);

			fileResponse.setLocalFile(tempFilePath);

			_asyncBroker.takeWithResult(remoteFilePath, fileResponse);

			Path localFilePath = FileServerTestUtil.registerForCleanUp(
				noticeableFuture1.get());

			Assert.assertSame(localFilePath, noticeableFuture2.get());
			Assert.assertSame(localFilePath, fileResponse.getLocalFile());
			Assert.assertNotNull(localFilePath);

			Assert.assertTrue(Files.notExists(tempFilePath));
			Assert.assertTrue(Files.exists(localFilePath));
			Assert.assertEquals(pathMap.toString(), 1, pathMap.size());
			Assert.assertSame(localFilePath, pathMap.get(remoteFilePath));

			Files.delete(localFilePath);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 4, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Fetching remote file " + remoteFilePath,
				logEntry.getMessage());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				"Fetching remote file " + remoteFilePath,
				logEntry.getMessage());

			logEntry = logEntries.get(2);

			Assert.assertEquals(
				StringBundler.concat(
					"Fetched remote file ", remoteFilePath, " to ",
					localFilePath),
				logEntry.getMessage());

			logEntry = logEntries.get(3);

			Assert.assertEquals(
				StringBundler.concat(
					"Fetched remote file ", remoteFilePath, " to ",
					localFilePath),
				logEntry.getMessage());
		}
		finally {
			pathMap.clear();
		}

		// Without log, not populate cacge

		FileServerTestUtil.createFileWithData(tempFilePath);

		Path localFilePath1 = FileServerTestUtil.registerForCleanUp(
			_repositoryPath.resolve("localFile1"));
		Path localFilePath2 = FileServerTestUtil.registerForCleanUp(
			_repositoryPath.resolve("localFile2"));

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyRepository.class.getName(), Level.OFF)) {

			NoticeableFuture<Path> noticeableFuture1 = _nettyRepository.getFile(
				_embeddedChannel, remoteFilePath, localFilePath1, false);

			NoticeableFuture<Path> noticeableFuture2 = _nettyRepository.getFile(
				_embeddedChannel, remoteFilePath, localFilePath2, false);

			Assert.assertNotSame(noticeableFuture1, noticeableFuture2);

			FileResponse fileResponse = new FileResponse(
				remoteFilePath, System.currentTimeMillis(), 0, false);

			fileResponse.setLocalFile(tempFilePath);

			_asyncBroker.takeWithResult(remoteFilePath, fileResponse);

			Assert.assertSame(localFilePath1, noticeableFuture1.get());
			Assert.assertSame(localFilePath2, noticeableFuture2.get());
			Assert.assertSame(localFilePath2, fileResponse.getLocalFile());
			Assert.assertTrue(Files.notExists(tempFilePath));
			Assert.assertTrue(Files.exists(localFilePath1));
			Assert.assertTrue(Files.exists(localFilePath2));
			Assert.assertTrue(pathMap.toString(), pathMap.isEmpty());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testGetFileChannelCancellation() {
		_channelPipeline.addFirst(
			new ChannelOutboundHandlerAdapter() {

				@Override
				public void write(
					ChannelHandlerContext channelHandlerContext, Object object,
					ChannelPromise channelPromise) {

					channelPromise.cancel(true);
				}

			});

		try {
			NoticeableFuture<Path> noticeableFuture = _nettyRepository.getFile(
				_embeddedChannel, Paths.get("remoteFile"),
				Paths.get("localFile"), false, false);

			Assert.assertTrue(noticeableFuture.isDone());
			Assert.assertTrue(noticeableFuture.isCancelled());
		}
		finally {
			_channelPipeline.removeFirst();
		}
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testGetFileChannelFailure() throws InterruptedException {
		doTestGetFileChannelFailure(false, false);
		doTestGetFileChannelFailure(false, true);
		doTestGetFileChannelFailure(true, false);
		doTestGetFileChannelFailure(true, true);
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testGetFileFileNotFound() throws Exception {

		// With log

		Path remoteFilePath = Paths.get("remoteFile");

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyRepository.class.getName(), Level.WARNING)) {

			NoticeableFuture<Path> noticeableFuture = _nettyRepository.getFile(
				_embeddedChannel, remoteFilePath, Paths.get("localFile"), false,
				false);

			_asyncBroker.takeWithResult(
				remoteFilePath,
				new FileResponse(
					remoteFilePath, FileResponse.FILE_NOT_FOUND, 0, false));

			Assert.assertNull(noticeableFuture.get());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Remote file " + remoteFilePath + " is not found",
				logEntry.getMessage());
		}

		// Without log

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyRepository.class.getName(), Level.OFF)) {

			NoticeableFuture<Path> noticeableFuture = _nettyRepository.getFile(
				_embeddedChannel, remoteFilePath, Paths.get("localFile"), false,
				false);

			_asyncBroker.takeWithResult(
				remoteFilePath,
				new FileResponse(
					remoteFilePath, FileResponse.FILE_NOT_FOUND, 0, false));

			Assert.assertNull(noticeableFuture.get());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testGetFileFileNotModified() throws Exception {

		// With log

		Path remoteFilePath = Paths.get("remoteFile");
		Path cachedLocalFilePath = Paths.get("cacheLocalFile");

		Map<Path, Path> pathMap = _nettyRepository.pathMap;

		pathMap.put(remoteFilePath, cachedLocalFilePath);

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyRepository.class.getName(), Level.FINEST)) {

			NoticeableFuture<Path> noticeableFuture = _nettyRepository.getFile(
				_embeddedChannel, remoteFilePath, Paths.get("localFile"), false,
				false);

			_asyncBroker.takeWithResult(
				remoteFilePath,
				new FileResponse(
					remoteFilePath, FileResponse.FILE_NOT_MODIFIED, 0, false));

			Assert.assertSame(cachedLocalFilePath, noticeableFuture.get());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Fetching remote file " + remoteFilePath,
				logEntry.getMessage());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				StringBundler.concat(
					"Remote file ", remoteFilePath,
					" is not modified, use cached local file ",
					cachedLocalFilePath),
				logEntry.getMessage());
		}

		// Without log

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyRepository.class.getName(), Level.OFF)) {

			NoticeableFuture<Path> noticeableFuture = _nettyRepository.getFile(
				_embeddedChannel, remoteFilePath, Paths.get("localFile"), false,
				false);

			_asyncBroker.takeWithResult(
				remoteFilePath,
				new FileResponse(
					remoteFilePath, FileResponse.FILE_NOT_MODIFIED, 0, false));

			Assert.assertSame(cachedLocalFilePath, noticeableFuture.get());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testGetFiles() throws Exception {
		Path remoteFilePath1 = Paths.get("remoteFile1");
		Path remoteFilePath2 = Paths.get("remoteFile2");
		Path localFilePath = FileServerTestUtil.registerForCleanUp(
			Paths.get("localFile1"));

		NoticeableFuture<Map<Path, Path>> noticeableFuture =
			_nettyRepository.getFiles(
				_embeddedChannel,
				HashMapBuilder.<Path, Path>put(
					remoteFilePath1, localFilePath
				).put(
					remoteFilePath2, Paths.get("localFile2")
				).build(),
				true);

		Path tempFilePath = FileServerTestUtil.createFileWithData(
			Paths.get("tempFile"));

		FileResponse fileResponse1 = new FileResponse(
			remoteFilePath1, Files.size(tempFilePath), -1, false);

		fileResponse1.setLocalFile(tempFilePath);

		Assert.assertTrue(
			_asyncBroker.takeWithResult(remoteFilePath1, fileResponse1));

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyRepository.class.getName(), Level.WARNING)) {

			Assert.assertTrue(
				_asyncBroker.takeWithResult(
					remoteFilePath2,
					new FileResponse(
						remoteFilePath2, FileResponse.FILE_NOT_FOUND, -1,
						false)));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Remote file remoteFile2 is not found", logEntry.getMessage());
		}

		Map<Path, Path> resultPathMap = noticeableFuture.get();

		Assert.assertEquals(resultPathMap.toString(), 1, resultPathMap.size());
		Assert.assertEquals(localFilePath, resultPathMap.get(remoteFilePath1));
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testGetFilesCancelled() {
		Path remoteFilePath1 = Paths.get("remoteFile1");

		NoticeableFuture<Map<Path, Path>> noticeableFuture =
			_nettyRepository.getFiles(
				_embeddedChannel,
				HashMapBuilder.<Path, Path>put(
					remoteFilePath1, Paths.get("localFile1")
				).put(
					Paths.get("remoteFile2"), Paths.get("requestFile2")
				).build(),
				true);

		Map<Path, NoticeableFuture<FileResponse>> openBids =
			_asyncBroker.getOpenBids();

		NoticeableFuture<FileResponse> fileGetNoticeableFuture = openBids.get(
			remoteFilePath1);

		Assert.assertNotNull(fileGetNoticeableFuture);

		fileGetNoticeableFuture.cancel(true);

		Assert.assertTrue(noticeableFuture.isCancelled());
	}

	@AdviseWith(
		adviceClasses = {
			NettyUtilAdvice.class, DefaultNoticeableFutureAdvice.class
		}
	)
	@Test
	public void testGetFilesCovertCausedException() throws Exception {
		Path remoteFilePath = Paths.get("remoteFile");

		NoticeableFuture<Map<Path, Path>> noticeableFuture =
			_nettyRepository.getFiles(
				_embeddedChannel,
				HashMapBuilder.<Path, Path>put(
					remoteFilePath, Paths.get("localFile")
				).build(),
				true);

		Exception exception = new Exception();

		DefaultNoticeableFutureAdvice.setConvertThrowable(exception);

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyRepository.class.getName(), Level.WARNING)) {

			Assert.assertTrue(
				_asyncBroker.takeWithResult(
					remoteFilePath,
					new FileResponse(
						_repositoryPath, FileResponse.FILE_NOT_FOUND, -1,
						false)));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Remote file remoteFile is not found", logEntry.getMessage());
		}

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Assert.assertSame(exception, executionException.getCause());
		}
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testGetFilesEmptyMap() throws Exception {
		NoticeableFuture<Map<Path, Path>> noticeableFuture =
			_nettyRepository.getFiles(
				_embeddedChannel, Collections.<Path, Path>emptyMap(), true);

		Assert.assertSame(
			Collections.<Path, Path>emptyMap(), noticeableFuture.get());
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testGetFilesExecutionException() throws Exception {
		Path remoteFilePath1 = Paths.get("remoteFile1");

		NoticeableFuture<Map<Path, Path>> noticeableFuture =
			_nettyRepository.getFiles(
				_embeddedChannel,
				HashMapBuilder.<Path, Path>put(
					remoteFilePath1, Paths.get("requestFile1")
				).put(
					Paths.get("remoteFile2"), Paths.get("requestFile2")
				).build(),
				true);

		Exception exception = new Exception();

		Assert.assertTrue(
			_asyncBroker.takeWithException(remoteFilePath1, exception));

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Assert.assertSame(exception, executionException.getCause());
		}
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testGetFileTimeoutCancellation() {
		NettyRepository nettyRepository = new NettyRepository(
			_repositoryPath, 0);

		_channelPipeline.addLast(
			new FileResponseChannelHandler(
				nettyRepository.getAsyncBroker(),
				_embeddedChannel.eventLoop()));

		NoticeableFuture<Path> noticeableFuture = nettyRepository.getFile(
			_embeddedChannel, Paths.get("remoteFile"), Paths.get("localFile"),
			false, false);

		Assert.assertTrue(noticeableFuture.isDone());
		Assert.assertTrue(noticeableFuture.isCancelled());
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testGetLastModifiedTime() throws IOException {
		Assert.assertEquals(
			Long.MIN_VALUE, NettyRepository.getLastModifiedTime(null));
		Assert.assertEquals(
			Long.MIN_VALUE,
			NettyRepository.getLastModifiedTime(Paths.get("Unknown")));

		FileTime fileTime = Files.getLastModifiedTime(_repositoryPath);

		Assert.assertEquals(
			fileTime.toMillis(),
			NettyRepository.getLastModifiedTime(_repositoryPath));
	}

	@Aspect
	public static class DefaultNoticeableFutureAdvice {

		public static void setConvertThrowable(Throwable throwable) {
			_convertThrowable = throwable;
		}

		@Around(
			"execution(public void com.liferay.petra.concurrent." +
				"DefaultNoticeableFuture.set(Object))"
		)
		public void set(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Object[] args = proceedingJoinPoint.getArgs();

			if ((args.length == 1) && (args[0] instanceof Map)) {
				throw _convertThrowable;
			}

			proceedingJoinPoint.proceed();
		}

		private static Throwable _convertThrowable;

	}

	protected void doTestGetFileChannelFailure(
			final boolean asyncBrokerFailure, boolean logging)
		throws InterruptedException {

		final Exception exception = new Exception();

		final Path remoteFilePath = Paths.get("remoteFile");

		_channelPipeline.addLast(
			new ChannelOutboundHandlerAdapter() {

				@Override
				public void write(
					ChannelHandlerContext channelHandlerContext, Object message,
					ChannelPromise channelPromise) {

					if (asyncBrokerFailure) {
						_asyncBroker.takeWithException(
							remoteFilePath, exception);
					}

					channelPromise.setFailure(exception);
				}

			});

		Level level = Level.OFF;

		if (logging) {
			level = Level.ALL;
		}

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyRepository.class.getName(), level)) {

			NoticeableFuture<Path> noticeableFuture = _nettyRepository.getFile(
				_embeddedChannel, remoteFilePath, Paths.get("localFile"), false,
				false);

			try {
				noticeableFuture.get();
			}
			catch (ExecutionException executionException) {
				Throwable throwable = executionException.getCause();

				if (!asyncBrokerFailure) {
					Assert.assertEquals(
						"Unable to fetch remote file " + remoteFilePath,
						throwable.getMessage());

					throwable = throwable.getCause();
				}

				Assert.assertSame(exception, throwable);
			}

			if (logging) {
				List<LogEntry> logEntries = logCapture.getLogEntries();

				LogEntry logEntry = logEntries.remove(0);

				Assert.assertEquals(
					"Fetching remote file " + remoteFilePath,
					logEntry.getMessage());

				if (asyncBrokerFailure) {
					logEntry = logEntries.remove(0);

					Assert.assertEquals(
						"Unable to place exception because no future exists " +
							"with ID " + remoteFilePath,
						logEntry.getMessage());

					Throwable throwable = logEntry.getThrowable();

					Assert.assertEquals(
						"Unable to fetch remote file " + remoteFilePath,
						throwable.getMessage());
					Assert.assertSame(exception, throwable.getCause());
				}

				Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
			}
		}
	}

	private final AnnotatedObjectDecoder _annotatedObjectDecoder =
		new AnnotatedObjectDecoder();
	private AsyncBroker<Path, FileResponse> _asyncBroker;
	private ChannelPipeline _channelPipeline;

	private final EmbeddedChannel _embeddedChannel = new EmbeddedChannel(
		new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel channel) {
				ChannelPipeline channelPipeline = channel.pipeline();

				channelPipeline.addLast(
					AnnotatedObjectDecoder.NAME, _annotatedObjectDecoder);
			}

		});

	private NettyRepository _nettyRepository;
	private Path _repositoryPath;

}