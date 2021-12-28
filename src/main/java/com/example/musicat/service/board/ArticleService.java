package com.example.musicat.service.board;

import com.example.musicat.controller.ArticleForm;
import com.example.musicat.domain.board.Article;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleService {

    public int save(Article article);

    public Article findOne(Integer id);

    public List findAll();

    public void update(ArticleForm form, Integer articleNo);

    public void remove(Integer id);

    public List<Article> findByBoardNo(int boardNo);
}
