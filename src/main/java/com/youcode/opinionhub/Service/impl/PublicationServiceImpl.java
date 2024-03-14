package com.youcode.opinionhub.Service.impl;

import com.youcode.opinionhub.Entity.Publication;
import com.youcode.opinionhub.Entity.User;
import com.youcode.opinionhub.Repository.PublicationRepository;
import com.youcode.opinionhub.ResponseDTO.PublicationResponseDTO;
import com.youcode.opinionhub.Service.PublicationService;
import com.youcode.opinionhub.convertor.PublicationConvertor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${upload.folder}")
    private String uploadFolder;

    @Override
    public List<PublicationResponseDTO> getPublications() throws IOException {
        List<Publication> publications = publicationRepository.findAll();
        List<PublicationResponseDTO> publicationResponseDTOList = new ArrayList<>();
        for (Publication publication : publications) {
            PublicationResponseDTO publicationResponseDTO = PublicationConvertor.convertToPublicationResponseDTO(publication);
            publicationResponseDTOList.add(publicationResponseDTO);
        }

        return publicationResponseDTOList;
    }

    public Publication addPublication(String text, MultipartFile image) throws IOException {
        if (text == null || text.isEmpty() || image == null || image.isEmpty()) {
            throw new BadRequestException("Text and image are required.");
        }

        try {
            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
            byte[] bytes = image.getBytes();
            Path path = Paths.get(uploadFolder + File.separator + filename);
            Files.write(path, bytes);


            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user=(User) userDetails;
            Publication publication= Publication.builder().user(user).text(text).image(path.toString()).likes(0).build();
            return publicationRepository.save(publication);
        } catch (IOException e) {
           throw new IOException("failed to save the image");
        }
    }

}
