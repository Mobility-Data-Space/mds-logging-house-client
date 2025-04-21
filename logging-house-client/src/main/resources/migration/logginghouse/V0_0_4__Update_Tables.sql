ALTER TABLE edc_logging_house_message
    ADD COLUMN IF NOT EXISTS status CHAR(1),
    ADD COLUMN IF NOT EXISTS retries INT DEFAULT 0;

UPDATE edc_logging_house_message SET status = 'P' WHERE receipt IS NULL;
UPDATE edc_logging_house_message SET status = 'S' WHERE receipt IS NOT NULL;

ALTER TABLE edc_logging_house_message
    ALTER COLUMN status SET NOT NULL;