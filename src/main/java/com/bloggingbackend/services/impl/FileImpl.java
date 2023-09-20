package com.bloggingbackend.services.impl;

import com.bloggingbackend.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class FileImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
       String name = file.getOriginalFilename();
       String randomId = UUID.randomUUID().toString();
       String filename1 = randomId.concat(name.substring(name.lastIndexOf(".")));
       String filePath = path + File.separator + filename1;
       File file1 = new File(path);
       if(!file1.exists()){
           file1.mkdir();
       }

        Files.copy(file.getInputStream(), Paths.get(filePath));
       return filename1;
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        String fullpath = path+File.separator+filename;
        return new FileInputStream(fullpath);
    }
}
