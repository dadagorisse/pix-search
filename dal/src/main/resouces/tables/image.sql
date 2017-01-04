-- http://www.h2database.com/html/grammar.html#date
-- created_date ISO 8601 ex: 2016-12-25T05:20:33,9854+01:00
CREATE TABLE IF NOT EXISTS IMAGE(id INT IDENTITY, width INT, height INT, created_date BIGINT);
CREATE INDEX IF NOT EXISTS IMAGE_DATE_IDX ON IMAGE(created_date);
