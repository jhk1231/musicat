package com.example.musicat.service.etc;

import com.example.musicat.repository.BaseRepository;
import com.example.musicat.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("totalService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TotalServiceImpl implements BaseService {

    @Qualifier("totalRepository")
    private final BaseRepository totalRepository;

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
