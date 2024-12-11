package in.zoukme.zouk_album.services.aws;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class BucketService {

  private static final Logger log = LoggerFactory.getLogger(BucketService.class);
  private final S3Client s3Client;

  public BucketService(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  public List<String> upload(String eventTitle, List<MultipartFile> files) {
    var imagesS3Paths = new ArrayList<String>();
    for (MultipartFile file : files) {
      try {
        var tempFile = File.createTempFile("temp", file.getOriginalFilename());
        var originalFilename = file.getOriginalFilename();
        file.transferTo(tempFile);
        var path = EventUtils.getEventFolderName(eventTitle) + File.separator + originalFilename;
        s3Client.putObject(
            PutObjectRequest.builder().bucket(EventUtils.BUCKET_NAME).key(path).build(),
            RequestBody.fromFile(Paths.get(tempFile.getAbsolutePath())));

        imagesS3Paths.add(
            "https://s3.sa-east-1.amazonaws.com/" + EventUtils.BUCKET_NAME + File.separator + path);
      } catch (IOException e) {
        log.error("Error uploading file to S3", e);
        throw new RuntimeException(e);
      }
    }
    return imagesS3Paths;
  }

  public void delete(List<String> pathFiles) {
    // implement a method to upload files to the S3 bucket

  }
}
