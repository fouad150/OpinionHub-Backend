package com.youcode.opinionhub.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String image;
    private Integer likes;
    @ManyToOne()
    private User user;
    /*@OneToMany(fetch = FetchType.EAGER)
    private List<Reaction> reactions;*/
}
