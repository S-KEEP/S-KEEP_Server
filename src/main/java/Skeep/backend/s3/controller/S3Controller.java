package Skeep.backend.s3.controller;

import Skeep.backend.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(s3Service.uploadToS3(multipartFile));
    }
}
