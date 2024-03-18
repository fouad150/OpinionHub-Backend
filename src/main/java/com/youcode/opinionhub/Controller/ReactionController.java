package com.youcode.opinionhub.Controller;

import com.youcode.opinionhub.Entity.Reaction;
import com.youcode.opinionhub.Service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/resource/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Reaction> addReaction(
            @RequestParam("publicationId") Long publicationId,
            @RequestParam("userEmail") String userEmail){
      return new ResponseEntity<>(reactionService.addReaction(publicationId,userEmail), HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> deleteReaction(
            @RequestParam("publicationId") Long publicationId,
            @RequestParam("userEmail") String userEmail) {
        reactionService.deleteReaction(publicationId,userEmail);
        Map<String, String> response = new HashMap<>();
        response.put("message", "the reaction was deleted successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
