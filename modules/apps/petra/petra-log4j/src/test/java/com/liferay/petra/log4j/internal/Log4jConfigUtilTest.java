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

package com.liferay.petra.log4j.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Hai Yu
 */
public class Log4jConfigUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConfigureLog4J() {
		String loggerName = StringUtil.randomString();

		Logger logger = Logger.getLogger(loggerName);

		Map<String, String> priorities = Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ALL));

		Assert.assertEquals(
			priorities, Collections.singletonMap(loggerName, _ALL));

		_assertPriority(logger, _ALL);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _DEBUG));

		_assertPriority(logger, _DEBUG);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR));

		_assertPriority(logger, _ERROR);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _FATAL));

		_assertPriority(logger, _FATAL);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _INFO));

		_assertPriority(logger, _INFO);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _OFF));

		_assertPriority(logger, _OFF);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _TRACE));

		_assertPriority(logger, _TRACE);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _WARN));

		_assertPriority(logger, _WARN);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, "FAKE_LEVEL"));

		_assertPriority(logger, _DEBUG);
	}

	@Test
	public void testConfigureLog4JWithAppender() {
		String loggerName = StringUtil.randomString();

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR));

		Logger logger = Logger.getLogger(loggerName);

		_assertAppenders(logger);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(
				loggerName, _ERROR, ConsoleAppender.class));

		_assertAppenders(logger, ConsoleAppender.class);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(
				loggerName, _ERROR, NullAppender.class));

		_assertAppenders(logger, NullAppender.class);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(
				loggerName, _ERROR, ConsoleAppender.class, NullAppender.class));

		_assertAppenders(logger, ConsoleAppender.class, NullAppender.class);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(
				loggerName, _ERROR, ConsoleAppender.class, NullAppender.class),
			NullAppender.class.getName());

		_assertAppenders(logger, ConsoleAppender.class);
	}

	@Test
	public void testConfigureLog4JWithException() {
		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				Log4jConfigUtil.class.getName(),
				java.util.logging.Level.SEVERE)) {

			Log4jConfigUtil.configureLog4J(null);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"java.lang.NullPointerException", logEntry.getMessage());
		}
	}

	@Test
	public void testGetJDKLevel() {
		Assert.assertEquals(
			"FINE", String.valueOf(Log4jConfigUtil.getJDKLevel(_DEBUG)));
		Assert.assertEquals(
			"SEVERE", String.valueOf(Log4jConfigUtil.getJDKLevel(_ERROR)));
		Assert.assertEquals(
			"INFO", String.valueOf(Log4jConfigUtil.getJDKLevel(_INFO)));
		Assert.assertEquals(
			"WARNING", String.valueOf(Log4jConfigUtil.getJDKLevel(_WARN)));
	}

	@Test
	public void testGetPriorities() {
		String loggerName = StringUtil.randomString();

		Map<String, String> priorities = Log4jConfigUtil.getPriorities();

		Assert.assertFalse(priorities.containsKey(loggerName));

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR));

		priorities = Log4jConfigUtil.getPriorities();

		Assert.assertEquals(
			"The priority should be ERROR by configuration", _ERROR,
			priorities.get(loggerName));

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, null));

		priorities = Log4jConfigUtil.getPriorities();

		Assert.assertFalse(priorities.containsKey(loggerName));
	}

	@Test
	public void testMisc() {
		new Log4jConfigUtil();
	}

	@Test
	public void testSetLevel() {
		String loggerName = StringUtil.randomString();

		Logger logger = Logger.getLogger(loggerName);

		_assertPriority(logger, _INFO);

		String childLoggerName = loggerName + ".child";

		Logger childLogger = Logger.getLogger(childLoggerName);

		_assertPriority(childLogger, _INFO);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _WARN));

		_assertPriority(logger, _WARN);
		_assertPriority(childLogger, _WARN);

		Log4jConfigUtil.setLevel(loggerName, _DEBUG);

		_assertPriority(logger, _DEBUG);
		_assertPriority(childLogger, _DEBUG);

		Log4jConfigUtil.setLevel(childLoggerName, _ERROR);

		_assertPriority(logger, _DEBUG);
		_assertPriority(childLogger, _ERROR);
	}

	@NewEnv(type = NewEnv.Type.JVM)
	@Test
	public void testShutdownLog4J() {
		Logger logger = Logger.getRootLogger();

		Enumeration<Appender> appendersEnumeration = logger.getAllAppenders();

		Assert.assertTrue(
			"The root logger should include appenders",
			appendersEnumeration.hasMoreElements());

		Log4jConfigUtil.shutdownLog4J();

		Assert.assertFalse(
			"The root logger should not own appenders after shutting down",
			appendersEnumeration.hasMoreElements());
	}

	@Rule
	public final NewEnvTestRule newEnvTestRule = NewEnvTestRule.INSTANCE;

	private void _assertAppenders(Logger logger, Class<?>... appenderTypes) {
		Enumeration<Appender> enumeration = logger.getAllAppenders();

		List<String> targetAppenderNames = new ArrayList<>();

		while (enumeration.hasMoreElements()) {
			Appender appender = enumeration.nextElement();

			targetAppenderNames.add(appender.getName());
		}

		Assert.assertEquals(targetAppenderNames.size(), appenderTypes.length);

		for (Class<?> appenderType : appenderTypes) {
			Assert.assertTrue(
				"Missing appender " + appenderType.getName(),
				targetAppenderNames.contains(appenderType.getName()));
		}
	}

	private void _assertPriority(Logger logger, String priority) {
		if (priority.equals(_ALL)) {
			Assert.assertTrue(
				"TRACE should be enabled if logging priority is ALL",
				logger.isTraceEnabled());

			return;
		}

		if (logger.isTraceEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _TRACE);
		}
		else if (logger.isDebugEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _DEBUG);
		}
		else if (logger.isInfoEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _INFO);
		}
		else if (logger.isEnabledFor(Level.WARN)) {
			Assert.assertEquals("Logging priority is wrong", priority, _WARN);
		}
		else if (logger.isEnabledFor(Level.ERROR)) {
			Assert.assertEquals("Logging priority is wrong", priority, _ERROR);
		}
		else if (logger.isEnabledFor(Level.FATAL)) {
			Assert.assertEquals("Logging priority is wrong", priority, _FATAL);
		}
		else {
			Assert.assertEquals("Logging priority is wrong", priority, _OFF);
		}
	}

	private String _generateXMLConfigurationContent(
		String loggerName, String priority, Class<?>... appenderTypes) {

		StringBundler sb = new StringBundler(10 + (8 * appenderTypes.length));

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<!DOCTYPE log4j:configuration SYSTEM \"log4j.dtd\">");
		sb.append("<log4j:configuration xmlns:log4j=");
		sb.append("\"http://jakarta.apache.org/log4j/\">");

		for (Class<?> appenderType : appenderTypes) {
			sb.append("<appender class=\"");
			sb.append(appenderType.getName());
			sb.append("\" name=\"");
			sb.append(appenderType.getName());
			sb.append("\"></appender>");
		}

		sb.append("<category name=\"");
		sb.append(loggerName);
		sb.append("\"><priority value=\"");
		sb.append(priority);
		sb.append("\" />");

		for (Class<?> appenderType : appenderTypes) {
			sb.append("<appender-ref ref=\"");
			sb.append(appenderType.getName());
			sb.append("\" />");
		}

		sb.append("</category></log4j:configuration>");

		return sb.toString();
	}

	private static final String _ALL = "ALL";

	private static final String _DEBUG = "DEBUG";

	private static final String _ERROR = "ERROR";

	private static final String _FATAL = "FATAL";

	private static final String _INFO = "INFO";

	private static final String _OFF = "OFF";

	private static final String _TRACE = "TRACE";

	private static final String _WARN = "WARN";

}