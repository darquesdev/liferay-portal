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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class JUnitBatchTestrayCaseResult extends BatchTestrayCaseResult {

	public JUnitBatchTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup,
		TestClassGroup.TestClass testClass) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);

		_testClass = testClass;
	}

	@Override
	public String getName() {
		String testClassName = JenkinsResultsParserUtil.getCanonicalPath(
			_testClass.getTestClassFile());

		testClassName = testClassName.replaceAll(
			".*/(com/liferay.*)\\.java", "$1");

		return testClassName.replace("/", ".");
	}

	@Override
	public Status getStatus() {
		TestClassResult testClassResult = getTestClassResult();

		if ((testClassResult == null) || testClassResult.isFailing()) {
			return Status.FAILED;
		}

		return Status.PASSED;
	}

	public TestClassResult getTestClassResult() {
		Build build = getBuild();

		String result = build.getResult();

		if (!result.equals("SUCCESS") && !result.equals("UNSTABLE")) {
			return null;
		}

		return build.getTestClassResult(getName());
	}

	private final TestClassGroup.TestClass _testClass;

}