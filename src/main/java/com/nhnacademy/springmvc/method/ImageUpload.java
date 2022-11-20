package com.nhnacademy.springmvc.method;


import com.nhnacademy.springmvc.exception.NotFoundFileException;
import com.nhnacademy.springmvc.exception.NotImageFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImageUpload {

    private ImageUpload() {
    }

    private static String UPLOAD_DIR = "/Users/taewon/Downloads";

    public static List<String> save(MultipartFile[] multipartFiles){
        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            if (file.getOriginalFilename().equals("")) {
                break;
            }

            if (!file.getContentType().startsWith("image")) {
                throw new NotImageFileException(file.getContentType());
            }

            String originalName = file.getOriginalFilename();
            String saveName = UPLOAD_DIR + File.separator + originalName;

            Path savePath = Paths.get(saveName);

            try {
                file.transferTo(savePath);
                fileNames.add(originalName);
            } catch (IOException e) {
                throw new NotFoundFileException(e.getMessage());
            }
        }

        return fileNames;
    }
}