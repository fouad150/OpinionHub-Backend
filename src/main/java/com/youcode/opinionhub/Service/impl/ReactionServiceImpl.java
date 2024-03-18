package com.youcode.opinionhub.Service.impl;

import com.youcode.opinionhub.Entity.Publication;
import com.youcode.opinionhub.Entity.Reaction;
import com.youcode.opinionhub.Entity.User;
import com.youcode.opinionhub.Repository.PublicationRepository;
import com.youcode.opinionhub.Repository.ReactionRepository;
import com.youcode.opinionhub.Repository.UserRepository;
import com.youcode.opinionhub.Service.ReactionService;
import com.youcode.opinionhub.exception.DoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReactionServiceImpl implements ReactionService {
    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Reaction addReaction(Long publicationId, String userEmail){

        Optional<User> userOptional=userRepository.findByEmail(userEmail);
        Optional<Publication> publicationOptional = publicationRepository.findById(publicationId);
        if(userOptional.isEmpty()){
            throw new DoesNotExistException("this user doesn't exist");
        }

        if(publicationOptional.isEmpty()){
            throw new DoesNotExistException("this post doesn't exist");
        }

        Publication publication=publicationOptional.get();
        publication.setLikes(publication.getLikes()+1);

        Reaction reaction = Reaction.builder()
                .publication(publication)
                .user(userOptional.get())
                .build();
        return  reactionRepository.save(reaction);

    }

    @Override
    public void deleteReaction(Long publicationId, String userEmail){

        Optional<User> userOptional=userRepository.findByEmail(userEmail);
        Optional<Publication> publicationOptional = publicationRepository.findById(publicationId);
        if(userOptional.isEmpty()){
            throw new DoesNotExistException("this user doesn't exist");
        }

        if(publicationOptional.isEmpty()){
            throw new DoesNotExistException("this post doesn't exist");
        }

        Publication publication=publicationOptional.get();
        publication.setLikes(publication.getLikes()-1);

        User user= userOptional.get();

        Optional<Reaction> reactionOptional = reactionRepository.findByPublicationAndUser(publication,user);
        if(reactionOptional.isEmpty()){
            throw new DoesNotExistException("this user didn't like this post.");
        }
        reactionRepository.delete(reactionOptional.get());

    }

}
