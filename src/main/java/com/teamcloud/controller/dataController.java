package com.teamcloud.controller;

import com.teamcloud.model.vo.FileVO;
import com.teamcloud.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes({"userInfo","fileList", "folderList", "path"})
public class DataController {

    @Autowired
    private DataService dataService;

    // 파일 업로드
    @RequestMapping(value="/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public FileVO uploadFile(MultipartFile uploadfile, HttpSession session) {
        FileVO fileInfo = null;
        try {
            fileInfo = dataService.upload(uploadfile, (String) session.getAttribute("path"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("받아온 fileInfo : " + fileInfo);
        return fileInfo;
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
            e.printStackTrace();
        }
        return checkMsg;
    }

    // 파일 다운로드
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public void downloadFile (HttpSession session, @RequestParam("fileName") String name, HttpServletResponse response){
        try {
            dataService.download( (String) session.getAttribute("path"), name, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 경로 변경
    @RequestMapping(value = "/moveFolder", method = RequestMethod.GET)
    public String moveFolder(@RequestParam("path") String movePath, Model model){
        try {
            System.out.println("옮겨갈 경로 : " + movePath);
            model.addAttribute("path", movePath);
            model.addAttribute("fileList", dataService.getFileList(movePath));
            model.addAttribute("folderList", dataService.getFolderList(movePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "cloud";
    }
}