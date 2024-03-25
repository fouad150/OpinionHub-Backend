package com.youcode.opinionhub.DTO;

import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String name;
    private String usedName;
    private String email;
    private String base64Profile;
}
