package com.example.musicat.repository.board;

import com.example.musicat.domain.board.Article;
import com.example.musicat.domain.board.Board;
import com.example.musicat.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;

@Repository("articleRepository")
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements BaseRepository {

    private final EntityManager em;

    @Override
    public void save(Object data) {
        em.persist(data);
    }

    @Override
    public void update(Object data) {}

    @Override
    public void remove(Integer id) {
        Article removeArticle = em.find(Article.class, id);
        em.remove(removeArticle);
    }

    @Override
    public Object findOne(Integer id) {
        return em.find(Article.class, id);
    }

    @Override
    public List findAll() {
        return em.createQuery("select a from Article a", Article.class)
                .getResultList();
    }

    /**
     * 게시판 별 목록 조회
     */
    public List<Article> findByBoardNo(Board board) {
        return em.createQuery("select a from Article a where a.board = :boardNo", Article.class)
                .setParameter("boardNo", board)
                .getResultList();
    }


}
