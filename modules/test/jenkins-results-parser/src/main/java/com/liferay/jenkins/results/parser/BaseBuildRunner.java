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

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildRunner<T extends BuildData>
	implements BuildRunner<T> {

	public T getBuildData() {
		return _buildData;
	}

	@Override
	public void run() {
		setUpWorkspace();
	}

	@Override
	public void setBuildDescription(String buildDescription) {
		buildDescription = buildDescription.replaceAll("\"", "\\\\\"");
		buildDescription = buildDescription.replaceAll("\'", "\\\\\'");

		StringBuilder sb = new StringBuilder();

		sb.append("def job = Jenkins.instance.getItemByFullName(\"");
		sb.append(_buildData.getJobName());
		sb.append("\"); ");

		sb.append("def build = job.getBuildByNumber(");
		sb.append(_buildData.getBuildNumber());
		sb.append("); ");

		sb.append("build.description = \"");
		sb.append(buildDescription);
		sb.append("\";");

		JenkinsResultsParserUtil.executeJenkinsScript(
			_buildData.getMasterHostname(), "script=" + sb.toString());
	}

	@Override
	public void setUp() {
	}

	@Override
	public void tearDown() {
		tearDownWorkspace();
	}

	protected BaseBuildRunner(T buildData) {
		_buildData = buildData;

		_job = JobFactory.newJob(_buildData);

		_job.readJobProperties();
	}

	protected Job getJob() {
		return _job;
	}

	protected abstract void initWorkspace();

	protected void setUpWorkspace() {
		if (workspace == null) {
			initWorkspace();
		}

		workspace.setUp(getJob());
	}

	protected void tearDownWorkspace() {
		if (workspace == null) {
			initWorkspace();
		}

		workspace.tearDown();
	}

	protected Workspace workspace;

	private final T _buildData;
	private final Job _job;

}