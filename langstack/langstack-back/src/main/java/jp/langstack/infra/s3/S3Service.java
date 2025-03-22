package jp.langstack.infra.s3;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class S3Service {

    private final S3Repository s3Repo;

    // TODO：プロパティファイル外だし
    private String bucketName = "langstack-bucket";
    private String region = "ap-northeast-1";

    private static final String S3_OBJECT_URL_FORMAT = "https://%s.s3.%s.amazonaws.com/%s";

    public String uploadObjectIfNotExists(String objectKey, String base64Str) {
        createBucketIfNotExists(bucketName);
        if(s3Repo.getObject(objectKey, bucketName).isEmpty()) {
            s3Repo.addObject(objectKey, bucketName, base64Str);
        }
        // 例「https://langstack-bucket.s3.ap-northeast-1.amazonaws.com/k02gi38h66d2if.jpeg」
        return String.format(S3_OBJECT_URL_FORMAT, bucketName, region, objectKey);
    }

    public void deleteObjectIfExists(String objectKey) {
        if(s3Repo.getObject(objectKey, bucketName).isPresent()) {
            s3Repo.deleteObject(objectKey, bucketName);
        }
    }

    private void createBucketIfNotExists(String bucketName) {
        if(s3Repo.getBucket(bucketName).isEmpty()) {
            s3Repo.createBucket(bucketName);
        }
    }
    
}
