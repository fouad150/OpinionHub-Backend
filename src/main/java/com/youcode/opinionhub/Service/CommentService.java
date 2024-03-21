package com.youcode.opinionhub.Service;

import com.youcode.opinionhub.DTO.CommentDTO;
import com.youcode.opinionhub.Entity.Comment;

public interface CommentService {
    Comment addComment(CommentDTO commentDTO);
}
