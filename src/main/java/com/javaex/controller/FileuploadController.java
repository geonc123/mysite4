package com.javaex.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ConcurrentReferenceHashMap.ReferenceType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.FileuploadService;
import com.javaex.vo.FileVo;

@Controller
@RequestMapping("/file")
public class FileuploadController {
	
	
	@Autowired
	FileuploadService fileuploadService;
	
	@RequestMapping(value = "/form" , method = RequestMethod.GET)
	public String form() {
		
		return "fileupload/form";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public FileVo upload(@RequestParam("file") MultipartFile file, 
						 @RequestParam("comments") String comments,
						 @RequestParam("user_no") int user_no,
						 Model model) {
		System.out.println(file.getOriginalFilename());
		FileVo fileVo = fileuploadService.restore(file, comments, user_no);
		model.addAttribute("fileVo", fileVo);
		return fileVo;
	}
	
	
	@RequestMapping(value = "/list" , method = RequestMethod.GET)
	public String list() {
		
		return "fileupload/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax_list" , method = {RequestMethod.POST,RequestMethod.GET})
	public List<FileVo> ajaxList() {
		List<FileVo> list = fileuploadService.getList();
		return list;
	}
	@ResponseBody
	@RequestMapping(value = "/ajax_one", method = RequestMethod.POST)
	public FileVo ajaxOne(@RequestParam("no") int no) {
		FileVo fileVo = fileuploadService.getOne(no);
		return fileVo;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public boolean delete(@RequestParam("user_no") int user_no, @RequestParam("no") int no) {
		
		boolean result =  fileuploadService.delete(no, user_no);
		return result;
		
	}
	
}
