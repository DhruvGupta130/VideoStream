package com.example.VideoStream.Controller;

import com.example.VideoStream.Entity.VideoEntity;
import com.example.VideoStream.Service.VideoService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videos")
public class VideoController {

    VideoService videoService;
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam String title, @RequestParam String description, @RequestParam MultipartFile file) {
        try {
            videoService.save(title, description, file);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Video uploaded successfully!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while uploading the video!");
        }
    }
    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam String title) {
        try {
            Optional<Resource> optional = videoService.getVideoResource(title);
            if(optional.isPresent()) {
                Resource resource = optional.get();
                return ResponseEntity.ok().header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            }
            return ResponseEntity.status(404).build();
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/stream")
    public ResponseEntity<Resource> stream(@RequestParam String id) {
        try {
            Optional<Resource> optional = videoService.getVideoResourceById(id);
            VideoEntity video = videoService.getVideoById(id);
            if(optional.isPresent() && video != null) {
                Resource resource = optional.get();
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.parseMediaType(video.getType()))
                        .body(resource);
            }
            return ResponseEntity.status(404).build();
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/bigStream")
    public ResponseEntity<Resource> stream(@RequestParam String id, @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {
        return videoService.getVideoById(id, rangeHeader);
    }
    @GetMapping
    public ResponseEntity<List<VideoEntity>> list() {
        return ResponseEntity.ok().body(videoService.getAll());
    }
}
