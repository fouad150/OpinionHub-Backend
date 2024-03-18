package com.youcode.opinionhub.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Publication publication;
    @ManyToOne
    private User user;
}
