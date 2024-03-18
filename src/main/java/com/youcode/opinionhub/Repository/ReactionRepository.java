package com.youcode.opinionhub.Repository;

import com.youcode.opinionhub.Entity.Publication;
import com.youcode.opinionhub.Entity.Reaction;
import com.youcode.opinionhub.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction,Long> {


    Optional<Reaction> findByPublicationAndUser(Publication publication, User user);
}
