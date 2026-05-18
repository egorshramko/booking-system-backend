package io.github.egorshramko.booking.service.impl;

import io.github.egorshramko.booking.service.ImageService;
import jakarta.persistence.AccessType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.http.SdkHttpMethod;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final S3Presigner s3Presigner;

    public ImageServiceImpl(S3Presigner s3Presigner) {
        this.s3Presigner = s3Presigner;
    }

    @Override
    public String generatePreSignedUrl(String bucketName,
                                       String filePath,
                                       SdkHttpMethod method) {
        if (method == SdkHttpMethod.GET) {
            return generateGetPreSignedUrl(bucketName, filePath);
        }
        else if (method == SdkHttpMethod.PUT) {
            return generatePutPreSignedUrl(bucketName, filePath);
        }
        else {
            throw new UnsupportedOperationException("Unsupported HTTP method: " + method);
        }
    }

    private String generateGetPreSignedUrl(String bucketName, String filePath) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(filePath)
                .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(2))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);
        return presignedGetObjectRequest.url().toString();
    }

    private String generatePutPreSignedUrl(String bucketName,
                                           String filePath) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filePath)
                .build();

        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(2))
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(putObjectPresignRequest);
        return presignedRequest.url().toString();

    }
}
