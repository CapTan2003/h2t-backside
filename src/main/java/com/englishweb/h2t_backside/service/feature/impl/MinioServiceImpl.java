package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.service.feature.MinioService;
import io.minio.*;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MinioServiceImpl implements MinioService {

    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    private MinioClient minioClient;

    @Override
    public void createBucket() {
        try {
            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build()
            );
            if (!bucketExists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(bucketName).build()
                );
            }
        } catch (Exception e) {
            throw new CreateResourceException(
                    new HashMap<String, String>(){{
                        put("bucketName", bucketName);
                    }},
                    e.getMessage(),
                    ErrorApiCodeContent.BUCKET_CREATED_FAIL,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SeverityEnum.HIGH);
        }
    }

    @Override
    public String uploadFile(String path, String fileName, byte[] bytes, String mimeType, RandomName randomName) {
        // Generate a unique file name
        String uniqueFileName = fileName;
        if(randomName.equals(RandomName.YES)) uniqueFileName += "-" + UUID.randomUUID().toString();

        if (mimeType == null || mimeType.isEmpty()) {
            mimeType = "application/octet-stream";
        }

        // Ensure path has trailing slash if not empty
        if (path != null && !path.isEmpty() && !path.endsWith("/")) {
            path += "/";
        }

        // Create the full object name (path + filename)
        String objectName = (path != null && !path.isEmpty()) ? path + uniqueFileName : uniqueFileName;

        try {
            // Ensure bucket exists
            createBucket();

            // Upload the file
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(new java.io.ByteArrayInputStream(bytes), bytes.length, -1)
                            .contentType(mimeType)
                            .build()
            );

            // Generate and return the URL for the uploaded file
            return getStorageUrl(objectName);
        } catch (Exception e) {
            String finalMimeType = mimeType;
            throw new CreateResourceException(
                    new HashMap<String, String>(){{
                        put("minType", finalMimeType);
                        put("fileName", fileName);
                        put("objectName", objectName);
                        put("bucketName", bucketName);
                    }},
                    e.getMessage(),
                    ErrorApiCodeContent.FILE_UPLOAD_FAIL,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SeverityEnum.MEDIUM);
        }
    }

    @Override
    public String uploadFileFromBase64(String path, String base64, String fileName, RandomName randomName) {
        try {
            String[] parts = base64.split(",");
            String base64Data = parts.length > 1 ? parts[1] : parts[0];

            // Decode base64 string to bytes
            byte[] fileBytes = Base64.getDecoder().decode(base64Data);
            String mimeType = determineMimeType(base64);

            if (fileName == null || fileName.isEmpty()) {
                fileName = "file-" + System.currentTimeMillis();
            }

            return uploadFile(path, fileName, fileBytes, mimeType, randomName);
        } catch (IllegalArgumentException e) {
            throw new CreateResourceException(
                    new HashMap<String, String>() {{
                        put("error", "Invalid base64 string");
                    }},
                    e.getMessage(),
                    ErrorApiCodeContent.FILE_UPLOAD_FAIL,
                    HttpStatus.BAD_REQUEST,
                    SeverityEnum.LOW);
        }
    }

    private String determineMimeType(String base64) {
        // Example: data:mimetype;base64,actualdata
        if (base64.startsWith("data:") && base64.contains(";base64,")) {
            String mimeType = base64.substring(5, base64.indexOf(";base64,"));
            return mimeType.isEmpty() ? "application/octet-stream" : mimeType;
        }
        return "application/octet-stream";
    }

    private String getStorageUrl(String objectName) {
        return String.format("http://localhost:9000/%s/%s", bucketName, objectName);
    }

    @Override
    public byte[] getFile(String objectName) {
        try {
            InputStream fileStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            byte[] fileBytes = fileStream.readAllBytes();
            fileStream.close();
            return fileBytes;
        } catch (Exception e) {
            throw new CreateResourceException(
                    new HashMap<String, String>() {{
                        put("objectName", objectName);
                        put("bucketName", bucketName);
                    }},
                    e.getMessage(),
                    ErrorApiCodeContent.FILE_DOWNLOAD_FAIL,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SeverityEnum.MEDIUM);
        }
    }

    @Override
    public void deleteFile(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new CreateResourceException(
                    new HashMap<String, String>() {{
                        put("objectName", objectName);
                        put("bucketName", bucketName);
                    }},
                    e.getMessage(),
                    ErrorApiCodeContent.FILE_DELETE_FAIL,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SeverityEnum.MEDIUM);
        }
    }
    public enum RandomName {
        YES,
        NO;

        public static RandomName fromString(String value) {
            if (value != null) {
                for (RandomName randomName : RandomName.values()) {
                    if (value.equalsIgnoreCase(randomName.name())) {
                        return randomName;
                    }
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}
