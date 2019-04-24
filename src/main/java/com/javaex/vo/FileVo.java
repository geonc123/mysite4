package com.javaex.vo;

public class FileVo {
	private int no;
	private int user_no;
	private String comments;
	private String filepath;
	private String orgName;
	private String saveName;
	private long fileSize;
	
	public FileVo() {
		
	}

	public FileVo(int user_no, String comments, String filepath, String orgName, String saveName, long fileSize) {
		super();
		this.user_no = user_no;
		this.comments = comments;
		this.filepath = filepath;
		this.orgName = orgName;
		this.saveName = saveName;
		this.fileSize = fileSize;
	}

	public FileVo( String filepath, String orgName, String saveName, long fileSize) {
		this.filepath = filepath;
		this.orgName = orgName;
		this.saveName = saveName;
		this.fileSize = fileSize;
	}
	
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getUser_no() {
		return user_no;
	}
	
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "FileVo [no=" + no + ", user_no=" + user_no + ", comments=" + comments + ", filepath=" + filepath
				+ ", orgName=" + orgName + ", saveName=" + saveName + ", fileSize=" + fileSize + "]";
	}

	
	
	
}
