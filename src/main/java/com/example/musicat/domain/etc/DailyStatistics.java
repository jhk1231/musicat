package com.example.musicat.domain.etc;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "daliystatistics")
public class DailyStatistics {

    @Id
    @GeneratedValue
    @Column(name = "daily_no")
    private int dailyNo;
    @Column(name = "writedate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dailyDate;
    @Column(name = "daily_visits")
    private int dailyVisits;
    @Column(name = "daily_article")
    private int dailyArticle;

    public static DailyStatistics dailyStatistics(int dailyNo, int dailyVisits, int dailyArticle) {
        DailyStatistics daily = new DailyStatistics();
        daily.dailyNo = dailyNo;
        daily.dailyVisits = dailyVisits;
        daily.dailyArticle = dailyArticle;
        return daily;

    }
}
