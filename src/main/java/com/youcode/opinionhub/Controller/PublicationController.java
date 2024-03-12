package com.youcode.opinionhub.Controller;

import com.youcode.opinionhub.Entity.Publication;
import com.youcode.opinionhub.Service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("api/v1/resource/publications")

public class PublicationController {

    @Autowired
    private PublicationService publicationService;


    @PostMapping()
/*
    @PreAuthorize("hasRole('User')")
*/
    public ResponseEntity<?> addPost(@RequestParam("text") String text, @RequestParam("image") MultipartFile image) throws IOException {
        Publication publication = publicationService.addPublication(text,image);
        System.out.println(publication);
        return ResponseEntity.ok("good");
    }


    @GetMapping()
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> getPublications(){
        return new ResponseEntity<>("publications", HttpStatus.OK);
    }
}
