package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.FileVo;

@Repository
public class FileUpLoadDao {

	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(FileVo fileVo) {
		int count = sqlSession.insert("file.insert", fileVo);
		return count;
	}
	
	public List<FileVo> selectList(){
		List<FileVo> list = sqlSession.selectList("file.selectList");
		return list; 
	}
	
	public FileVo selectOne(int no) {
		FileVo fileVo = sqlSession.selectOne("file.selectOne", no);
		return fileVo;
	}
	public int delete(Map<String, Object> map){
		int count = sqlSession.delete("file.delete", map);
		return count;
	}
}
