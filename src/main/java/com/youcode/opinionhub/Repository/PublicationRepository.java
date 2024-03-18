package com.youcode.opinionhub.Repository;

import com.youcode.opinionhub.Entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface PublicationRepository extends JpaRepository<Publication,Long> {
    @Query("SELECT p FROM Publication p INNER JOIN Reaction r ON p.id = r.publication.id")
    List<Publication> findAllWithReactions();


}
