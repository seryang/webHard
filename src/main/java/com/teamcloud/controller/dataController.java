package com.teamcloud.controller;

import com.teamcloud.service.DataService;
import com.teamcloud.model.FileVO;
import com.teamcloud.util.DataPath;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@SessionAttributes("fileList")
public class DataController {


    @Autowired
    private DataService dataService;

    @Resource(name="dataPath")
    private DataPath dataPath;

    // 파일 업로드
    @RequestMapping(value="/uploadFile", method = RequestMethod.POST)
    public FileVO uploadFile(MultipartFile uploadfile, Model model) {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat t = new SimpleDateFormat("HH:mm:ss");

        String time = d.format(new Date(System.currentTimeMillis())) + " " + t.format(new Date(System.currentTimeMillis()));
        FileVO fileInfo = null;
        try {
            String filename = uploadfile.getOriginalFilename();
            String directory = dataPath.ABSOLUTE_PATH+dataPath.STORAGE_PATH;
            String filepath = Paths.get(directory, filename).toString();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();
            fileInfo = new FileVO(filename, dataPath.STORAGE_PATH, uploadfile.getSize(), time, uploadfile.getContentType());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    // 폴더 생성
    @RequestMapping(value="/addFolder")
    public String addFolder(@RequestParam("folderName") String folderName) {
        String checkMsg = "폴더 생성에 실패하였습니다.";
        try {
            File dir = new File(dataPath.ABSOLUTE_PATH + dataPath.STORAGE_PATH + "/" + folderName);
            if(dir.exists()){
                checkMsg = "존재하는 폴더입니다.";
            }else{
                if(dir.mkdirs()){
                    checkMsg = "폴더가 생성되었습니다.";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkMsg;
    }

    // 파일 다운로드
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public void downloadFile (@RequestParam("filePath") String path, @RequestParam("fileName") String name,HttpServletResponse response)throws Exception {
        byte fileByte[] = FileUtils.readFileToByteArray(new File("C:\\"+path+"\\"+name));
        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(name,"UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}