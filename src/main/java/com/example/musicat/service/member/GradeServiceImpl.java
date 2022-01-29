package com.example.musicat.service.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.musicat.domain.member.GradeVO;
import com.example.musicat.mapper.member.GradeMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log
@Service("gradeService")
@Transactional
public class GradeServiceImpl implements GradeService{
	@Autowired private GradeMapper gradeMapper;

	// 양 ~

	@Override
	@Transactional(readOnly = true)
	public Integer retrieveGradeNo(String auth) {
		auth = auth.replace("[", "");
		auth = auth.replace("]", "");
		int gradeNo = this.gradeMapper.selectGradeNo(auth);
		log.info("auth : " + auth + " gradeNo : " + gradeNo);
		return gradeNo;
	}

	@Override
	@Transactional(readOnly = true)
	public ArrayList<GradeVO> retrieveGradeList() {
		return this.gradeMapper.selectGradeList();
	}

	// ~ 양

	@Override
	public void modifyGrade(GradeVO grade) throws Exception {
		this.gradeMapper.updateGrade(grade);
		this.gradeMapper.rearrangeMemberGrade();
	}

	@Override
	public void removeGrade(int no) throws Exception {
		this.gradeMapper.rearrangeGrade(no);
		this.gradeMapper.deleteGrade(no);
		this.gradeMapper.rearrangeMemberGrade();
	}
	
	public void sortGrade() throws Exception {
		this.gradeMapper.standGrade();
		this.gradeMapper.rearrangeMemberGrade();
	}

	@Override
	public Map<String, Object> retrieveGradeBoardList(int gradeNo) throws Exception {
		List readList = this.gradeMapper.selectReadBoardByGrade(gradeNo);
		List writeList = this.gradeMapper.selectWriteBoardByGrade(gradeNo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("read", readList);
		map.put("write", writeList);
		return map;
	}

	
}
