package io.github.egorshramko.booking.service;

import jakarta.persistence.AccessType;
import software.amazon.awssdk.http.SdkHttpMethod;

public interface ImageService {

    /**
     * Генерирует временный URL для доступа к S3-хранилищу
     * @param bucket - название bucket в хранилище
     * @param filePath - путь к файлу
     * @param method - HTTP-метод, для которого генерируется URL
     * @return Сгенерированный URL
     */
    String generatePreSignedUrl(String bucket, String filePath,
                           SdkHttpMethod method);


    /**
     * Проверяет, существует ли изображение с заданным именем
     * @param posterImageFilename - имя файла
     * @return результат поиска файла в хранилище
     */
    Boolean imageIsUploaded(String posterImageFilename);
}
