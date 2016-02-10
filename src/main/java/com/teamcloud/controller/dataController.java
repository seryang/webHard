package com.teamcloud.controller;

import com.teamcloud.model.vo.FolderTreeNode;
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

    // [수정 완료] 파일 invalid check
    @RequestMapping(value="/uploadCheck", method = RequestMethod.POST)
    @ResponseBody
    public boolean uploadFileCheck(MultipartFile uploadfile, HttpSession session) {
        boolean flag = false;

//       1) Windows Version
//        String path = session.getAttribute("path") + "\\" +  uploadfile.getOriginalFilename();

//        2) Mac Version
        String path = session.getAttribute("path") + "/" +  uploadfile.getOriginalFilename();

        try{
            if(new File(path).exists()){
                flag = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    // [수정 완료] 파일 업로드
    @RequestMapping(value="/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public boolean uploadFile(MultipartFile uploadfile, HttpSession session) {
        boolean flag = false;
        try {
            dataService.upload(uploadfile, (String) session.getAttribute("path"));
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 파일 다운로드
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public void downloadFile (HttpSession session, @RequestParam("fileName") String fileName, HttpServletResponse response){
        try {
            dataService.download( (String) session.getAttribute("path"), fileName, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // [수정 완료] 폴더 생성
    @RequestMapping(value="/addFolder", method = RequestMethod.POST)
    @ResponseBody
    public String addFolder( @RequestParam("folderName") String folderName, HttpSession session) {
        System.out.println("서버에서 받은 folderName : " + folderName);
        String checkMsg = "존재하는 폴더입니다.";
        try {
            if(dataService.addFolder( (String) session.getAttribute("path"), folderName)){
                checkMsg = "폴더가 생성되었습니다.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkMsg;
    }

    // [수정 완료] 경로 변경
    @RequestMapping(value = "/moveFolder", method = RequestMethod.GET)
    public String moveFolder(@RequestParam("path") String movePath, Model model){
        try {
            System.out.println("옮겨갈 경로 : " + movePath);
            model.addAttribute("path", movePath);
            model.addAttribute("list", dataService.getFile_Folder_List(movePath));
            model.addAttribute("parentDirectory", dataService.getParentDirectory(movePath) );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "cloud";
    }

    @RequestMapping("/tree")
    @ResponseBody
    public List<FolderTreeNode> tree(@RequestParam(required = false, defaultValue = "") String selected) {
        List<FolderTreeNode> result = new ArrayList<FolderTreeNode>();
        try {
            String absolutePath = environment.getRequiredProperty("ABSOLUTE_PATH");
            FolderTreeNode root = new FolderTreeNode(absolutePath, absolutePath);
            if (selected.equals("DataStore") == false) {
                root.setOpened(true);
            }
            result.add(directoryTreeService.getDirectoryTree0(root, selected));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.add(new FolderTreeNode("NO FOLDER", ""));
        }
        return result;
    }
}