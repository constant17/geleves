package com.geleves.app.data.entity;

import javax.persistence.Entity;

import com.geleves.app.data.AbstractEntity;

@Entity
public class Status extends AbstractEntity {
    private String name;

    public Status() { }

    public Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
