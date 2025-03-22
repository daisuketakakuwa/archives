package jp.langstack.infra.s3;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

/*
 AWS SDK for Java 2.x
 https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/credentials.html#credentials-chain
*/
@RequiredArgsConstructor
@Repository
public class S3Repository {

    /**
     * デフォルトで次の順番で認証情報を探しに行く 1. Javaのシステムプロパティ「aws.accessKeyId」「aws.secretKey」
     * (SystemPropertiesCredentialsProvider) 2.
     * 環境変数「AWS_ACCESS_KEY_ID」「AWS_SECRET_ACCESS_KEY」
     * (EnvironmentVariableCredentialsProvider) 5. ECSコンテナ認証情報
     * AWS_CONTAINER_CREDENTIALS_RELATIVE_URI
     */
    private final S3Client s3Client = S3Client.builder().region(Region.AP_NORTHEAST_1).build();

    public CreateBucketResponse createBucket(String bucketName) {
        CreateBucketRequest request = CreateBucketRequest.builder().bucket(bucketName).build();
        return s3Client.createBucket(request);
    }

    public Optional<Bucket> getBucket(String bucketName) {
        return s3Client.listBuckets().buckets().stream().filter(b -> b.name().equals(bucketName)).findAny();
    }

    public DeleteBucketResponse deleteBucket(String bucketName) {
        DeleteBucketRequest request = DeleteBucketRequest.builder().bucket(bucketName).build();
        return s3Client.deleteBucket(request);
    }

    public PutObjectResponse addObject(String objectKey, String bucketName, String base64Str) {
        // MEMO：base64文字列を元の文字列にデコードしてからS3にアップする
        byte[] buf = java.util.Base64.getDecoder().decode(base64Str);
        PutObjectRequest request = PutObjectRequest.builder().bucket(bucketName).key(objectKey).build();
        return s3Client.putObject(request, RequestBody.fromBytes(buf));
    }

    public DeleteObjectResponse deleteObject(String objectKey, String bucketName) {
        DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build();
        return s3Client.deleteObject(request);
    }

    public Optional<S3Object> getObject(String objectKey, String bucketName) {
        ListObjectsRequest request = ListObjectsRequest.builder().bucket(bucketName).build();
        return s3Client.listObjects(request).contents().stream().filter(o -> o.key().equals(objectKey)).findAny();
    }

}
