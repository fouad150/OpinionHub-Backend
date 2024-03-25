package com.youcode.opinionhub.convertor;

import com.youcode.opinionhub.DTO.CommentResponseDTO;
import com.youcode.opinionhub.Entity.Publication;
import com.youcode.opinionhub.Entity.User;
import com.youcode.opinionhub.ResponseDTO.PublicationResponseDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PublicationConvertor {

    public static PublicationResponseDTO convertToPublicationResponseDTO(Publication publication) throws IOException {

        List<CommentResponseDTO> commentResponseDTOList;

        if (publication.getComments() != null) {
            commentResponseDTOList = publication.getComments().stream()
                    .map(comment -> {
                        try {
                            return CommentResponseDTO.builder()
                                    .id(comment.getId())
                                    .content(comment.getContent())
                                    .usedName(comment.getUser().getUsedName())
                                    .base64Profile(loadImage(comment.getUser().getPhotoPath()))
                                    .build();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
        } else {
            commentResponseDTOList = Collections.emptyList();
        }

        PublicationResponseDTO publicationResponseDTO = PublicationResponseDTO
                .builder()
                .id(publication.getId())
                .text(publication.getText())
                .likes(publication.getLikes())
                .usedName(publication.getUser().getUsedName())
                .userEmail(publication.getUser().getEmail())
                .comments(commentResponseDTOList)
                .build();

        PublicationResponseDTO publicationResponseDTO1= loadImageForPublication(publicationResponseDTO,publication);
        return loadProfileForPublication(publicationResponseDTO1,publication.getUser().getPhotoPath());
    }

    public static PublicationResponseDTO loadImageForPublication(PublicationResponseDTO publicationResponseDTO, Publication publication) throws IOException {
        String base64Image=loadImage(publication.getImage());
        publicationResponseDTO.setBase64Image(base64Image);
        return publicationResponseDTO;
    }

    public static PublicationResponseDTO loadProfileForPublication(PublicationResponseDTO publicationResponseDTO, String profilePath) throws IOException {
        String base64Image=loadImage(profilePath);
        publicationResponseDTO.setBase64Profile(base64Image);
        return publicationResponseDTO;
    }

    public static String  loadImage (String profilePath) throws IOException {
        Path imagePath = Paths.get(profilePath);
        byte[] imageBytes = Files.readAllBytes(imagePath);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return base64Image;
    }
}
