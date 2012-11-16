package com.agileapes.powerpack.test.model;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/16, 21:05)
 */
public class HidingRabbit {

    private String location = "unknown";

    public String getLocation() {
        return "not telling";
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
