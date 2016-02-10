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

	// [수정완료] 현재 Path의 (파일 / 폴더) 리스트 보여주기
	public Map<String, List> getFile_Folder_List(String dirPath) throws Exception{
		System.out.println("보여줄 리스트의 경로 : " + dirPath);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<DirectoryVO> directoryList = new ArrayList<DirectoryVO>();
		List<FileVO> fileList = new ArrayList<FileVO>();

		String [] allFile_Folder_List = new File(dirPath).list();
		File checkFile;
		for(String go : allFile_Folder_List){

			//1) Windows Version
//			checkFile = new File(dirPath + "\\" + go);

			//2) Mac Version
			checkFile = new File(dirPath + "/" + go);

			if(checkFile.isDirectory()){
				DirectoryVO dvo = new DirectoryVO();
				// 디렉토리 이름
				dvo.setDirectoryName(checkFile.getName());
				// 디렉토리 경로
				dvo.setDirectoryPath(checkFile.getPath());
				directoryList.add(dvo);
			}else{
				FileVO fvo = new FileVO();
				// 파일 이름
				fvo.setFileName(checkFile.getName());
				// 파일 날짜
				fvo.setFileDate(dateFormat.format(new Date(checkFile.lastModified())));
				// 파일 사이즈
				fvo.setFileSize(checkFile.length());
				// 파일 타입
				String [] splitData = checkFile.getName().split("\\.");
				String fileType = splitData[ (splitData .length) - 1 ];
				fvo.setFileType(fileType);
				fileList.add(fvo);
			}
		}
		Map <String, List> map = new HashMap<String, List>();
		map.put("fileList",fileList);
		map.put("directoryList",directoryList);
		return map;
	}

	// [수정완료] 폴더 생성하기
	public boolean addFolder(String parentFolder, String childFolder) throws Exception {
		boolean flag = false;

		// 1) Windows Version
//		String fullPath = parentFolder +"\\"+ childFolder;


		// 2) Mac Version
		String fullPath = parentFolder+"/"+childFolder;
		System.out.println("생성할 폴더 : " + fullPath);
		File dir = new File(fullPath);

		if(!dir.exists()){
			flag = dir.mkdir();
		}

		return flag;
	}

	// [수정 완료] 파일 업로드
	public void upload(MultipartFile uploadFile, String dirPath) throws Exception {
		System.out.println("저장될 파일의 폴더 : " + dirPath);

		String filename = uploadFile.getOriginalFilename();
		String filepath = Paths.get(dirPath, filename).toString();

		System.out.println("폴더명 + 파일명 : " + filepath);

		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
		stream.write(uploadFile.getBytes());
		stream.close();
	}

	// [수정 완료] 파일 다운로드 (인코딩 굿)
	public void download(String path, String fileName, HttpServletResponse response) throws Exception {
		System.out.println("다운로드 할 path : " + path);
		System.out.println("다운로드 할 파일명 : " + fileName);

//		1) Windows Version
//		String downloadPath = path+"\\"+fileName;

//		2) Mac Version
		String downloadPath = path+"/"+fileName;

		// 인코딩
		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

		System.out.println("풀 경로 : " + downloadPath);
		byte fileByte[] = FileUtils.readFileToByteArray(new File(downloadPath));
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	// [완료 상위 폴더의 PATH 설정
	public String getParentDirectory(String movePath) throws Exception{
		String parentDirectory;
		if(environment.getRequiredProperty("ABSOLUTE_PATH").equals(movePath)){
			parentDirectory = "";
		}else{
			File file = new File(movePath);
			parentDirectory = file.getParent();
		}
		System.out.println("상위 폴더 : " + parentDirectory);
		return parentDirectory;
	}
}