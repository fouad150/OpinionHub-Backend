package com.youcode.opinionhub.Repository;

import com.youcode.opinionhub.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
