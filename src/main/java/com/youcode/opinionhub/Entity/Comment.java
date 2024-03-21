package com.youcode.opinionhub.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    private User user;
    /*@ManyToOne
    private Publication publication;*/
}
