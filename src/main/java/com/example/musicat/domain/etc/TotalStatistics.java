package com.example.musicat.domain.etc;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "totalstatistics")
public class TotalStatistics {

    @Id
    @GeneratedValue
    @Column(name = "total_no")
    private int totalNo;
    @Column(name = "total_member")
    private int totalMember;
    @Column(name = "total_article")
    private int totalArticle;
    @Column(name = "total_view")
    private int totalView;
    @Column(name = "total_visits")
    private int totalVisits;


    public static TotalStatistics totalStatistics(int totalNo, int totalMember, int totalArticle, int totalView, int totalVisits) {
        TotalStatistics total = new TotalStatistics();
        total.totalNo = totalNo;
        total.totalMember = totalMember;
        total.totalArticle = totalArticle;
        total.totalView = totalView;
        total.totalVisits = totalVisits;
        return total;

    }
}
