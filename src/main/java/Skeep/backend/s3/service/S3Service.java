package Skeep.backend.s3.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import Skeep.backend.naverOcr.util.MultiFileUtil;
import Skeep.backend.naverOcr.util.MultipartFileResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.InputStream;
import java.time.Duration;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    public String getPresignUrl(String filename) {
        if(filename == null || filename.isEmpty()) {
            return null;
        }

        GetObjectRequest getObjectRequest= GetObjectRequest.builder()
                                                           .bucket(bucketName)
                                                           .key(filename)
                                                           .build();
        GetObjectPresignRequest getObjectPresignRequest
                = GetObjectPresignRequest.builder()
                                         .signatureDuration(Duration.ofMinutes(10))
                                         .getObjectRequest(getObjectRequest)
                                         .build();
        PresignedGetObjectRequest presignedGetObjectRequest
                = s3Presigner.presignGetObject(getObjectPresignRequest);

        String url = presignedGetObjectRequest.url().toString();

        s3Presigner.close();

        return url;
    }

    public String uploadToS3(Long userLocationId, MultipartFile image) {

        String contentType = MultiFileUtil.determineImageFormat(image);
        String mimeType;
        switch (contentType){
            case "jpg", "jpeg" -> mimeType = MediaType.IMAGE_JPEG_VALUE;
            case "png" -> mimeType = MediaType.IMAGE_PNG_VALUE;
            default -> throw BaseException.type(GlobalErrorCode.NOT_SUPPORTED_MEDIA_TYPE_ERROR);
        }

        String fileName;
        fileName = Objects.requireNonNull(image.getOriginalFilename())
                                               .split("\\.")[0] +  "_" + userLocationId.toString();

        InputStream inputStream = MultipartFileResource.getInputStream(image);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                                                            .bucket(bucketName)
                                                            .key(fileName)
                                                            .contentType(mimeType)
                                                            .build();
        PutObjectResponse putObjectResponse = s3Client.putObject(
                                                    putObjectRequest,
                                                    RequestBody.fromInputStream(inputStream, image.getSize())
                                              );

        log.info("s3 업로드 성공");
        log.info("fileName = {}", fileName);
        log.info("ETag : {}", putObjectResponse.eTag());

        return fileName;
    }
}
