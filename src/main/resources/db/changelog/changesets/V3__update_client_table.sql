ALTER TABLE client
    ADD COLUMN first_name VARCHAR(255),
    ADD COLUMN last_name VARCHAR(255),
    ADD COLUMN date_of_birth DATE;

UPDATE client
SET
    first_name = CASE
         WHEN name IS NOT NULL AND position(' ' in name) > 0
             THEN split_part(name, ' ', 1)
         ELSE name
    END,
    last_name = CASE
        WHEN name IS NOT NULL AND position(' ' in name) > 0
            THEN split_part(name, ' ', 2)
        ELSE NULL
    END;

ALTER TABLE client
DROP COLUMN name;