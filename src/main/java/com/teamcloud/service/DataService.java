package com.teamcloud.service;

import com.teamcloud.model.vo.DirectoryVO;
import com.teamcloud.model.vo.FileVO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@PropertySource( value = { "classpath:application.properties" })
public class DataService {

	@Autowired
	private Environment environment;

	// 현재 Path의 (파일 / 폴더) 리스트 보여주기
	public Map<String, Object> getFile_Folder_List(String dirPath) throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String [] allFile_Folder_List = new File(dirPath).list();

		List<DirectoryVO> directoryList = new ArrayList<DirectoryVO>();
		List<FileVO> fileList = new ArrayList<FileVO>();
		File checkFile;

		for(String go : allFile_Folder_List){
			checkFile = new File(dirPath + "/" + go);
			if(checkFile.isDirectory()){
				DirectoryVO dvo = new DirectoryVO();
				dvo.setDirectoryName(checkFile.getName()); // 디렉토리 이름
				dvo.setDirectoryPath(checkFile.getPath()); // 디렉토리 경로
				directoryList.add(dvo);
			}else{
				FileVO fvo = new FileVO();
				fvo.setFileName(checkFile.getName()); // 파일 이름
				fvo.setFileDate(dateFormat.format(new Date(checkFile.lastModified()))); // 파일 날짜
				fvo.setFileSize(checkFile.length()); // 파일 사이즈
				String [] splitData = checkFile.getName().split("\\.");
				String fileType = splitData[ (splitData .length) - 1 ];
				fvo.setFileType(fileType); // 파일 타입
				fileList.add(fvo);
			}
		}
		Map <String, Object> map = new HashMap<String, Object>();
		map.put("dirSize", directoryList.size());
		map.put("fileSize", fileList.size());
		map.put("fileList",fileList);
		map.put("directoryList",directoryList);
		return map;
	}

	// 폴더 생성하기
	public boolean addFolder(String parentFolder, String childFolder) throws Exception {
		boolean flag = false;
		String fullPath = parentFolder+"/"+childFolder;

		File dir = new File(fullPath);
		if(!dir.exists()){
			flag = dir.mkdir();
		}
		return flag;
	}

	// 파일 업로드
	public void upload(MultipartFile uploadFile, String dirPath) throws Exception {
		String filename = uploadFile.getOriginalFilename();
		String filepath = Paths.get(dirPath, filename).toString();

		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
		stream.write(uploadFile.getBytes());
		stream.close();
	}

	// 파일 다운로드
	public void download(String path, String fileName, HttpServletResponse response) throws Exception {
		String downloadPath = path+"/"+fileName;
		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1"); // 인코딩
		byte fileByte[] = FileUtils.readFileToByteArray(new File(downloadPath));

		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	// 상위 폴더의 PATH 설정
	public String getParentDirectory(String movePath) throws Exception{
		String parentDirectory = "";

		if(environment.getRequiredProperty("ABSOLUTE_PATH").equals(movePath) == false){
			File file = new File(movePath);
			parentDirectory = file.getParent();
		}
		return parentDirectory;
	}
}