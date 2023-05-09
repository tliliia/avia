package com.tronina.avia.model.entity;

public enum Status {
    CREATED,
//    RESERVED, - такой статус, если присутствует запись в таблице reservation
    RESERVATION_CONFIRMED,
    SOLD
}