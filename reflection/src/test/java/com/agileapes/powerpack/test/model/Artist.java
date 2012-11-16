package com.agileapes.powerpack.test.model;

import java.util.Collection;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/16, 19:04)
 */
public class Artist {

    private String name;
    private Collection<Artist> affiliations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Artist> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(Collection<Artist> affiliations) {
        this.affiliations = affiliations;
    }

}
