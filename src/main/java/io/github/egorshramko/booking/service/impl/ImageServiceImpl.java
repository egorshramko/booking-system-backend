package io.github.egorshramko.booking.service.impl;

import io.github.egorshramko.booking.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.http.SdkHttpMethod;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
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

    @Autowired
    private S3Presigner s3Presigner;

    @Autowired
    private S3Client s3Client;

    @Value("${aws.buckets.movie-service}")
    private String s3BucketName;

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

    @Override
    public Boolean imageIsUploaded(String posterImageFilename) {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(s3BucketName)
                .key(posterImageFilename)
                .build();
        try {
            s3Client.headObject(headObjectRequest);

            return true;
        }
        catch(NoSuchKeyException exception) {
            //если файл не найден, то возвращаем false
            return false;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
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
