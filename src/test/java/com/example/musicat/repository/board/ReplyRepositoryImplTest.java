package com.example.musicat.repository.board;

import com.example.musicat.domain.board.Article;
import com.example.musicat.domain.board.Reply;
import com.example.musicat.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = false)
class ReplyRepositoryImplTest {


    @Autowired EntityManager em;
    @Autowired private ArticleRepositoryImpl articleRepository;
    @Autowired private ReplyRepositoryImpl replyRepository;

    @Test
    public void 댓글_등록() throws Exception {
        //given
        int articleNo = 33;
        int memberNo = 4;
        String content = "댓글 테스트2";
        Member findMember = em.find(Member.class, memberNo);
        Reply createReply = Reply.createReply(findMember, content);
        Reply createReply2 = Reply.createReply(findMember, content);
        Reply createReply3 = Reply.createReply(findMember, content);
        Article findArticle = (Article) articleRepository.findOne(articleNo);

        //when
        createReply.addArticle(findArticle);
        createReply2.addArticle(findArticle);
        createReply3.addArticle(findArticle);
        replyRepository.save(createReply);
        replyRepository.save(createReply2);
        replyRepository.save(createReply3);
        //then
    }

    @Test
    public void 댓글_수정() throws Exception {
        //given
        String modifyContent = "수정 테스트1";
        int modifyReplyNo = 3;
        Object modifyReply = replyRepository.findOne(modifyReplyNo);

        //when
        Reply.updateReply((Reply) modifyReply, modifyContent);

        //then
    }

    @Test
    public void 댓글_삭제() throws Exception {
        //given
        int removeReplyNo = 3;

        //when
        replyRepository.remove(removeReplyNo);

        //then
    }

}