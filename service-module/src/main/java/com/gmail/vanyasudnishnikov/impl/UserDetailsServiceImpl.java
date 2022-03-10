package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.UserRepository;
import com.gmail.vanyasudnishnikov.model.RoleDTOEnum;
import com.gmail.vanyasudnishnikov.model.User;
import com.gmail.vanyasudnishnikov.model.UserDTO;
import com.gmail.vanyasudnishnikov.model.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private DataSource dataSource;
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getUserByUsername(connection, username);
                connection.commit();
                UserDTO userDTO = convertToUserDTO(user);
                if (userDTO == null) {
                    throw new UsernameNotFoundException("User was not found with username");
                }
                return UserDetailsImpl
                        .build(userDTO);
            } catch (Exception e) {
                connection.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private UserDTO convertToUserDTO(User user) {
        Integer userId = user.getId();
        String username = user.getUsername();
        String userPassword = user.getPassword();
        RoleEnum role = user.getRole();
        List<RoleDTOEnum> roleDTOList = new ArrayList<>();
        //  roleDTOList.add(role);
        if (role == RoleEnum.ROLE_USER) {
            roleDTOList.add(RoleDTOEnum.USER);
        } else {
            roleDTOList.add(RoleDTOEnum.ADMIN);
        }
        return UserDTO.builder().id(userId)
                .username(username)
                .password(userPassword)
                .role(roleDTOList)
                .build();
    }
}
