package com.youcode.opinionhub.Repository;

import com.youcode.opinionhub.Entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Long> {
}
