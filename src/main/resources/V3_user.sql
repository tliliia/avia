CREATE TABLE customer
(
    id            bigserial NOT NULL,
    email         varchar(255) NULL,
    hash_password varchar(255) NULL,
    "role"        varchar(255) NULL,
    CONSTRAINT customer_pkey PRIMARY KEY (id)
);