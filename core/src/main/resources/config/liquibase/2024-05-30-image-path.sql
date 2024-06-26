--liquibase formatted sql
--changeset renatodam:add-movie-image-path logicalFilePath:add-movie-image-path
ALTER TABLE movie ADD COLUMN
    image_path VARCHAR(255);