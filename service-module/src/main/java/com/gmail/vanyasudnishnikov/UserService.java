package com.gmail.vanyasudnishnikov;

import com.gmail.vanyasudnishnikov.model.User;

public interface UserService {
    Boolean isValid(String username, String password);

    User findByUsername(String username);
}
