package com.coursera.androidcapstone.dailyselfie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Question {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(nullable=false)
    private String question_text;

    @Column(nullable=false)
    private String answer;

    @Column(nullable=true)
    private Long took_at;

    @JsonIgnore
    @ManyToOne(targetEntity=CheckIn.class)
    @JoinColumn(name="check_in_fk")
    private CheckIn check_in;

    public Question() {
        super();
    }

    public Question(String question, String answer) {
        this.question_text = question;
        this.answer = answer;
    }

    public Question(String question, String answer, Long took_at) {
        this.question_text = question;
        this.answer = answer;
        this.took_at = took_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("question_text")
    public String getQuestionText() {
        return question_text;
    }

    public void setQuestionText(String question_text) {
        this.question_text = question_text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @JsonProperty("took_at")
    public Long getTookAt() {
        return took_at;
    }

    public void setTookAt(Long took_at) {
        this.took_at = took_at;
    }

    @JsonIgnore
    @JsonProperty("check_in")
    public CheckIn getCheckIn() {
        return check_in;
    }

    public void setCheckIn(CheckIn check_in) {
        this.check_in = check_in;
    }

    @Override
    public String toString() {
        return "id = " + id +
               "\n question_text = " + question_text + 
               "\n answer = " + answer +
               "\n took_at = " + took_at + "\n";
    }

}
