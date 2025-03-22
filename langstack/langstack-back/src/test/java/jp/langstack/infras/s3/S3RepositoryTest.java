package jp.langstack.infras.s3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.langstack.infra.s3.S3Repository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class S3RepositoryTest {

    @Autowired
    S3Repository repository;

    String bucketName = "daisukedaisuke";     
    
    @Test
    public void createBucket() {
        repository.createBucket(bucketName);
    }

    @Test
    public void deleteBucket() {
        repository.deleteBucket(bucketName);
    }

}
