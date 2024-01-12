package com.youcode.opinionhub.Repository;

import com.youcode.opinionhub.Entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication,Long> {
}
