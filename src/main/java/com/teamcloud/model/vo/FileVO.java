package com.teamcloud.model.vo;

public class FileVO {
	private String fileName;
	private String fileSize;
	private String fileModify;
	private String fileType;
	
	public FileVO(String fileName, String fileSize, String fileModify, String fileType) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileModify = fileModify;
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileModify() {
		return fileModify;
	}

	public void setFileModify(String fileModify) {
		this.fileModify = fileModify;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileVO [fileName=").append(fileName).append(", fileSize=").append(fileSize)
				.append(", fileModify=").append(fileModify).append(", fileType=").append(fileType).append("]");
		return builder.toString();
	}
}