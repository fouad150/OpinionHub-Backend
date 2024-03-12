package com.youcode.opinionhub.Service;

import com.youcode.opinionhub.Entity.Publication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PublicationService {
    public Publication addPublication(String text,MultipartFile image) throws IOException;
}
