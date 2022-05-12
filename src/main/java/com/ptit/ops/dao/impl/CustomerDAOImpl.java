package com.ptit.ops.dao.impl;

import com.ptit.ops.dao.CustomerDAO;
import com.ptit.ops.entities.CustomerEntity;
import com.ptit.ops.factory.MySQLConnectionFactory;
import com.ptit.ops.global.ConfigInfo;
import com.ptit.ops.model.response.InfoCustomerResponse;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class CustomerDAOImpl extends AbstractDAO implements CustomerDAO {

    @Override
    public InfoCustomerResponse create(CustomerEntity entity) throws Exception {
        InfoCustomerResponse result = new InfoCustomerResponse();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MySQLConnectionFactory.getInstance().getMySQLConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO " + ConfigInfo.DB_CUSTOMER + "(name, address, phone) VALUES(?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            statement.setString(i++, entity.getName());
            statement.setString(i++, entity.getAddress());
            statement.setString(i++, entity.getPhone());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                result.setId(resultSet.getInt(1));
            }
            result.setName(entity.getName());
            result.setAddress(entity.getAddress());
            result.setPhone(entity.getPhone());
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            eLogger.error("Error CustomerDAO.insert customer: {}", e.getMessage());
        } finally {
            releaseResource(connection, statement, resultSet);
        }
        return result;
    }

    @Override
    public InfoCustomerResponse update(CustomerEntity entity) throws Exception {
        InfoCustomerResponse result = new InfoCustomerResponse();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MySQLConnectionFactory.getInstance().getMySQLConnection();
            connection.setAutoCommit(false);
            String sql = "UPDATE " + ConfigInfo.DB_CUSTOMER + " SET name = ?, address = ?, phone = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            int i = 1;
            statement.setString(i++, entity.getName());
            statement.setString(i++, entity.getAddress());
            statement.setString(i++, entity.getPhone());
            statement.setInt(i++, entity.getId());
            statement.executeUpdate();
            result.setId(entity.getId());
            result.setName(entity.getName());
            result.setAddress(entity.getAddress());
            result.setPhone(entity.getPhone());
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            eLogger.error("Error CustomerDAO.update customer: {}", e.getMessage());
        } finally {
            releaseConnectAndStatement(connection, statement);
        }
        return result;
    }
}
