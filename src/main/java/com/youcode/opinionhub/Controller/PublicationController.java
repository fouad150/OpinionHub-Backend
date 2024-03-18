package com.youcode.opinionhub.Controller;

import com.youcode.opinionhub.Entity.Publication;
import com.youcode.opinionhub.Repository.PublicationRepository;
import com.youcode.opinionhub.ResponseDTO.PublicationResponseDTO;
import com.youcode.opinionhub.Service.PublicationService;
import com.youcode.opinionhub.convertor.PublicationConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("api/v1/resource/publications")

public class PublicationController {

    @Autowired
    private PublicationService publicationService;



    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addPost(@RequestParam("text") String text, @RequestParam("image") MultipartFile image) throws IOException {
        if (text == null || text.isEmpty()) {
            text = " ";
        }
        Publication addedPublication = publicationService.addPublication(text,image);
        PublicationResponseDTO publication = PublicationConvertor.convertToPublicationResponseDTO(addedPublication);
        return new ResponseEntity<>(publication,HttpStatus.OK);
    }


    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getPublications() throws IOException {
/*
        return new ResponseEntity<>(this.publicationService.findAll(),HttpStatus.OK);
*/
        List<Object> publications=publicationService.getPublications();
        return new ResponseEntity<>(publications, HttpStatus.OK);

    }
}
