package com.youcode.opinionhub.Service;

import com.youcode.opinionhub.Entity.Publication;
import com.youcode.opinionhub.Entity.Reaction;
import com.youcode.opinionhub.ResponseDTO.PublicationResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PublicationService {
    List<Object> getPublications() throws IOException;

    public Publication addPublication(String text, MultipartFile image) throws IOException;

    List<Reaction>  findAll();

    void deletePublicationById(Long id);
}
