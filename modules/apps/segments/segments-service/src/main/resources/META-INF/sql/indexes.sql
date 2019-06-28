create index IX_5BFEEA84 on SegmentsEntry (active_, type_[$COLUMN_LENGTH:75$]);
create index IX_56AA45CF on SegmentsEntry (groupId, active_, source[$COLUMN_LENGTH:75$], type_[$COLUMN_LENGTH:75$]);
create index IX_755F267E on SegmentsEntry (groupId, active_, type_[$COLUMN_LENGTH:75$]);
create unique index IX_E72E3826 on SegmentsEntry (groupId, segmentsEntryKey[$COLUMN_LENGTH:75$]);
create index IX_90AB04A7 on SegmentsEntry (source[$COLUMN_LENGTH:75$]);
create index IX_5296FAFD on SegmentsEntry (type_[$COLUMN_LENGTH:75$]);
create index IX_444527CC on SegmentsEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_5C4D314E on SegmentsEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_E1165342 on SegmentsEntryRel (classNameId, classPK);
create index IX_5FBA8532 on SegmentsEntryRel (groupId, classNameId, classPK);
create unique index IX_55B38A5 on SegmentsEntryRel (segmentsEntryId, classNameId, classPK);

create index IX_6465BC65 on SegmentsExperience (groupId, classNameId, classPK, active_, approved);
create unique index IX_8B4A6BC7 on SegmentsExperience (groupId, classNameId, classPK, priority);
create index IX_817D041C on SegmentsExperience (groupId, segmentsEntryId, classNameId, classPK, active_, approved);
create unique index IX_B8F358EB on SegmentsExperience (groupId, segmentsExperienceKey[$COLUMN_LENGTH:75$]);
create index IX_E90B4ACD on SegmentsExperience (segmentsEntryId);
create index IX_15CA0884 on SegmentsExperience (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_6482E006 on SegmentsExperience (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_72C2980B on SegmentsExperiment (groupId, segmentsExperimentKey[$COLUMN_LENGTH:75$]);
create index IX_714776BC on SegmentsExperiment (segmentsExperienceId);
create index IX_DECD8397 on SegmentsExperiment (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_284003D9 on SegmentsExperiment (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_1FF5BA6E on SegmentsExperimentRel (segmentsExperimentId);