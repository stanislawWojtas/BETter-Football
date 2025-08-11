package com.sportisfun.backend.services.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;
    private final String bucketName = "better-football";

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void uploadFile(MultipartFile file, String keyName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3.putObject(
                new PutObjectRequest(bucketName, keyName, file.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.Private)
        );
    }

    public byte[] downloadFile(String keyName) throws IOException {
        S3Object object = amazonS3.getObject(bucketName, keyName);
        return object.getObjectContent().readAllBytes();
    }

    public URL generatePresignedUrl(String keyName) {
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10);
        return amazonS3.generatePresignedUrl(bucketName, keyName, expiration);
    }
}

