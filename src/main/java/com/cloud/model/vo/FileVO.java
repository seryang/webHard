package com.cloud.model.vo;

import com.cloud.util.ByteFormatUtil;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileVO implements FileAware{

	private int id;
	private String fileName;
	private long fileSize;
	private String fileDate;
	private String fileType;

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
		return ByteFormatUtil.byteFormat(getFileSize());
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