package com.youcode.opinionhub.Repository;

import com.youcode.opinionhub.Entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface PublicationRepository extends JpaRepository<Publication,Long> {
}
