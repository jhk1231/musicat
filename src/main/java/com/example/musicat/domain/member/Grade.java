package com.example.musicat.domain.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Grade {

    @Id
    @GeneratedValue
    @Column(name = "grade_no")
    private int no;

    private String name;
    private String auth;
}
