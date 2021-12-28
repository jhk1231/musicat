package com.example.musicat.repository.board;

import com.example.musicat.domain.board.File;
import com.example.musicat.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository("fileRepository")
@RequiredArgsConstructor
public class FileRepositoryImpl implements BaseRepository {

    private final EntityManager em;

    @Override
    public void save(Object data) {
        em.persist(data);
    }

    @Override
    public void remove(Integer id) {
        File removeFile = em.find(File.class, (Integer)id);
        em.remove(removeFile);
    }

    @Override
    public Object findOne(Integer id) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public void update(Object data) {

    }


}
