package com.teamcloud.controller;

import com.teamcloud.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
@SessionAttributes({"userInfo","list", "path", "parentDirectory"})
public class DataController {

    @Autowired
    private DataService dataService;

    // [수정 완료] 파일 invalid check
    @RequestMapping(value="/uploadCheck", method = RequestMethod.POST)
    @ResponseBody
    public boolean uploadFileCheck(MultipartFile uploadfile, HttpSession session) {
        boolean flag = false;
        String path = session.getAttribute("path") + "\\" +  uploadfile.getOriginalFilename();

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
}