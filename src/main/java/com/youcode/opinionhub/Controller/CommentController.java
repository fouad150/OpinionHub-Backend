package com.youcode.opinionhub.Controller;

import com.youcode.opinionhub.DTO.CommentDTO;
import com.youcode.opinionhub.Entity.Comment;
import com.youcode.opinionhub.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/resource/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Comment>  addComment(@RequestBody CommentDTO commentDTO){

        Comment comment= commentService.addComment(commentDTO);
        return new ResponseEntity<>(comment, HttpStatus.OK);

    }
}
