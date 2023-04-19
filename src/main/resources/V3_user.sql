CREATE TABLE customer
(
    id            bigserial NOT NULL,
    email         varchar(255) NOT NULL,
    password      varchar(255) NOT NULL,
    "role"        varchar(255) NOT NULL,
    CONSTRAINT customer_pkey PRIMARY KEY (id),
    CONSTRAINT uk_79lf777xwhg41um6w6sihe643 UNIQUE (email)
);