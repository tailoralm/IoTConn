package com.example.iotconn.models;

import java.io.Serializable;
import java.util.UUID;

public class Action implements Serializable {

    String id;
    String name;
    String value;

    public Action() {
        setId(UUID.randomUUID().toString());
        setName("");
        setValue("");
    }

    public Action(String id, String name, String value){
        setId(id);
        setName(name);
        setValue(value);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
