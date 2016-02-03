package com.teamcloud.service;

import com.teamcloud.model.DirectoryDao;
import com.teamcloud.model.FileDao;
import com.teamcloud.model.vo.DirectoryVO;
import com.teamcloud.model.vo.FileVO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
public class DataService {

	@Autowired
	private DirectoryDao directoryDao;

	@Autowired
	private FileDao fileDao;

	// 파일 업로드
	public FileVO upload(MultipartFile uploadFile, String dirPath) throws Exception {
		System.out.println("저장될 파일의 폴더 : " + dirPath);
		Date date = new Date(System.currentTimeMillis());
		String filename = uploadFile.getOriginalFilename();
		String filepath = Paths.get(dirPath, filename).toString();

		// 1) 서버에 저장하기
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
		stream.write(uploadFile.getBytes());
		stream.close();
		// 2) DB에 반영하기
		DirectoryVO dvo = directoryDao.selectId(dirPath);

		String [] splitData = filename.split("\\.");
		String fileType = splitData [(splitData .length)-1];

		FileVO fvo = new FileVO (dvo, filename, uploadFile.getSize(), date, fileType);
		fileDao.insertFile(fvo);
		fvo = new FileVO ( filename, uploadFile.getSize(), date, fileType);
		return fvo;
	}

	// 파일 다운로드
	public void download(String path, String name, HttpServletResponse response) throws Exception {
		System.out.println("다운로드 할 path : " + path);
		System.out.println("다운로드 할 파일명 : " + name);
		byte fileByte[] = FileUtils.readFileToByteArray(new java.io.File(path+name));
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(name,"UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	// 폴더 생성하기
	public boolean addFolder(String parentFolder, String childFolder) throws Exception {
		boolean flag = false;

		String fullPath = parentFolder+childFolder+"\\";

		File dir = new File(fullPath);

		if(!dir.exists()){
			if( flag = dir.mkdirs() ) {
				DirectoryVO dvo = directoryDao.selectId(parentFolder);
				directoryDao.insertFolder( new DirectoryVO(fullPath, childFolder, dvo.getId()) );
			}
		}
		return flag;
	}

	// 파일 리스트 보여주기
	public List<FileVO> getFileList(String dirPath) throws Exception{
		System.out.println("보여줄 파일 리스트의 경로 : " + dirPath);
		DirectoryVO dvo = directoryDao.selectId(dirPath);
		return fileDao.selectFileList(dvo);
	}

	// 폴더 리스트 보여주기
	public  List<DirectoryVO> getFolderList(String dirPath) throws Exception {
		System.out.println("보여줄 폴더 리스트의 경로 : " + dirPath);
		DirectoryVO dvo = directoryDao.selectId(dirPath);
		return directoryDao.selectFolderList(dvo.getId());
	}
}