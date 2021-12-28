package com.example.musicat.domain.board;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_no")
    private int no;

    @Column(name = "categoryname")
    private String categoryName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Board> boardList = new ArrayList<>();
}
