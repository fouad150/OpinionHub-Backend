package com.youcode.opinionhub.convertor;

import com.youcode.opinionhub.Entity.Publication;
import com.youcode.opinionhub.ResponseDTO.PublicationResponseDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class PublicationConvertor {

    public static PublicationResponseDTO convertToPublicationResponseDTO(Publication publication) throws IOException {

        PublicationResponseDTO publicationResponseDTO = PublicationResponseDTO
                .builder()
                .id(publication.getId())
                .text(publication.getText())
                .likes(publication.getLikes())
                .usedName(publication.getUser().getUsedName())
                .userEmail(publication.getUser().getEmail())
                .build();

        return loadImageForPublication(publicationResponseDTO,publication);
    }

    private static PublicationResponseDTO loadImageForPublication(PublicationResponseDTO publicationResponseDTO, Publication publication) throws IOException {
        Path imagePath = Paths.get(publication.getImage());
        byte[] imageBytes = Files.readAllBytes(imagePath);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        publicationResponseDTO.setBase64Image(base64Image);
        return publicationResponseDTO;
    }
}
