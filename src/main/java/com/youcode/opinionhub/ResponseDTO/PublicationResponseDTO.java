package com.youcode.opinionhub.ResponseDTO;

import com.youcode.opinionhub.Entity.Comment;
import com.youcode.opinionhub.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class PublicationResponseDTO {
    private Long id;
    private String text;
    private String base64Image;
    private Integer likes;

    private String usedName;
    private String userEmail;
    private List<Comment> comments;
}
