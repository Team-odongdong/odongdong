SET GLOBAL log_bin_trust_function_creators = 1;

CREATE PROCEDURE generate_bathrooms_loop(NUM INT)
BEGIN
    DECLARE i INT DEFAULT 0;

    WHILE i < NUM DO
            CALL generate_bathroom();
            SET i = i + 1;
        END WHILE;
END;


CREATE PROCEDURE generate_bathroom()
BEGIN
        INSERT INTO bathroom_project.bathroom
        (title, latitude, longitude, is_locked, address, register, is_unisex, operation_time, created_at, updated_at, user_id)
        VALUES
            (CONCAT('Bathroom ', FLOOR(RAND() * 10000000)), RAND() + 37, RAND() + 126, IF(RAND() > 0.5, 'Y', 'N'), CONCAT('Address ', FLOOR(RAND() * 10000000)), IF(RAND() > 0.5, 1, 0), IF(RAND() > 0.5, 1, 0), CONCAT(FLOOR(RAND() * 2) + 10, ':00~', FLOOR(RAND() * 12) + 12, ':00'), NOW(), NOW(), RAND() * 1000);
END;


DELIMITER $$
CREATE FUNCTION bulk_for_generate_bathroom_data(start INT, num_rows INT)
    RETURNS TEXT
BEGIN
    DECLARE i INT DEFAULT start;
    DECLARE result TEXT DEFAULT '';
    WHILE i < num_rows
        DO
            SET result = CONCAT(result,'
(''Bathroom ', i, ''', ', RAND() + 37, ', ', RAND() + 126, ', ''', IF(RAND() > 0.5, 'Y', 'N'),
                                ''', ''Address ', i, ''', 1, ', IF(RAND() > 0.5, 1, 0),
                                ', ''', CONCAT(FLOOR(RAND() * 2) + 10, ':00~', FLOOR(RAND() * 12) + 12, ':00'), ''', NOW(), NOW())'
                , IF(i <> num_rows - 1, ',', ';')
                , CHAR(10));
            SET i = i + 1;
        END WHILE;
    RETURN result;
END$$
DELIMITER ;