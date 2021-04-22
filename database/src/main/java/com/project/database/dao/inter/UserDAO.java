package com.project.database.dao.inter;

import com.project.database.entity.UserEntity;

public interface UserDAO {
    UserEntity findByUsername(String username);
}
