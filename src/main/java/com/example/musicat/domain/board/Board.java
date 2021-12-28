package com.example.musicat.domain.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board")
public class Board {
    @Id @GeneratedValue
    @Column(name = "board_no")
    private int no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    private Category category;

    @Column(name = "boardname")
    private String boardName;

    @Column(name = "boardkind")
    private int boardKind;

    @Override
    public String toString() {
        return "Board{" +
                "no=" + no +
                ", category=" + category +
                ", boardName='" + boardName + '\'' +
                ", boardKind=" + boardKind +
                '}';
    }
}
