package com.youcode.opinionhub.ResponseDTO;

import com.youcode.opinionhub.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Data
public class PublicationResponseDTO {
    private Long id;
    private String text;
    private String base64Image;
    private Integer likes;

    private String userName;
    private String userEmail;
}
