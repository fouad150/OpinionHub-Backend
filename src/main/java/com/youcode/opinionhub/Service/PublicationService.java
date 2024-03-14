package com.youcode.opinionhub.Service;

import com.youcode.opinionhub.Entity.Publication;
import com.youcode.opinionhub.ResponseDTO.PublicationResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PublicationService {
    List<PublicationResponseDTO> getPublications() throws IOException;

    public Publication addPublication(String text, MultipartFile image) throws IOException;
}
