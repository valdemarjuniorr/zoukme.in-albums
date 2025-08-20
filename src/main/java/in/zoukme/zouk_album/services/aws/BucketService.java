package in.zoukme.zouk_album.services.aws;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CommonPrefix;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class BucketService {

  @Value("${spring.cloud.aws.endpoint}")
  private String awsEndpoint;

  private static final Logger log = LoggerFactory.getLogger(BucketService.class);
  private final S3Client s3Client;

  public BucketService(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  public BucketImage upload(String eventTitle, MultipartFile file) {
    try {
      var tempFile = File.createTempFile("temp", file.getOriginalFilename());
      var originalFilename = file.getOriginalFilename();
      file.transferTo(tempFile);
      var eventFolderName = EventUtils.getEventFolderName(eventTitle);
      var path = eventFolderName + File.separator + originalFilename;
      s3Client.putObject(
          builder -> builder.bucket(EventUtils.BUCKET_NAME).key(path),
          RequestBody.fromFile(Path.of(tempFile.getAbsolutePath())));

      return new BucketImage(
          EventUtils.getFormatEventName(eventTitle),
          awsEndpoint + EventUtils.BUCKET_NAME + File.separator + path);
    } catch (IOException e) {
      log.error("Error uploading file to S3", e);
      throw new RuntimeException(e);
    }
  }

  public List<BucketImage> upload(String eventTitle, List<MultipartFile> files) {
    if (CollectionUtils.isEmpty(files)) {
      return List.of();
    }
    var imagesS3Paths = new ArrayList<BucketImage>();
    for (MultipartFile file : files) {
      var image = upload(eventTitle, file);
      imagesS3Paths.add(image);
    }
    return imagesS3Paths;
  }

  public void deleteFolder(String eventTitle) {
    var objects =
        s3Client
            .listObjectsV2(
                b ->
                    b.bucket(EventUtils.BUCKET_NAME)
                        .prefix(EventUtils.getEventFolderName(eventTitle)))
            .contents();
    objects.forEach(
        object ->
            s3Client.deleteObject(
                DeleteObjectRequest.builder()
                    .bucket(EventUtils.BUCKET_NAME)
                    .key(object.key())
                    .build()));
  }

  /** Get the list of folders from a given path. For example, "events/zoukme-in/" */
  public List<String> getFoldersNamesBy(String folderPath) {
    return s3Client
        .listObjectsV2(
            b ->
                b.bucket(EventUtils.BUCKET_NAME)
                    .prefix(folderPath + File.separator)
                    .delimiter(File.separator))
        .commonPrefixes()
        .stream()
        .map(CommonPrefix::prefix)
        .toList();
  }

  /** Get the list of files from a given path. For example, "events/zoukme-in/2021-09-25" */
  public List<String> getFilesNamesBy(String folderPath) {
    return s3Client
        .listObjectsV2(
            b -> b.bucket(EventUtils.BUCKET_NAME).prefix(folderPath).delimiter(File.separator))
        .contents()
        .stream()
        .map(S3Object::key)
        .filter(key -> !key.endsWith(File.separator))
        .toList();
  }

  public String getBucketUri() {
    return awsEndpoint + EventUtils.BUCKET_NAME + File.separator;
  }

  /** delete photo from event which is in the next-events folder */
  public void deletePhotoBy(String eventUrl, String fileName) {
    s3Client.deleteObject(
        DeleteObjectRequest.builder()
            .bucket(EventUtils.BUCKET_NAME)
            .key(EventUtils.getEventFolderName(eventUrl) + File.separator + fileName)
            .build());
  }
}
