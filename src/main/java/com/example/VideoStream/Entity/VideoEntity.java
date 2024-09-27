package com.example.VideoStream.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class VideoEntity {
    @Id
    private String id;
    private String title;
    private String description;
    private String type;
    private String filePath; //storing metadata in the database (fileURL)
   /* @Lob
    * @Column(columnDefinition = "LONGBLOB")
    * private byte[] data;
    * //save file completely in the database
    */
}
