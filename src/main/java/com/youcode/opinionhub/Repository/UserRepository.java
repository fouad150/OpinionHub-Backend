package com.youcode.opinionhub.Repository;

import com.youcode.opinionhub.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
