package com.example.musicat.service.etc;

import com.example.musicat.domain.etc.NotifyVO;
import com.example.musicat.repository.etc.NotifyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyService {
    @Autowired
    private NotifyDao notifyDao;

    public void insertNotify(NotifyVO notifyVo) {
        notifyDao.insertNotify(notifyVo);
    }

    public List<NotifyVO> selectNotify(int memberNo){
        return notifyDao.selectNotify(memberNo);
    }
}
