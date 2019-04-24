package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookDao guestbookDao;

	public List<GuestbookVo> getList(){
		return guestbookDao.selectList();
	}
	
	public GuestbookVo add(GuestbookVo vo){
		
		 guestbookDao.insert(vo);
		 System.out.println(vo.getNo());
		 GuestbookVo gVo = guestbookDao.selectOne(vo.getNo());
		 
		return gVo;
	}
	
	public int delete(GuestbookVo vo){
		return guestbookDao.delete(vo);
	}
	public List<GuestbookVo> ajaxList(){
		int rnum =10;
		return guestbookDao.ajaxList(rnum);
	}
	
	public List<GuestbookVo> plusList(int no){
		int rnum =10;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("rnum", rnum);
		return guestbookDao.listPlus(map);
	}
}
