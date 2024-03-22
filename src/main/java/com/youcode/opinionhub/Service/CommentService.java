package com.youcode.opinionhub.Service;

import com.youcode.opinionhub.DTO.CommentDTO;
import com.youcode.opinionhub.Entity.Comment;
import org.apache.coyote.BadRequestException;

public interface CommentService {
    Comment addComment(CommentDTO commentDTO) throws BadRequestException;
}
