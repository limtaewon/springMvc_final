package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.exception.NotFoundFileException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class ImageController {
    private final String UPLOAD_DIR = "/Users/taewon/Downloads";


    @GetMapping("/image/{fileName}")
    public ResponseEntity<byte[]> imageView(@PathVariable("fileName") String fileName) {
        File file = new File(UPLOAD_DIR + File.separator + fileName);
        ResponseEntity<byte[]> result = null;

        try {
            HttpHeaders header = new HttpHeaders();
            header.add("Content-type", Files.probeContentType(file.toPath()));

            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (IOException e) {
            throw new NotFoundFileException(e.getMessage());
        }

        return result;
    }
}
