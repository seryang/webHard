package com.teamcloud.controller;

import com.teamcloud.model.vo.FolderTreeVO;
import com.teamcloud.service.DataService;
import com.teamcloud.service.DirectoryTreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"userInfo","list", "path", "parentDirectory"})
public class DataController {
    @Autowired
    private Environment environment;

    @Autowired
    private DirectoryTreeService directoryTreeService;

    @Autowired
    private DataService dataService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    // 파일 invalid check
    @RequestMapping(value="/uploadCheck", method = RequestMethod.POST)
    @ResponseBody
    public boolean uploadFileCheck(@RequestParam("name") String fileName, HttpSession session) {
        boolean flag = false;

        try {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1"); // 인코딩
            String path = session.getAttribute("path") + "/" +  fileName;
            if(new File(path).exists()){
                flag = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    // 파일 업로드
    @RequestMapping(value="/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public boolean uploadFile(MultipartFile uploadfile, HttpSession session) {
        boolean flag = false;
        try {
            dataService.upload(uploadfile, (String) session.getAttribute("path"));
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
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
    public String moveFolder(@RequestParam("path") String movePath, Model model){
        try {
            if(new File(movePath).exists()){
                model.addAttribute("path", movePath);
                model.addAttribute("list", dataService.getFile_Folder_List(movePath));
                model.addAttribute("parentDirectory", dataService.getParentDirectory(movePath));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "cloud";
    }

    // 바로가기 - 경로체크
    @RequestMapping(value = "/checkPath")
    @ResponseBody
    public String checkPath(@RequestParam("path") String path, Model model){
        String checkMsg = "Not found.";
        path = environment.getRequiredProperty("ABSOLUTE_PATH") +"/"+ path;

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
    public String shortCut(@RequestParam("path") String path, Model model){
        path = environment.getRequiredProperty("ABSOLUTE_PATH") +"/"+ path;

        try {
            if(new File(path).exists()){
                model.addAttribute("path", path);
                model.addAttribute("list", dataService.getFile_Folder_List(path));
                model.addAttribute("parentDirectory", dataService.getParentDirectory(path));
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
            String absolutePath = environment.getRequiredProperty("ABSOLUTE_PATH");
            FolderTreeVO root = new FolderTreeVO(absolutePath, absolutePath);
            root.setOpened(true);
            result.add(directoryTreeService.getDirectoryTree(root));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.add(new FolderTreeVO("NO FOLDER", ""));
        }
        return result;
    }
}