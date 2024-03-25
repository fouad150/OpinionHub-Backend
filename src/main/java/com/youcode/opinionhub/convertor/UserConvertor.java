package com.youcode.opinionhub.convertor;

import com.youcode.opinionhub.DTO.UserResponseDTO;
import com.youcode.opinionhub.Entity.User;

import java.io.IOException;

import static com.youcode.opinionhub.convertor.PublicationConvertor.loadImage;


public class UserConvertor {

    public static UserResponseDTO convertToUserResponseDTO(User user) throws IOException {

        String base64Image=loadImage(user.getPhotoPath());

        return UserResponseDTO.builder()
                .name(user.getName())
                .usedName(user.getUsedName())
                .email(user.getEmail())
                .base64Profile(base64Image)
                .build();

    }
}
