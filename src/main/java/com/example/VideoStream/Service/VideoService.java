package com.example.VideoStream.Service;

import com.example.VideoStream.Entity.VideoEntity;
import com.example.VideoStream.Repository.VideoRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class VideoService {

    @Value("${video.upload.dir}") //giving the directory through application.properties
    String uploadDIR;

    private final VideoRepo videoRepo;
    public VideoService(VideoRepo videoRepo) {
        this.videoRepo = videoRepo;
    }

    public void save(String title, String description, MultipartFile file) throws IOException {
        Path filePath=Path.of(uploadDIR, file.getOriginalFilename());
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        VideoEntity video = new VideoEntity();
        video.setId(UUID.randomUUID().toString());
        video.setTitle(title);
        video.setDescription(description);
        video.setType(file.getContentType());
        video.setFilePath(filePath.toString());
//        video.setData(file.getBytes());   //save file completely in database
        videoRepo.save(video);
    }
    public Optional<Resource> getVideoResource(String title) throws MalformedURLException {
        Optional<VideoEntity> video = videoRepo.findByTitle(title);
        if (video.isPresent()) {
            VideoEntity videoEntity = video.get();
            System.out.println(videoEntity.getType());
            Path filePath = Path.of(videoEntity.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()){
                return Optional.of(resource);
            }
            deleteVideo(title);
            }
        return Optional.empty();
    }

    public Optional<Resource> getVideoResourceById(String id) throws MalformedURLException {
        Optional<VideoEntity> video = videoRepo.findById(id);
        if (video.isPresent()) {
            Resource resource = new UrlResource(Path.of(video.get().getFilePath()).toUri());
            if(resource.exists()){
                return Optional.of(resource);
            }
            videoRepo.deleteById(id);
        }
        return Optional.empty();
    }
    public ResponseEntity<Resource> getVideoById(String id, String rangeHeader) throws IOException {
        Optional<Resource> optionalResource = getVideoResourceById(id);
        VideoEntity video = videoRepo.findById(id).orElse(null);
        if (optionalResource.isPresent() && video!=null) {
            Resource resource = optionalResource.get();
            byte[] videoContent;
            try (InputStream inputStream = resource.getInputStream()) {
                videoContent = inputStream.readAllBytes();
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build();
            }

            long length = videoContent.length;
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", video.getType());
            if (rangeHeader != null) {
                HttpRange httpRange = HttpRange.parseRanges(rangeHeader).getFirst();
                long start = httpRange.getRangeStart(length);
                long end = httpRange.getRangeEnd(length);
                httpHeaders.add("Content-Range", "bytes " + start + "-" + end + "/" + length);
                httpHeaders.setContentLength(end - start + 1);
                System.out.println(start + "-" + end);
                return ResponseEntity
                        .status(HttpStatus.PARTIAL_CONTENT)
                        .headers(httpHeaders)
                        .body(new ByteArrayResource(Arrays.copyOfRange(videoContent, (int) start, (int) end + 1)));
            }
            httpHeaders.setContentLength(length);
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(httpHeaders)
                    .body(new ByteArrayResource(videoContent));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public List<VideoEntity> getAll() {
        return videoRepo.findAll();
    }
    public VideoEntity getVideoById(String id) {
        return videoRepo.findById(id).orElse(null);
    }
    public void deleteVideo(String title) {
        Optional<VideoEntity> videoEntityOptional = videoRepo.findByTitle(title);
        videoEntityOptional.ifPresent(videoRepo::delete);
    }
}
