CREATE TABLE log
(
    id          BIGSERIAL NOT NULL,
    event       VARCHAR(100) NOT NULL,
    entity_name VARCHAR(100),
    entity_id   INT8,
    time        BIGINT NOT NULL,
    PRIMARY KEY(id)
);