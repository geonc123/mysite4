package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.FileUpLoadDao;
import com.javaex.vo.FileVo;

@Service
public class FileuploadService {
	
	
	@Autowired
	private FileUpLoadDao fileUpLoadDao;
	
	public FileVo restore(MultipartFile file, String comments, int user_no) {
		String saveDir = "/Users/thisisspear/Desktop/upload";

		// oreginame filename
		String orgName = file.getOriginalFilename();
		System.out.println(orgName);
		// 확장자
		String exName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		System.out.println(exName);

		// 저장 파일명
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
		System.out.println(saveName);

		// filepath
		String filePath = saveDir + "/" + saveName;
		System.out.println(filePath);

		// filesize
		long filesize = file.getSize();
		System.out.println("filesize: " + filesize);
		
		FileVo fileVo = new FileVo(user_no, comments , filePath, orgName, saveName, filesize);
		System.out.println(fileVo.toString());
		fileUpLoadDao.insert(fileVo);
		System.out.println(fileVo.getNo());
		FileVo getOne = fileUpLoadDao.selectOne(fileVo.getNo());
		// 서버파일 복사
		try {
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream(saveDir + "/"  + saveName);
			BufferedOutputStream bout = new BufferedOutputStream(out);

			bout.write(fileData);

			if (bout != null) {
				bout.close();
			}

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getOne;
	}
	
	public List<FileVo> getList(){
		List<FileVo> list = fileUpLoadDao.selectList();
		return list;
	}
	public FileVo getOne(int no) {
		FileVo vo = fileUpLoadDao.selectOne(no);
		return vo;
	}
	
	public boolean delete(int no, int user_no) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("user_no", user_no);
		FileVo fileVo = fileUpLoadDao.selectOne(no);
		if(fileVo.getUser_no()  == user_no) {
			fileUpLoadDao.delete(map);
			return true;
		}else return false;
		
	}
}
