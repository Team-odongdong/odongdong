create table bathroom
(
    bathroom_id    int auto_increment
        primary key,
    title          varchar(100)                         not null,
    latitude       double                               not null,
    longitude      double                               not null,
    is_locked      varchar(5) default 'N'               not null,
    address        varchar(100)                         not null,
    address_detail varchar(100)                         null,
    register       tinyint(1) default 0                 not null,
    is_unisex      tinyint(1) default 0                 null,
    image_url      varchar(200)                         null,
    operation_time varchar(100)                         null,
    created_at     datetime   default CURRENT_TIMESTAMP not null,
    updated_at     datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    member_id        bigint                               null
)
    charset = utf8;

create index location_idx
    on bathroom (longitude, latitude);

create table deleted_bathroom
(
    bathroom_id    int auto_increment
        primary key,
    title          varchar(100)                         not null,
    latitude       double                               not null,
    longitude      double                               not null,
    is_locked      varchar(5) default 'N'               not null,
    address        varchar(100)                         not null,
    address_detail varchar(100)                         null,
    is_unisex      tinyint(1) default 0                 null,
    image_url      varchar(200)                         null,
    operation_time varchar(100)                         null,
    created_at     datetime                             not null,
    deleted_at     datetime   default CURRENT_TIMESTAMP not null,
    member_id        bigint                               null
)
    charset = utf8;

create table inquery
(
    inquery_id int auto_increment
        primary key,
    content    varchar(1000)                      not null,
    email      varchar(100)                       null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
)
    charset = utf8mb4;

create table rating
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6) null,
    score       double      null,
    updated_at  datetime(6) null,
    bathroom_id bigint      null,
    member_id     bigint      null
)
    charset = utf8mb4;

create table updated_bathroom
(
    id             bigint auto_increment
        primary key,
    member_id        bigint               null,
    bathroom_id    bigint               null,
    title          varchar(45)          not null,
    latitude       double               not null,
    longitude      double               not null,
    is_locked      varchar(5)           not null,
    address        varchar(45)          not null,
    address_detail varchar(100)         null,
    image_url      varchar(200)         null,
    operation_time varchar(100)         null,
    is_unisex      tinyint(1) default 0 null,
    register       tinyint(1) default 0 not null,
    created_at     datetime(6)          null,
    updated_at     datetime(6)          null
)
    charset = utf8mb4;


create table user
(
    id         int auto_increment
        primary key,
    name       varchar(45)                        not null,
    email      varchar(45)                        not null,
    picture    varchar(100)                       not null,
    role       varchar(45)                        not null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
)
    charset = utf8mb4;
