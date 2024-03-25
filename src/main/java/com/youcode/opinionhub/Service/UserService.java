package com.youcode.opinionhub.Service;

import com.youcode.opinionhub.Entity.User;

public interface UserService {
    User getUserByEmail(String email);
}
