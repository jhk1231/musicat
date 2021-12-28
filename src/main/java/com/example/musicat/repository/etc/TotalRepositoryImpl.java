package com.example.musicat.repository.etc;

import com.example.musicat.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository("totalRepository")
@RequiredArgsConstructor
public class TotalRepositoryImpl implements BaseRepository {

    private final EntityManager em;


    @Override
    public void save(Object data) {

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

    @Override
    public void remove(Integer id) {

    }
}
