package com.teamcloud.model.vo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "file")
public class FileVO{

	@Id
	@GeneratedValue
	@Column(name = "file_Id")
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dir_Id", nullable = false)
	private DirectoryVO dir_Id;

	@Column(name = "name")
	private String fileName;

	@Column(name = "size")
	private long fileSize;

	@Column(name = "date")
	private Date fileDate;

	@Column(name = "type")
	private String fileType;

	public FileVO(){}

	public FileVO(String fileName, long fileSize, Date fileDate, String fileType) {
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileDate = fileDate;
		this.fileType = fileType;
	}

	public FileVO(DirectoryVO dir_Id, String fileName, long fileSize, Date fileDate, String fileType) {
		this.dir_Id = dir_Id;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileDate = fileDate;
		this.fileType = fileType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DirectoryVO getDir_Id() {
		return dir_Id;
	}

	public void setDir_Id(DirectoryVO dir_Id) {
		this.dir_Id = dir_Id;
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

	public Date getFileDate() {
		return fileDate;
	}

	public void setFileDate(Date fileDate) {
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
				"id=" + id +
				", dir_Id=" + dir_Id +
				", fileName='" + fileName + '\'' +
				", fileSize=" + fileSize +
				", fileDate=" + fileDate +
				", fileType='" + fileType + '\'' +
				'}';
	}
}