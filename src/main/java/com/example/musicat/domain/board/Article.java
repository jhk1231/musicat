package com.example.musicat.domain.board;

import com.example.musicat.controller.ArticleForm;
import com.example.musicat.domain.member.Member;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article")
public class Article {

    @Id @GeneratedValue
    @Column(name = "article_no")
    private Integer no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_no")
    private Board board;

    private String nickname; // 회원명
    private String subject; // 제목
    private String content; // 내용

    @Column(name = "writedate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp writedate; // 작성일

    private int viewcount; // 조회수
    private int likecount; // 추천수

    @OneToMany(fetch = LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> fileList = new ArrayList<>(); // 이미지 List

    // 삭제시만 같이 하기 위해 Casacade Remove
    @OneToMany(fetch = LAZY, mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>(); // 댓글

//    private FileVO attacheFile; //첨부 파일
//    private FileVO thumbnail; // 썸네일
//
//    private int likeCheck;


    //==생성 메소드==//
//    @Builder
    public static Article createArticle(Member member, Board board, String nickname, ArticleForm form) {
        Article article = new Article();
        article.member = member;
        article.board = board;
        article.nickname = nickname;
        article.subject = form.getSubject();
        article.content = form.getContent();
        return article;
    }


    //==비즈니스 로직==//
    //게시글 수정
    public static void updateArticle(Article article,Board board ,ArticleForm articleForm) {
        article.board = board;
        article.subject = articleForm.getSubject();
        article.content = articleForm.getContent();
    }


    //조회수 증가
    public void addViewcount(){
        this.viewcount += 1;
    }

    //추천 수 증가(ajax 용 아님)
    public void addLikecount(){
        this.likecount += 1;
    }

    //추천 수 감소(ajax 용 아님)
    public void removeLikecount(){
        this.likecount -= 1;
    }


    @Override
    public String toString() {
        return "Article{" +
                "no=" + no +
                ", member=" + member +
                ", board=" + board +
                ", nickname='" + nickname + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", writedate=" + writedate +
                ", viewcount=" + viewcount +
                ", likecount=" + likecount +
//                ", fileList=" + fileList +
//                ", replyList=" + replyList +
                '}';
    }
}
