package com.example.musicat.repository.board;

import com.example.musicat.controller.ArticleForm;
import com.example.musicat.domain.board.Article;
import com.example.musicat.domain.board.Board;
import com.example.musicat.domain.board.File;
import com.example.musicat.domain.board.Reply;
import com.example.musicat.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = false)
class ArticleRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    private ArticleRepositoryImpl articleRepository;
    @Autowired
    private ReplyRepositoryImpl replyRepository;



    @Disabled
    @Test
    public void 게시글_등록() throws Exception {
        //given ~가 주어졌을 때
//        Member member = em.find(Member.class, 4);
//        Board board = em.find(Board.class, 6);
//        Article article = Article.createArticle(member, board, "Test용30", "Test용30", "Test30");
//        File createFile = File.createFile("TestFile1", "TestFile2", 13133L, 1);
//        File createFile2 = File.createFile("TestFile1", "TestFile2", 1313L, 1);
//        File createFile3 = File.createFile("TestFile1", "TestFile2", 1313L, 1);
//        createFile.addArticle(article);
//        createFile2.addArticle(article);
//        createFile3.addArticle(article);
//
//        when 이것을 실행하면
//        articleRepository.save(article);

        //then 결과가 이렇게 나와야 한다.
//        Object findArticle = articleRepository.findOne(29);
//        assertEquals(article, findArticle);
    }

    @Disabled
    @Test
    public void 게시글_삭제() throws Exception {
        //given
        int removeNo = 25;
        //when
        articleRepository.remove(removeNo);
        //then
    }

    @Disabled
    @Test
    public void 게시글_수정() throws Exception {
        //given
        ArticleForm form = new ArticleForm();
        form.setBoardNo(5);
        form.setSubject("수정 테스트4");
        form.setContent("수정 테스트2");

        Board board = em.find(Board.class, form.getBoardNo());
        Object findArticle = articleRepository.findOne(27);

        //when
        Article.updateArticle((Article) findArticle, board, form);

        //then
    }

    @Disabled
    @Test
    @Transactional(readOnly = true)
    public void 게시글_단건_조회() throws Exception {
        //given
        int articleNo = 27;
        Article findArticle = (Article) articleRepository.findOne(articleNo);

        //when
        List<File> findFileList = findArticle.getFileList();
        List<Reply> findReplyList = findArticle.getReplyList();

        //then
        assertEquals(articleNo, ((Article) findArticle).getNo());
//        assertEquals(2, findReplyList.size(), "25번 게시글의 댓글의 수는 2이다.");
//        assertEquals(2, findFileList.size(), "25번 게시글의 첨부 파일의 수는 2이다.");
//        for (Reply reply : findReplyList) {
//            log.info("=================================");
//            log.info(reply.toString());
//            log.info("=================================");
//        }
//        for (File file : findFileList) {
//            log.info("=================================");
//            log.info(file.toString());
//            log.info("=================================");
//        }
    }

    @Disabled
    @Test
    @Transactional(readOnly = true)
    public void 게시글_게시판별_조회() throws Exception {
        //given
        Board findBoard = em.find(Board.class, 3);
        List<Article> articles = articleRepository.findByBoardNo(findBoard);

        //when

        //then
        assertEquals(2, articles.size());
        for (Article article : articles) {
            log.info(article.toString());
        }
    }

    @Disabled
    @Test
    @Transactional(readOnly = true)
    public void 게시글_모두_조회() throws Exception {
        //given
        List<Article> articles = articleRepository.findAll();
        //when
        //then
        assertEquals(5, articles.size());
        for (Article article : articles) {
            log.info(article.toString());
        }
    }


}

