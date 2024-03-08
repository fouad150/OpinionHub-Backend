package com.youcode.opinionhub.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/resource/publications")

public class PublicationController {

    @GetMapping()
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> getPublications(){
        return new ResponseEntity<>("publications", HttpStatus.OK);
    }
}
