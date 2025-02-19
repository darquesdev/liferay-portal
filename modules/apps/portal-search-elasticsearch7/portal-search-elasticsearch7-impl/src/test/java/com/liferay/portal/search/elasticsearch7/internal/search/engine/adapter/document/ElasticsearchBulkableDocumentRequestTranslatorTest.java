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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.document.DefaultElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch7.internal.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.util.PropsImpl;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.common.xcontent.XContentType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class ElasticsearchBulkableDocumentRequestTranslatorTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.setProps(new PropsImpl());

		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchBulkableDocumentRequestTranslatorTest.class);

		_elasticsearchFixture.setUp();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Before
	public void setUp() throws Exception {
		ElasticsearchDocumentFactory elasticsearchDocumentFactory =
			createElasticsearchDocumentFactory();

		ElasticsearchBulkableDocumentRequestTranslator
			elasticsearchBulkableDocumentRequestTranslator =
				createElasticsearchBulkableDocumentRequestTranslator(
					elasticsearchDocumentFactory);

		_elasticsearchBulkableDocumentRequestTranslator =
			elasticsearchBulkableDocumentRequestTranslator;

		_elasticsearchDocumentFactory = elasticsearchDocumentFactory;

		_documentFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_documentFixture.tearDown();
	}

	@Test
	public void testDeleteDocumentRequestTranslationWithNoRefresh() {
		doTestDeleteDocumentRequestTranslation(
			false, WriteRequest.RefreshPolicy.NONE);
	}

	@Test
	public void testDeleteDocumentRequestTranslationWithRefresh() {
		doTestDeleteDocumentRequestTranslation(
			true, WriteRequest.RefreshPolicy.IMMEDIATE);
	}

	@Test
	public void testIndexDocumentRequestTranslationWithNoRefresh()
		throws Exception {

		doTestIndexDocumentRequestTranslation(
			"1", false, WriteRequest.RefreshPolicy.NONE);
	}

	@Test
	public void testIndexDocumentRequestTranslationWithNoRefreshNoId()
		throws Exception {

		doTestIndexDocumentRequestTranslation(
			null, false, WriteRequest.RefreshPolicy.NONE);
	}

	@Test
	public void testIndexDocumentRequestTranslationWithRefresh()
		throws Exception {

		doTestIndexDocumentRequestTranslation(
			"1", true, WriteRequest.RefreshPolicy.IMMEDIATE);
	}

	@Test
	public void testIndexDocumentRequestTranslationWithRefreshNoId()
		throws Exception {

		doTestIndexDocumentRequestTranslation(
			null, true, WriteRequest.RefreshPolicy.IMMEDIATE);
	}

	@Test
	public void testUpdateDocumentRequestTranslationWithNoRefresh()
		throws Exception {

		doTestUpdateDocumentRequestTranslation(
			"1", false, WriteRequest.RefreshPolicy.NONE);
	}

	@Test
	public void testUpdateDocumentRequestTranslationWithNoRefreshNoId()
		throws Exception {

		doTestUpdateDocumentRequestTranslation(
			null, false, WriteRequest.RefreshPolicy.NONE);
	}

	@Test
	public void testUpdateDocumentRequestTranslationWithRefresh()
		throws Exception {

		doTestUpdateDocumentRequestTranslation(
			"1", true, WriteRequest.RefreshPolicy.IMMEDIATE);
	}

	@Test
	public void testUpdateDocumentRequestTranslationWithRefreshNoId()
		throws Exception {

		doTestUpdateDocumentRequestTranslation(
			null, true, WriteRequest.RefreshPolicy.IMMEDIATE);
	}

	protected static ElasticsearchBulkableDocumentRequestTranslator
		createElasticsearchBulkableDocumentRequestTranslator(
			ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		return new ElasticsearchBulkableDocumentRequestTranslatorImpl() {
			{
				setElasticsearchDocumentFactory(elasticsearchDocumentFactory);
			}
		};
	}

	protected static ElasticsearchDocumentFactory
		createElasticsearchDocumentFactory() {

		return new DefaultElasticsearchDocumentFactory();
	}

	protected void doTestDeleteDocumentRequestTranslation(
		boolean refreshPolicy,
		WriteRequest.RefreshPolicy expectedRefreshPolicy) {

		String id = "1";

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			_INDEX_NAME, id);

		deleteDocumentRequest.setRefresh(refreshPolicy);
		deleteDocumentRequest.setType(_MAPPING_NAME);

		DeleteRequest deleteRequest =
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				deleteDocumentRequest);

		Assert.assertEquals(
			expectedRefreshPolicy, deleteRequest.getRefreshPolicy());
		Assert.assertEquals(_INDEX_NAME, deleteRequest.index());
		Assert.assertEquals(_MAPPING_NAME, deleteRequest.type());
		Assert.assertEquals(id, deleteRequest.id());

		BulkRequest bulkRequest = new BulkRequest();

		bulkRequest.add(
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				deleteDocumentRequest));

		Assert.assertEquals(1, bulkRequest.numberOfActions());
	}

	protected void doTestIndexDocumentRequestTranslation(
			String id, boolean refreshPolicy,
			WriteRequest.RefreshPolicy expectedRefreshPolicy)
		throws Exception {

		Document document = new DocumentImpl();

		_setUid(document, id);

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			_INDEX_NAME, document);

		indexDocumentRequest.setRefresh(refreshPolicy);
		indexDocumentRequest.setType(_MAPPING_NAME);

		IndexRequest indexRequest =
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				indexDocumentRequest);

		Assert.assertEquals(
			expectedRefreshPolicy, indexRequest.getRefreshPolicy());
		Assert.assertEquals(_INDEX_NAME, indexRequest.index());
		Assert.assertEquals(id, indexRequest.id());

		String source = XContentHelper.convertToJson(
			indexRequest.source(), false, XContentType.JSON);

		Assert.assertEquals(
			_elasticsearchDocumentFactory.getElasticsearchDocument(document),
			source);

		BulkRequest bulkRequest = new BulkRequest();

		bulkRequest.add(
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				indexDocumentRequest));

		Assert.assertEquals(1, bulkRequest.numberOfActions());
	}

	protected void doTestUpdateDocumentRequestTranslation(
			String id, boolean refreshPolicy,
			WriteRequest.RefreshPolicy expectedRefreshPolicy)
		throws Exception {

		Document document = new DocumentImpl();

		_setUid(document, id);

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			_INDEX_NAME, id, document);

		updateDocumentRequest.setRefresh(refreshPolicy);
		updateDocumentRequest.setType(_MAPPING_NAME);

		UpdateRequest updateRequest =
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				updateDocumentRequest);

		Assert.assertEquals(
			expectedRefreshPolicy, updateRequest.getRefreshPolicy());
		Assert.assertEquals(_INDEX_NAME, updateRequest.index());
		Assert.assertEquals(id, updateRequest.id());

		IndexRequest indexRequest = updateRequest.doc();

		String source = XContentHelper.convertToJson(
			indexRequest.source(), false, XContentType.JSON);

		Assert.assertEquals(
			_elasticsearchDocumentFactory.getElasticsearchDocument(document),
			source);

		BulkRequest bulkRequest = new BulkRequest();

		bulkRequest.add(
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				updateDocumentRequest));

		Assert.assertEquals(1, bulkRequest.numberOfActions());
	}

	private void _setUid(Document document, String uid) {
		if (!Validator.isBlank(uid)) {
			document.addKeyword(Field.UID, uid);
		}
	}

	private static final String _INDEX_NAME = "test_request_index";

	private static final String _MAPPING_NAME = "testMapping";

	private static ElasticsearchFixture _elasticsearchFixture;

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private ElasticsearchBulkableDocumentRequestTranslator
		_elasticsearchBulkableDocumentRequestTranslator;
	private ElasticsearchDocumentFactory _elasticsearchDocumentFactory;

}