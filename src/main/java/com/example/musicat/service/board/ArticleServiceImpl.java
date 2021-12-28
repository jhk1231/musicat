package com.example.musicat.service.board;

import com.example.musicat.controller.ArticleForm;
import com.example.musicat.domain.board.Article;
import com.example.musicat.domain.board.Board;
import com.example.musicat.repository.BaseRepository;
import com.example.musicat.repository.board.ArticleRepositoryImpl;
import com.example.musicat.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service("articleService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

//    private final BoardRepositoryImpl boardRepository;
    private final EntityManager em;
    private final ArticleRepositoryImpl articleRepository;


    /**
     * 1. 게시글 등록
     * 2. 파일 업로드 (persist은 필요 없음)
     * 3. 유저 작성글 + 1게시글 추가
     * @param article 추가될 게시글 Controller에서 값 settiong되어서 넘어온다.
     */
    @Override
    @Transactional
    public int save(Article article) {
        articleRepository.save(article);
//        article.getMember().addArticleCount(); // 임의로 선언 해놓은것 (member 관계자와 협의할 것)
        return article.getNo();
    }

    /**
     * 1. 게시글 세부 조회
     * 2. 조회수 증가
     * @param id 세부 조회할 게시글 no
     * @return
     */
    @Override
    public Article findOne(Integer id) {
        Article findArticle = (Article) articleRepository.findOne(id);
        findArticle.addViewcount(); // 조회수 증가
        return findArticle;
    }

    /**
     * 전체글 조회
     */
    @Override
    public List findAll() {
        List<Article> articles = articleRepository.findAll();
        return articles;
    }

    /**
     * 게시글 수정
     * @param form 수정 Data
     * @param articleNo 수정되는 게시글의 No
     */
    @Override
    @Transactional
    public void update(ArticleForm form, Integer articleNo) {
        Board updateBoard = em.find(Board.class, form.getBoardNo());
        Object findArticle = articleRepository.findOne(articleNo);
        Article.updateArticle((Article) findArticle, updateBoard, form);
    }

    /**
     * 게시글 삭제
     * @param id 수정되는 게시글 No
     */
    @Override
    @Transactional
    public void remove(Integer id) {
        articleRepository.remove(id);
    }

    /**
     * 게시판 별 게시글 목록 조회
     * @param boardNo 게시판
     * @return 게시판의 게시글
     */
    @Override
    public List<Article> findByBoardNo(int boardNo) {
        Board findBoard = em.find(Board.class, boardNo);
        List<Article> articles = articleRepository.findByBoardNo(findBoard);
        return articles;
    }
}
