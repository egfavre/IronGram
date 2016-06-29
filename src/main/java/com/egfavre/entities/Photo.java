package com.egfavre.entities;

import javax.persistence.*;

/**
 * Created by user on 6/28/16.
 */
@Entity
@Table(name="photos")
public class Photo {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User sender;

    @ManyToOne
    User recipient;

    @Column(nullable = false)
    String filename;

    Integer timeLimit;

    Integer viewTime;

    String show;

    public Photo() {
    }

    public Photo(User sender, User recipient, String filename, Integer timeLimit, Integer viewTime, String show) {
        this.sender = sender;
        this.recipient = recipient;
        this.filename = filename;
        this.timeLimit = timeLimit;
        this.viewTime = viewTime;
        this.show = show;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getViewTime() {
        return viewTime;
    }

    public void setViewTime(Integer viewTime) {
        this.viewTime = viewTime;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }
}
