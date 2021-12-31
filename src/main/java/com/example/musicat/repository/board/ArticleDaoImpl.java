package com.example.musicat.repository.board;

import java.util.List;
import java.util.Map;

import com.example.musicat.domain.board.ArticleVO;
import com.example.musicat.domain.board.SelectArticleVO;
import com.example.musicat.domain.board.TagVO;
import com.example.musicat.mapper.board.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("articleDao")
public class ArticleDaoImpl implements ArticleDao {

	private ArticleMapper articleMapper;
	
	@Autowired
	public ArticleDaoImpl(ArticleMapper articleMapper) {
		this.articleMapper = articleMapper;
	}
	
	
	@Override
	public List<SelectArticleVO> selectArticle(int articleNo) {
		return this.articleMapper.selectArticle(articleNo);
	}

	@Override
	public List<ArticleVO> selectBoard(int boardNo) {
		return this.articleMapper.selectBoard(boardNo);
	}

	@Override
	public void insertArticle(ArticleVO article) {
		this.articleMapper.insertArticle(article);
	}

	
	@Override
	public void updateArticle(ArticleVO article) {
		this.articleMapper.updateArticle(article);
	}

	@Override
	public void deleteArticle(int articleNo) {
		this.articleMapper.deleteArticle(articleNo);

	}
	
	@Override
	public void upViewcount(int articleNo) {
		this.articleMapper.upViewcount(articleNo);
	}
	
	@Override
	public List<ArticleVO> selectAllArticle() {
		return this.articleMapper.selectAllArticle();
	}

	// 추천
	@Override
	public void insertLike(ArticleVO articleVO) {
		this.articleMapper.insertLike(articleVO);
	}
//	
//	// 추천 취소
	@Override
	public void deleteLike(ArticleVO articleVO) {
		this.articleMapper.deleteLike(articleVO);
	}
//	
	@Override
	public int totalRecCount(int articleNo) {
		return this.articleMapper.totalLikeCount(articleNo);
	}
	
	public int likeCheck(ArticleVO articleVO) {
		return this.articleMapper.likeCheck(articleVO);
	}
	
	@Override
	public void upLikecount(int articleNo) {
		this.articleMapper.upLikecount(articleNo);
	}
	
	@Override
	public void downLikecount(int articleNo) {
		this.articleMapper.downLikecount(articleNo);
	}

	@Override
	public void insertTags(int articleNo, String[] tagList) {
		for (int i = 0; i < tagList.length; i++) {
			TagVO tagVO = new TagVO(articleNo, tagList[i].substring(0, tagList[i].length()-1));
			this.articleMapper.insertTag(tagVO);
		}
	}

	@Override
	public List<TagVO> selectArticleTags(int articleNo) {
		return articleMapper.selectArticleTags(articleNo);
	}

	@Override
	public void deleteTag(int tagNo) {
		this.articleMapper.deleteTag(tagNo);
	}

	@Override
	public List<ArticleVO> search(Map<String, String> map) {
		return this.articleMapper.search(map);
	}
}
