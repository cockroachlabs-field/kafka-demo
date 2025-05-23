package io.cockroachdb.demo.model;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;

public abstract class AbstractEntity<T extends Serializable> implements Persistable<T> {
    @Transient
    private boolean isNew = true;

    @JsonIgnore
    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
