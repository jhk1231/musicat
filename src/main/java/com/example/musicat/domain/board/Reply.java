package com.example.musicat.domain.board;

import com.example.musicat.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {

    @Id @GeneratedValue
    @Column(name = "reply_no")
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_no")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;
    private String nickname;

    @Column(name = "writedate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp writedate;
    private String content;

    //==생성 메소드==//
    public static Reply createReply(Member member, String content){
        Reply reply = new Reply();
        reply.member = member;
        reply.nickname = member.getNickname();
        reply.content = content;
        return reply;
    }

    //==연관관계 편의 메소드==//
    public void addArticle(Article article) {
        this.article = article;
        article.getReplyList().add(this);
    }
                                                      
//    public void addMember(Member member){
//        this.member = member;
//    }

    //==비즈니스 로직==//
    public static void updateReply(Reply reply, String content) {
        reply.content = content;
    }


    @Override
    public String toString() {
        return "Reply{" +
                "no=" + no +
//                ", article=" + article +
                ", member=" + member +
                ", nickname='" + nickname + '\'' +
                ", writedate=" + writedate +
                ", content='" + content + '\'' +
                '}';
    }
}
