package io.cockroachdb.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum EventType {
    insert,
    update,
    upsert,
    delete
}
