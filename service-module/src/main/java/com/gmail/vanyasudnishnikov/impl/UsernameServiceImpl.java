package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.UniqueUsernameRepository;
import com.gmail.vanyasudnishnikov.UsernameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
@AllArgsConstructor
@Slf4j
public class UsernameServiceImpl implements UsernameService {
    private DataSource dataSource;
    private UniqueUsernameRepository uniqueUsernameRepository;

    @Override
    public Boolean isExists(String username) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                String usernameFromTable = uniqueUsernameRepository.checkInTableUsers(connection, username);
                connection.commit();
                if (usernameFromTable == null) {
                    return false;
                }
            } catch (Exception e) {
                connection.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return true;
    }
}
