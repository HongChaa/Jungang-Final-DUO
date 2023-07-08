package site.woulduduo.aws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class S3Service {

    private S3Client s3Client;

    @Value("${aws.credentials.accessKey}")
    private String assessKey;

    @Value("${aws.credentials.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.bucketName}")
    private String bucketName;


    @PostConstruct //S3랑 연결될 때 한 번만 호출되도록 해주는 어노테이션
    public void initializeAmazon(){
        AwsBasicCredentials credentials = AwsBasicCredentials.create(assessKey, secretKey);
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    public String uploadToBucket(byte[] uploadFile, String fileName){

        //업로드한 파일정보 객체 생성
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        //객체를 버켓에 업로드
        s3Client.putObject(request, RequestBody.fromBytes(uploadFile));

        //업로드한 파일의 url을 리턴
        return s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(fileName)).toString();
    }


}
