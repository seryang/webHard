package com.cloud.service;

import com.cloud.model.vo.*;
import com.cloud.model.MemoDao;
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

	@Autowired
	private MemoDao memoDao;

	// 페이지 당 보여줄 데이터 사이즈
	public static int pagingSize = 15;

	// 현재 Path의 (파일 / 폴더 / 페이지) 리스트 보여주기
	public Map<String, Object> getFileFolderList(String dirPath, int currentPage) throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String [] fileFolderList = new File(dirPath).list();

		List<FileAware> dataList = new ArrayList<FileAware>();

		int dirSize = 0;
		int fileSize = 0;

		for(String fileName : fileFolderList){

			File checkFile = new File(dirPath + File.separator + fileName);

			if(checkFile.isDirectory()){
				DirectoryVO dvo = new DirectoryVO();
				dvo.setDirectoryName(checkFile.getName()); // 디렉토리 이름
				dvo.setDirectoryPath(checkFile.getPath()); // 디렉토리 경로
				dataList.add(dvo);
				dirSize++;
			}else{
				FileVO fvo = new FileVO();
				fvo.setFileName(checkFile.getName()); // 파일 이름
				fvo.setFileDate(dateFormat.format(new Date(checkFile.lastModified()))); // 파일 날짜
				fvo.setFileSize(checkFile.length()); // 파일 사이즈
				String [] splitData = checkFile.getName().split("\\.");
				String fileType = splitData[ (splitData .length) - 1 ];
				fvo.setFileType(fileType); // 파일 타입
				dataList.add(fvo);
				fileSize++;
			}
		}

		// 보여줄 페이지
		int pageList = fileFolderList.length / pagingSize;
		if( (fileFolderList.length % pagingSize > 0) ){
			pageList += 1;
		}

		// 정렬 ( 폴더 먼저 )
		Collections.sort(dataList, new Comparator<FileAware>() {
			@Override
			public int compare(FileAware o1, FileAware o2) {
				return o1.isFile() ^ o2.isFile() == false ? 0 : (o1.isFile() ? 1 : -1) ;
			}
		});

		// 페이지 당 보여줄 (파일,폴더) 쪼개기
		dataList = dataList.subList((currentPage - 1) * pagingSize, Math.min(dataList.size(), currentPage * pagingSize));

		Map <String, Object> map = new HashMap<String, Object>();
		map.put("dirSize", dirSize);
		map.put("fileSize", fileSize);
		map.put("pageList", pageList);
		map.put("dataList", dataList);
		map.put("pagingSize", pagingSize);
		return map;
	}

	// 폴더 생성하기
	public boolean addFolder(String parentFolder, String childFolder) throws Exception {
		boolean flag = false;
		String fullPath = parentFolder + File.separator + childFolder;
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
		String downloadPath = path + File.separator + fileName;
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

		if(environment.getRequiredProperty("absolute.path").equals(movePath) == false){
			File file = new File(movePath);
			parentDirectory = file.getParent();
		}
		return parentDirectory;
	}

	// 메모 Histroy List
	public List<MemoHistoryVO> getMemoList(String path, String uid) throws Exception{
		MemoVO mvo = memoDao.selectMemoId(path,uid);
		return memoDao.selectMemoHistoryList(mvo);
	}

	// 메모 삽입
	public MemoHistoryVO addMemo(String path, String uid, String comment) throws Exception{
		// select 해서 id 가져오기
		MemoVO mvo = memoDao.selectMemoId(path, uid);

		// Id가 없다면
		if(mvo == null) {
			memoDao.insertMemo(new MemoVO(path, uid)); // Memo테이블에 path와 , uid 삽입
			mvo = memoDao.selectMemoId(path, uid); // MemoID 가져오기
		}

		MemoHistoryVO hvo = new MemoHistoryVO(mvo, comment,  new Date(System.currentTimeMillis()) );
		memoDao.insertMemoHistory(hvo); // History 테이블에 MemoID, memo_content, reg_date 삽입

		return hvo;
	}

	// 메모 삭제
	public void removeMemo(int no) throws Exception{
		MemoHistoryVO hvo = memoDao.selectMemoHistoryId(no);
		memoDao.deleteMemo(hvo);
	}

	// 메모 수정
	public MemoHistoryVO modifyMemo(int no, String comment) throws Exception{
		MemoHistoryVO hvo = memoDao.selectMemoHistoryId(no);
		hvo.setMemoContent(comment);
		hvo.setRegDate(new Date(System.currentTimeMillis()));
		memoDao.updateMemoHistory(hvo);
		return hvo;
	}
}