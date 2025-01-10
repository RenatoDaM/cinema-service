--liquibase formatted sql
--changeset renatodam:add-session-end-time-field-at-session-table logicalFilePath:add-session-end-time

ALTER TABLE session ADD COLUMN
    session_end_time TIMESTAMP WITHOUT TIME ZONE NOT NULL;