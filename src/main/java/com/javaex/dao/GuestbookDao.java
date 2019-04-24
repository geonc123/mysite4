package com.javaex.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> selectList() {
		return sqlSession.selectList("guestbook.selectList");
	}

	public int insert(GuestbookVo vo) {
		int count = sqlSession.insert("guestbook.insert", vo);
		System.out.println(vo.getNo());
		return count;
	}

	public int delete(GuestbookVo vo) {
		return sqlSession.delete("guestbook.delete", vo);
	}

	
	public List<GuestbookVo> ajaxList(int rnum){
		return sqlSession.selectList("guestbook.ajaxList", rnum);
	}
	
	public List<GuestbookVo> listPlus(Map<String, Object> map){
		return sqlSession.selectList("guestbook.selectplus", map);
	}
	
	public GuestbookVo selectOne(int no) {
		GuestbookVo vo = sqlSession.selectOne("guestbook.selectOne", no);
		return vo;
	}
}
