package com.teamcloud.model.vo;

public class FileVO {
	
	private String fileName;
	private String filePath;
	private long fileSize;
	private String fileDate;
	private String fileType;
	
	public FileVO(String fileName, String filePath, long fileSize, String fileDate, String fileType) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileSize = fileSize;
		this.fileDate = fileDate;
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
		StringBuilder builder = new StringBuilder();
		builder.append("FileVO [fileName=").append(fileName).append(", filePath=").append(filePath)
				.append(", fileSize=").append(fileSize).append(", fileDate=").append(fileDate).append(", fileType=")
				.append(fileType).append("]");
		return builder.toString();
	}

	
}