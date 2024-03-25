package com.youcode.opinionhub.Service.impl;

import com.youcode.opinionhub.Entity.Publication;
import com.youcode.opinionhub.Entity.Reaction;
import com.youcode.opinionhub.Entity.User;
import com.youcode.opinionhub.Repository.PublicationRepository;
import com.youcode.opinionhub.Repository.ReactionRepository;
import com.youcode.opinionhub.ResponseDTO.PublicationResponseDTO;
import com.youcode.opinionhub.Service.PublicationService;
import com.youcode.opinionhub.convertor.PublicationConvertor;
import com.youcode.opinionhub.exception.DoesNotExistException;
import com.youcode.opinionhub.exception.DoesntHavePermission;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${upload.folder}")
    private String uploadFolder;

    @Override
    public List<Object> getPublications() throws IOException {
        List<Publication> foundPublications = publicationRepository.findAll();
        List<PublicationResponseDTO> publications = new ArrayList<>();
        for (Publication foundPublication : foundPublications) {
            PublicationResponseDTO publication = PublicationConvertor.convertToPublicationResponseDTO(foundPublication);
            publications.add(publication);
        }

        List<Reaction> reactions=this.reactionRepository.findAll();

        return List.of(publications,reactions);
    }

    @Override
    public Publication addPublication(String text, MultipartFile image) throws IOException {
        if (text == null || text.isEmpty() || image == null || image.isEmpty()) {
            throw new BadRequestException("Text and image are required.");
        }

        try {
            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
            byte[] bytes = image.getBytes();
            Path path = Paths.get(uploadFolder + File.separator + filename);
            Files.write(path, bytes);


            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user=(User) userDetails;
            Publication publication= Publication.builder().user(user).text(text).image(path.toString()).likes(0).build();
            return publicationRepository.save(publication);
        } catch (IOException e) {
           throw new IOException("failed to save the image");
        }
    }

    @Override
    public List<Reaction>  findAll(){
/*
        List<Publication> publications = this.publicationRepository.findAllWithReactions();
*/
       return this.reactionRepository.findAll();
    }

    @Override
    @Transactional
    public void deletePublicationById(Long publicationId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        User user = (User) principal;
        String email = user.getEmail();
        System.out.println("User's email: " + email);

        Optional<Publication> publicationOptional=this.publicationRepository.findById(publicationId);
        if(publicationOptional.isEmpty()){
            throw new DoesNotExistException("this post doesn't exist");
        }

        Publication foundPublication=publicationOptional.get();

        if(!email.equals(foundPublication.getUser().getEmail())){
            throw new DoesntHavePermission("you can't delete this post");
        }

        this.reactionRepository.deleteByPublicationId(publicationId);
        this.publicationRepository.deleteById(publicationId);
    }
}
