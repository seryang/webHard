package com.cloud.controller;

import com.cloud.model.vo.FolderTreeVO;
import com.cloud.model.vo.MemoHistoryVO;
import com.cloud.model.vo.UserVO;
import com.cloud.service.DataService;
import com.cloud.service.DirectoryTreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DataController {
    @Autowired
    private Environment environment;

    @Autowired
    private DirectoryTreeService directoryTreeService;

    @Autowired
    private DataService dataService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 파일 invalid check
    @RequestMapping(value="/uploadCheck", method = RequestMethod.POST)
    @ResponseBody
    public boolean uploadFileCheck(@RequestParam("name") String fileName, HttpSession session) {
        boolean result = false;

        try {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1"); // 인코딩
            String path = session.getAttribute("path") + File.separator +  fileName;
            result = new File(path).exists();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    // 파일 업로드
    @RequestMapping(value="/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public boolean uploadFile(MultipartFile uploadfile, HttpSession session) {
        boolean result = false;
        try {
            dataService.upload(uploadfile, (String) session.getAttribute("path"));
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    // 파일 다운로드
    @RequestMapping(value = "/downloadFile")
    public void downloadFile (HttpSession session, @RequestParam("fileName") String fileName, HttpServletResponse response){
        try {
            dataService.download( (String) session.getAttribute("path"), fileName, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 폴더 생성
    @RequestMapping(value="/addFolder", method = RequestMethod.POST)
    @ResponseBody
    public String addFolder( @RequestParam("folderName") String folderName, HttpSession session) {
        String checkMsg = "존재하는 폴더입니다.";

        try {
            if(dataService.addFolder( (String) session.getAttribute("path"), folderName)){
                checkMsg = "폴더가 생성되었습니다.";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return checkMsg;
    }

    // 경로 변경
    @RequestMapping(value = "/moveFolder")
    public String moveFolder(@RequestParam("path") String movePath, HttpSession session,
                             @RequestParam(required = false, defaultValue = "1", value = "currentPage") int currentPage){
        try {
            if(new File(movePath).exists()){
                session.setAttribute("path", movePath);
                session.setAttribute("list", dataService.getFileFolderList(movePath, currentPage));
                session.setAttribute("parentDirectory", dataService.getParentDirectory(movePath));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "cloud";
    }

    // 바로가기 - 경로체크
    @RequestMapping(value = "/checkPath")
    @ResponseBody
    public String checkPath(@RequestParam("path") String path){
        String checkMsg = "Not found.";
        path = environment.getRequiredProperty("absolute.path") + File.separator + path;

        try {
            if(new File(path).exists()){
                checkMsg = "Find !";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return checkMsg;
    }

    // 바로가기
    @RequestMapping(value = "/shortCut")
    public String shortCut(@RequestParam("path") String path, HttpSession session,
                           @RequestParam(required = false, defaultValue = "1", value = "currentPage") int currentPage){
        path = environment.getRequiredProperty("absolute.path") + File.separator + path;

        try {
            if(new File(path).exists()){
                session.setAttribute("list", dataService.getFileFolderList(path, currentPage));
                session.setAttribute("parentDirectory", dataService.getParentDirectory(path));
                session.setAttribute("path", path);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "cloud";
    }

    // 폴더 트리
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<FolderTreeVO> tree() {
        List<FolderTreeVO> result = new ArrayList<FolderTreeVO>();
        try {
            String absolutePath = environment.getRequiredProperty("absolute.path");
            FolderTreeVO root = new FolderTreeVO(absolutePath, absolutePath);
            root.setOpened(true);
            result.add(directoryTreeService.getDirectoryTree(root));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.add(new FolderTreeVO("NO FOLDER", ""));
        }
        return result;
    }

    // 메모
    @RequestMapping(value = "/memo")
    @ResponseBody
    public List<MemoHistoryVO> memo(@RequestParam("path") String path, @RequestParam("type") String type, HttpSession session) {

        if(type.equals("file")){
            path = session.getAttribute("path") + File.separator + path;
        }

        List<MemoHistoryVO> list = null;

        try {
            list = dataService.getMemoList(path, ( ((UserVO)session.getAttribute("userInfo")).getEmail() ) );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        logger.info("Memo List : {}", list);

        return list;
    }

    // 메모 작성
    @RequestMapping(value ="/writeMemo")
    @ResponseBody
    public MemoHistoryVO writeMemo(@RequestParam("path") String path, @RequestParam("type") String type, HttpSession session, @RequestParam("comment") String comment){
        if(type.equals("file")){
            path = session.getAttribute("path") + File.separator + path;
        }

        MemoHistoryVO hvo = null;

        try{
            hvo = dataService.addMemo( path, ( ((UserVO)session.getAttribute("userInfo")).getEmail() ), comment);
            logger.info("hvo : {}", hvo);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return hvo;
    }

    // 메모 삭제
    @RequestMapping(value ="/removeMemo")
    @ResponseBody
    public String removeMemo(@RequestParam("no") int no){
        String checkMsg = "삭제에 실패하였습니다.";
        try{
            dataService.removeMemo(no);
            checkMsg = "메모가 삭제되었습니다.";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return checkMsg;
    }

    // 메모 수정
    @RequestMapping(value ="/modifyMemo")
    @ResponseBody
    public MemoHistoryVO modifyMemo(@RequestParam("no")int no, @RequestParam("comment") String comment){
        MemoHistoryVO hvo = null;
        try{
            hvo = dataService.modifyMemo(no, comment);
            logger.info("hvo : {}", hvo);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return hvo;
    }

    // 페이지당 보여줄 list 개수 변경
    @RequestMapping(value ="/changePage")
    @ResponseBody
    public String changePagingCount(@RequestParam("count")int count){
        dataService.pagingSize = count;
        String checkMsg = "페이지당 보여줄 데이터의 수가 " + count + "로 변경되었습니다.";
        return checkMsg;
    }
}
