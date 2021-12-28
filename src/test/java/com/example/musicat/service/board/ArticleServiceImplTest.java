//package com.example.musicat.service.board;
//
//import com.example.musicat.domain.board.Article;
//import com.example.musicat.repository.BaseRepository;
//import com.example.musicat.service.BaseService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Slf4j
//class ArticleServiceImplTest {
//
//    @Autowired
//    @Qualifier("articleService")
//    private BaseService articleService;
//
//    @Test
//    @Transactional
//    public void 게시글_등록() throws Exception {
//        //given ~가 주어졌을 때
//        Article article = Article.createArticle(1, 3, "팩토", "리", "테스트");
//
//        //when 이것을 실행하면
//        articleService.save(article);
//
//        //then 결과가 이렇게 나와야 한다.
//    }
//}