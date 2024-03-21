package com.youcode.opinionhub.Service.impl;

import com.youcode.opinionhub.DTO.CommentDTO;
import com.youcode.opinionhub.Entity.Comment;
import com.youcode.opinionhub.Entity.Publication;
import com.youcode.opinionhub.Entity.User;
import com.youcode.opinionhub.Repository.CommentRepository;
import com.youcode.opinionhub.Repository.PublicationRepository;
import com.youcode.opinionhub.Repository.UserRepository;
import com.youcode.opinionhub.Service.CommentService;
import com.youcode.opinionhub.exception.DoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment addComment(CommentDTO commentDTO){

        Optional<Publication> publicationOptional = publicationRepository.findById(commentDTO.getPublicationId());
        Optional<User> userOptional = userRepository.findByEmail(commentDTO.getUserEmail());
        if (publicationOptional.isPresent()) {
            if(userOptional.isPresent()){
                Publication publication = publicationOptional.get();
                System.out.println("publication: "+publication);
                User user = userOptional.get();
                System.out.println("user: "+user);
                Comment comment = Comment.builder()
                        .content(commentDTO.getContent())
                        .user(user)
                        .build();
                List<Comment> comments = publication.getComments();
                System.out.println("comments: "+comments);
                Comment savedComment = commentRepository.save(comment);
                comments.add(savedComment);
                publication.setComments(comments);
                System.out.println("saved publication: "+ publicationRepository.save(publication));
                return savedComment;
            } else {
                throw new DoesNotExistException("This user doesn't exist");
            }
        } else {
            throw new DoesNotExistException("This post doesn't exist");
        }
    }

}
