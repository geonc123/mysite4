package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestbookService;
import com.javaex.vo.GuestbookVo;

@Controller
@RequestMapping("/api/gb")
public class apiGuestbookController {

	
	@Autowired
	private GuestbookService guestbookService;
	
	
	@ResponseBody
	@RequestMapping(value = "/ajaxlist", method = {RequestMethod.POST,RequestMethod.GET})
	public List<GuestbookVo> ajaxlist() {
		System.out.println("get ajax_list in controller");
		List<GuestbookVo> list = guestbookService.ajaxList();
		System.out.println(list.toString());
		return list; //기존방식과 비교 ... 
	}
	
	@ResponseBody
	@RequestMapping(value = "/list", method = {RequestMethod.POST,RequestMethod.GET})
	public List<GuestbookVo> list() {
		System.out.println("get ajax_list in controller");
		List<GuestbookVo> list = guestbookService.getList();
		System.out.println(list.toString());
		return list; //기존방식과 비교 ... 
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public int delete(@RequestParam("no") int no, @RequestParam("password") String password) {
		System.out.println("delete in controller");
		System.out.println("password"+password);
		System.out.println(no);
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		System.out.println(vo.toString());
		int count = guestbookService.delete(vo);
		return count;
	}
	
	@ResponseBody
	@RequestMapping(value = "/insert", method ={RequestMethod.POST,RequestMethod.GET})
	public GuestbookVo insert(@RequestBody GuestbookVo vo) {
		
		System.out.println("insert in controller");

		System.out.println(vo.toString());
		GuestbookVo gVo = guestbookService.add(vo);
		System.out.println(gVo);
		return gVo;
	}
	
	@ResponseBody
	@RequestMapping(value = "/pluslist", method = RequestMethod.POST)
	public List<GuestbookVo> plusList(@RequestParam("no") int no) {
		System.out.println(no);
		List<GuestbookVo> list = guestbookService.plusList(no);
		return list;
	}
}
