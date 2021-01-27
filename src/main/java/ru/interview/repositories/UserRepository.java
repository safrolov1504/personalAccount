package ru.interview.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;
import ru.interview.models.User;


import javax.sql.DataSource;
import java.sql.*;


@Slf4j
@Repository
public class UserRepository {

    private DataSource ds;

    @Autowired
    public UserRepository(ApplicationContext applicationContext) {
        this.ds = (DataSource)applicationContext.getBean("dataSource");
    }

    public User getUserByUser(String name) throws SQLException {
        log.debug("Запрос информации о пользовалее по имени [{}] и паролю",name);
        Connection conn = ds.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT id, nm_name, nm_password,nm_email,nm_first_name,nm_second_name FROM users WHERE nm_name = ?");
        ps.setString(1,name);
        ResultSet rs =  ps.executeQuery();
        User user = null;
        if(rs.next()){
            user = new User(rs.getInt("id"),rs.getString("nm_name"),rs.getString("nm_password"),rs.getString("nm_first_name"),
                    rs.getString("nm_second_name"),rs.getString("nm_email"));
        }
        conn.close();
        return user;
    }

    public boolean checkExistUserByName(String name) throws SQLException {
        log.debug("Запрос информации о пользовалее по имени [{}]",name);
        User user = getUserByUser(name);
        return user == null ? false : true;
    }

    public int addNewUser(User newUser) throws SQLException {
        log.debug("Добавление нового пользователя с именем [{}]",newUser.getName());
        Connection conn = ds.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (nm_name, nm_password, nm_first_name, nm_second_name, nm_email) " +
                        "VALUES (?,?,?,?,?)");
        ps.setString(1,newUser.getName());
        ps.setString(2,newUser.getPassword());
        ps.setString(3,newUser.getFirstName());
        ps.setString(4,newUser.getSecondName());
        ps.setString(5,newUser.getEMail());
        int numbers = ps.executeUpdate();
        int idNewUser = 0;
        if(numbers != 0){
            idNewUser = getUserByUser(newUser.getName()).getId();
        }
        conn.close();
        return idNewUser;
    }

}
