package com.teamcloud.model.vo;

public class FileVO implements FileAware{

	private int id;
	private String fileName;
	private long fileSize;
	private String fileDate;
	private String fileType;

	public FileVO(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileDate() {
		return fileDate;
	}

	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "FileVO{" +
				"fileName='" + fileName + '\'' +
				", fileSize=" + fileSize +
				", fileDate='" + fileDate + '\'' +
				", fileType='" + fileType + '\'' +
				'}';
	}

	@Override
	public boolean isFile() {
		return true;
	}

	@Override
	public String getName() {
		return getFileName();
	}

	@Override
	public String getPath() {
		return "";
	}

	@Override
	public String getSize() {
		return getFileSize() + " KB";
	}

	@Override
	public String getDate() {
		return getFileDate();
	}

	@Override
	public String getType() {
		return getFileType();
	}

}