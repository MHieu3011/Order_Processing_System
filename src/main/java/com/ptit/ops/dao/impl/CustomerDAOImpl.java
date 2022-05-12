package com.ptit.ops.dao.impl;

import com.ptit.ops.dao.CustomerDAO;
import com.ptit.ops.entities.CustomerEntity;
import com.ptit.ops.factory.MySQLConnectionFactory;
import com.ptit.ops.model.response.InfoCustomerResponse;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

@Repository
public class CustomerDAOImpl extends AbstractDAO implements CustomerDAO {

    @Override
    public InfoCustomerResponse create(CustomerEntity entity) throws Exception {
        InfoCustomerResponse result = new InfoCustomerResponse();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLConnectionFactory.getInstance().getMySQLConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO customer(name, address, phone) VALUES(?, ?, ?)";
            statement = connection.prepareStatement(sql);
            int i = 1;
            statement.setString(i++, entity.getName());
            statement.setString(i++, entity.getAddress());
            statement.setString(i++, entity.getPhone());
            statement.executeUpdate();
            result.setName(entity.getName());
            result.setAddress(entity.getAddress());
            result.setPhone(entity.getPhone());
            connection.commit();
        } catch (Exception e) {
            eLogger.error("Error CustomerDAO.insert customer: {}", e.getMessage());
        } finally {
            releaseConnectAndStatement(connection, statement);
        }
        return result;
    }
}
