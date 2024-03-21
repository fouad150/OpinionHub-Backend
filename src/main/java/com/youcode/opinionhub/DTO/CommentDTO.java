package com.youcode.opinionhub.DTO;

import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String content;
    private String userEmail;
    private Long publicationId;
}
