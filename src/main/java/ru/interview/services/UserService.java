package ru.interview.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.interview.errors.AuthException;
import ru.interview.errors.ErrorUser;
import ru.interview.models.User;
import ru.interview.repositories.BalanceRepository;
import ru.interview.repositories.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;

import static ru.interview.errors.ErrorUser.*;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private BalanceRepository balanceRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       BalanceRepository balanceRepository) {
        this.userRepository = userRepository;
        this.balanceRepository = balanceRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.getUserByUser(username);
            if(user == null){
                throw new AuthException(NO_USER.getDescription());
            }
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                    new ArrayList<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new AuthException(TECHNICAL_ERROR.getDescription());
    }

    public ErrorUser addNewUser(User newUser) throws SQLException {
        ErrorUser statusBack;
                if(userRepository.checkExistUserByName(newUser.getName())){
                    log.debug("Пользователь с именем [{}] уже сущетсвует",newUser.getName());
                    statusBack = LOGIN_NOT_UNIQ;
                } else {
                    int idNewUser = userRepository.addNewUser(newUser);
                    if(idNewUser == 0){
                        statusBack = TECHNICAL_ERROR;
                    } else {
                        statusBack = balanceRepository.setBalanceFroNewUser(idNewUser) != 0?OK:TECHNICAL_ERROR;
                    }
                    log.debug("Пользователь с именем [{}] добавлен, его ID [{}]",newUser.getName(),idNewUser);
                }
        return statusBack;
    }

    public User getUser(String name){
        try {
            return userRepository.getUserByUser(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
