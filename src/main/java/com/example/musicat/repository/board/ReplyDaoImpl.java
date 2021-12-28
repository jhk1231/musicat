package com.example.musicat.repository.board;

import java.util.List;

import com.example.musicat.domain.board.ReplyVO;
import com.example.musicat.mapper.board.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("replyDao")
public class ReplyDaoImpl implements ReplyDao {

	@Autowired
	private ReplyMapper replyMapper;
	
	
	@Override
	public List<ReplyVO> selectAllReply(int articleNo) {
		return this.replyMapper.selectAllReply(articleNo);
	}

	@Override
	public void insertReply(ReplyVO reply) {
		this.replyMapper.insertReply(reply);
	}

	@Override
	public void updateReply(ReplyVO reply) {
		this.replyMapper.updateReply(reply);
	}

	@Override
	public void deleteReply(int replyNo) {
		this.replyMapper.deleteReply(replyNo);
	}
	
	@Override
	public void allDelete(int articleNo) {
		this.replyMapper.allDelete(articleNo);
	};
	
	

}