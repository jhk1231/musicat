package com.example.musicat.repository.board;

import com.example.musicat.domain.board.Article;
import com.example.musicat.domain.board.Reply;
import com.example.musicat.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository("replyRepository")
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements BaseRepository {

    private final EntityManager em;

    @Override
    public void save(Object data) {
        em.persist(data);
    }

    @Override
    public void remove(Integer id) {
        Reply removeReply = em.find(Reply.class, (Integer)id);
        em.remove(removeReply);
    }

    @Override
    public Object findOne(Integer id) {
        return em.find(Reply.class, id);
    }

    //게시글 내의 댓글 목록 조회
    public List<Reply> findByArticleNo(Article article) {
        return em.createQuery("select r from Reply r where r.article = :article", Reply.class)
                .setParameter("article",article)
                .getResultList();
    }

    @Override
    public void update(Object data) {}



    @Override
    public List findAll() {
        return null;
    }

}
