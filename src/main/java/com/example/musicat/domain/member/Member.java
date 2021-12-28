package com.example.musicat.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_no")
    private int no;

    @OneToOne
    @JoinColumn(name = "grade_no")
    private Grade grade;

    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String nickname;

    @Column(name = "regdate", insertable = false, updatable = false, nullable = false,  columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp regdate;

    @Column
    private LocalDateTime lastdate;
    @Column
    private int docs;
    @Column
    private int comms;
    @Column
    private int visits;
    @Column
    private Date ban;
    @Column
    private int ismember;

    @Override
    public String toString() {
        return "Member{" +
                "no=" + no +
                ", grade=" + grade +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", regdate=" + regdate +
                ", lastdate=" + lastdate +
                ", docs=" + docs +
                ", comms=" + comms +
                ", visits=" + visits +
                ", ban=" + ban +
                ", ismember=" + ismember +
                '}';
    }
}
