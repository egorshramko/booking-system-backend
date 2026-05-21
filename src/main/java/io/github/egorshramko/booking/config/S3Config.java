package io.github.egorshramko.booking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${aws.s3.access-key}")
    private String accessKey;

    @Value("${aws.s3.secret-key}")
    private String secretKey;

    @Value("${aws.s3.endpoint}")
    private String endpoint;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.path-style-access-enabled}")
    private Boolean pathStyleAccessEnabled;

    /**
     * Провайдер для обеспечения доступа к S3-хранилищу
     */
    @Bean
    public StaticCredentialsProvider credentialsProvider() {
        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey)
        );
    }

    @Bean
    public S3Client s3Client() {

        S3ClientBuilder s3ClientBuilder = S3Client.builder()
                .credentialsProvider(credentialsProvider())
                .region(Region.of(region));

        if (endpoint != null && !endpoint.isEmpty()) {
            s3ClientBuilder.endpointOverride(URI.create(endpoint));
        }
        s3ClientBuilder.serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(pathStyleAccessEnabled)
                        .build());

        return s3ClientBuilder.build();
    }

    @Bean
    public S3Presigner s3Presigner() {

        S3Presigner.Builder s3PresignerBuilder = S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider());

        if (endpoint != null && !endpoint.isEmpty()) {
            s3PresignerBuilder.endpointOverride(URI.create(endpoint));
        }
        s3PresignerBuilder.serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(pathStyleAccessEnabled)
                        .build());

        return s3PresignerBuilder.build();
    }

}
