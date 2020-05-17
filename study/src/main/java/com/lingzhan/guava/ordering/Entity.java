package com.lingzhan.guava.ordering;

/**
 * Created by 凌战 on 2019/11/21
 */
public class Entity {


    public int status;
    public String name;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Entity(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public Entity(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "status=" + status +
                ", name='" + name + '\'' +
                '}';
    }
}
