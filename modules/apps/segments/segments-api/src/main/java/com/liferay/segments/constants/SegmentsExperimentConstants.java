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

package com.liferay.segments.constants;

import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.segments.exception.SegmentsExperimentStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Eduardo García
 * @author Sarai Díaz
 */
public class SegmentsExperimentConstants {

	public static final int STATUS_COMPLETED = 2;

	public static final int STATUS_DRAFT = 0;

	public static final int STATUS_FINISHED_NO_WINNER = 4;

	public static final int STATUS_FINISHED_WINNER = 3;

	public static final int STATUS_PAUSED = 5;

	public static final int STATUS_RUNNING = 1;

	public static final int STATUS_SCHEDULED = 7;

	public static final int STATUS_TERMINATED = 6;

	public enum Goal {

		BOUNCE_RATE("bounce-rate"), CLICK_RATE("click"),
		SCROLL_DEPTH("scroll-depth"), TIME_ON_PAGE("time-on-page");

		public static Goal parse(String label) {
			for (Goal goal : values()) {
				if (label.equals(goal.getLabel())) {
					return goal;
				}
			}

			return null;
		}

		public String getLabel() {
			return _label;
		}

		private Goal(String label) {
			_label = label;
		}

		private final String _label;

	}

	public enum Status {

		COMPLETED(STATUS_COMPLETED, "COMPLETED", "completed", false, true),
		DRAFT(STATUS_DRAFT, "DRAFT", "draft", true, true) {

			@Override
			public Set<Status> validTransitions() {
				return SetUtil.fromCollection(
					Arrays.asList(Status.RUNNING, Status.SCHEDULED));
			}

		},
		FINISHED_NO_WINNER(
			STATUS_FINISHED_NO_WINNER, "FINISHED_NO_WINNER", "no-winner", false,
			true) {

			@Override
			public Set<Status> validTransitions() {
				return Collections.singleton(Status.COMPLETED);
			}

		},
		FINISHED_WINNER_DECLARED(
			STATUS_FINISHED_WINNER, "FINISHED_WINNER", "winner", false, true) {

			@Override
			public Set<Status> validTransitions() {
				return Collections.singleton(Status.COMPLETED);
			}

		},
		PAUSED(STATUS_PAUSED, "PAUSED", "paused", false, true) {

			@Override
			public Set<Status> validTransitions() {
				return Collections.singleton(Status.RUNNING);
			}

		},
		RUNNING(STATUS_RUNNING, "RUNNING", "running", false, true) {

			@Override
			public Set<Status> validTransitions() {
				return SetUtil.fromCollection(
					Arrays.asList(
						Status.FINISHED_NO_WINNER,
						Status.FINISHED_WINNER_DECLARED, Status.PAUSED,
						Status.TERMINATED));
			}

		},
		SCHEDULED(STATUS_SCHEDULED, "SCHEDULED", "scheduled", false, true) {

			@Override
			public Set<Status> validTransitions() {
				return Collections.singleton(Status.RUNNING);
			}

		},
		TERMINATED(STATUS_TERMINATED, "TERMINATED", "terminated", false, true);

		public static int[] exclusiveStates() {
			Stream<Status> stream = Arrays.stream(Status.values());

			return stream.filter(
				Status::isExclusive
			).mapToInt(
				Status::getValue
			).toArray();
		}

		public static Optional<Status> parse(int value) {
			for (Status status : values()) {
				if (status.getValue() == value) {
					return Optional.of(status);
				}
			}

			return Optional.empty();
		}

		public static Optional<Status> parse(String stringValue) {
			for (Status status : values()) {
				if (stringValue.equals(status.toString())) {
					return Optional.of(status);
				}
			}

			return Optional.empty();
		}

		public static void validateTransition(
				final int fromStatusValue, final int toStatusValue)
			throws SegmentsExperimentStatusException {

			Optional<Status> fromStatusOptional = Status.parse(fromStatusValue);

			Status fromStatus = fromStatusOptional.orElseThrow(
				() -> new SegmentsExperimentStatusException(
					"Invalid initial status value " + fromStatusValue));

			Optional<Status> toStatusOptional = Status.parse(toStatusValue);

			Status toStatus = toStatusOptional.orElseThrow(
				() -> new SegmentsExperimentStatusException(
					"Invalid final status value " + toStatusValue));

			if (Objects.equals(fromStatus, toStatus)) {
				return;
			}

			Set<Status> validTransitions = fromStatus.validTransitions();

			if (!validTransitions.contains(toStatus)) {
				throw new SegmentsExperimentStatusException(
					String.format(
						"Invalid status transition: from %s to %s",
						fromStatus.name(), toStatus.name()));
			}
		}

		public static Status valueOf(int value) {
			for (Status status : values()) {
				if (status.getValue() == value) {
					return status;
				}
			}

			throw new IllegalArgumentException("Invalid status value " + value);
		}

		public String getLabel() {
			return _label;
		}

		public int getValue() {
			return _value;
		}

		public boolean isEditable() {
			return _editable;
		}

		public boolean isExclusive() {
			return _exclusive;
		}

		@Override
		public String toString() {
			return _stringValue;
		}

		public Set<Status> validTransitions() {
			return Collections.emptySet();
		}

		private Status(
			int value, String stringValue, String label, boolean editable,
			boolean exclusive) {

			_value = value;
			_stringValue = stringValue;
			_label = label;
			_editable = editable;
			_exclusive = exclusive;
		}

		private final boolean _editable;
		private final boolean _exclusive;
		private final String _label;
		private final String _stringValue;
		private final int _value;

	}

}