DELIMITER $$
CREATE TRIGGER bathroom_remove_back_up
    AFTER DELETE
    ON bathroom
    FOR EACH ROW
BEGIN

    DECLARE title varchar(100);
    DECLARE latitude double;
    DECLARE longitude double;
    DECLARE is_locked varchar(5) default 'N';
    DECLARE address varchar(100);
    DECLARE address_detail varchar(100);
    DECLARE is_unisex tinyint(1) default 0;
    DECLARE image_url varchar(200);
    DECLARE operation_time varchar(100);
    DECLARE created_at datetime;
    DECLARE deleted_at datetime default CURRENT_TIMESTAMP;
    DECLARE member_id bigint;

    SET title = OLD.title;
    SET latitude = OLD.latitude;
    SET longitude = OLD.longitude;
    SET is_locked = OLD.is_locked;
    SET address = OLD.address;
    SET address_detail = OLD.address_detail;
    SET is_unisex = OLD.is_unisex;
    SET image_url = OLD.image_url;
    SET operation_time = OLD.operation_time;
    SET created_at = OLD.created_at;
    SET deleted_at = CURRENT_TIMESTAMP;
    SET member_id = OLD.member_id;


    INSERT INTO deleted_bathroom (title, latitude, longitude, is_locked, address, address_detail, register, is_unisex,
                                  image_url, operation_time, created_at, deleted_at, member_id)
    VALUES (title, latitude, longitude, is_locked, address, address_detail, register, is_unisex, image_url,
            operation_time, created_at, deleted_at, member_id);

END $$
DELIMITER ;
