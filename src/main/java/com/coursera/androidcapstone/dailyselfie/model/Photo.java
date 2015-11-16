package com.coursera.androidcapstone.dailyselfie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=true)
    private String uri;

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_fk")
    private User owner;

    public Photo() {

    }

    public Photo(String name, String uri, User owner) {
        this.name = name;
        this.uri = uri;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public User getOwner() {
        return owner;
    }
}
