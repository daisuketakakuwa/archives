package jp.langstack.infras.s3;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.langstack.infra.s3.S3Service;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class S3ServiceTest {
    
    @Autowired
    S3Service s3Service;

    // MEMO：target配下に展開されるフォルダ階層をgetClassLoader().getResource()で指定すればOK
    @Test
    public void uploadObjectIfNotExists() throws Exception{
        String filePath =  this.getClass().getClassLoader().getResource("resources/jp/langstack/infras/s3/sampleImageBase64.txt").getFile();
        String base64Str = Files.readString(Paths.get(filePath.substring(1)));
        s3Service.uploadObjectIfNotExists("testObject.png", base64Str);
    }

    @Test
    public void deleteObjectIfExists() {
        s3Service.deleteObjectIfExists("testObject.png");
    }
    
}
