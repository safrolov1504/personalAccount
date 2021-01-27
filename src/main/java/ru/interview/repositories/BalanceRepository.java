package ru.interview.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import ru.interview.models.Balance;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
public class BalanceRepository {
    private DataSource ds;

    @Autowired
    public BalanceRepository(ApplicationContext applicationContext) {
        this.ds = (DataSource)applicationContext.getBean("dataSource");
    }

    public int setBalanceFroNewUser(int idUser) throws SQLException {
        log.debug("Добавление нулевого баланска для нового пользователя с id [{}]",idUser);
        Connection conn = ds.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO balance (id_user, nn_balance) " +
                        "VALUES (?,?)");
        ps.setInt(1,idUser);
        ps.setLong(2,0);
        int count = ps.executeUpdate();
        conn.close();
        return count;
    }

    public Balance getBalance(String userName) throws SQLException {
        log.debug("Добавление нулевого баланска для нового пользователя с id [{}]",userName);
        Connection conn = ds.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "SELECT users.nm_first_name, users.nm_second_name, balance.nn_balance FROM users " +
                        "JOIN balance ON balance.id_user = users.id " +
                        "WHERE users.nm_name = ?"
        );
        ps.setString(1,userName);
        ResultSet rs =  ps.executeQuery();
        Balance balance = null;
        if(rs.next()){
            balance = new Balance(rs.getString("nm_first_name"),rs.getString("nm_second_name"), rs.getBigDecimal("nn_balance"));
        }
        conn.close();
        return balance;
    }

    public int addBalance(String userName, BigDecimal changeBalance) throws SQLException {
        log.debug("Изменение баланса для пользователя с именем [{}]",userName);
        Connection conn = ds.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE balance " +
                        "SET balance.nn_balance = balance.nn_balance + ? " +
                        "WHERE balance.id_user = (SELECT users.id FROM users WHERE users.nm_name = ?)"
                );
        ps.setBigDecimal(1,changeBalance);
        ps.setString(2,userName);
        int i = ps.executeUpdate();
        conn.close();
        return i;
    }
}
