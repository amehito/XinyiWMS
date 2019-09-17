package com.xinyi.controller;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xinyi.bean.XinyiImage;
import com.xinyi.dao.XinyiImageMapper;

@Controller
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
    @Autowired
    XinyiImageMapper imageMapper;
    @GetMapping("/upload")
    public String upload() {
        return "upload"; 	
    }

    @PostMapping("/upload")
    @ResponseBody
    @Transactional
    public void upload(@RequestParam("file") MultipartFile file
    		,@RequestParam("id") String id,@RequestParam("name") String name) {
        if (file.isEmpty()) {
        	return ;
        }
        double random = Math.floor(Math.random()*1000);
        String fileName = random+file.getOriginalFilename();
        File dest = new File(fileName);
        try {
            file.transferTo(dest);
            LOGGER.info("上传成功");
            System.out.println(dest.getAbsolutePath());
            XinyiImage record = new XinyiImage();
            record.setImageName(fileName);
            record.setMaterialId(id);
            record.setPlus(name);
            imageMapper.insert(record);
            LOGGER.info(id+" 插入数据库 "+name);
            
            return;
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
    }

    
}