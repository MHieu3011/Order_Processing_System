package com.ptit.ops.dao.impl;

import com.ptit.ops.dao.ProductDAO;
import com.ptit.ops.factory.MySQLConnectionFactory;
import com.ptit.ops.model.response.InfoProductResponse;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAOImpl extends AbstractDAO implements ProductDAO {
    @Override
    public List<InfoProductResponse> findAll() throws Exception {
        List<InfoProductResponse> resultList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MySQLConnectionFactory.getInstance().getMySQLConnection();
            connection.setAutoCommit(false);
            StringBuilder sql = new StringBuilder("SELECT p.id, p.price, p.type, SUM(s.quantity) AS total_quantity");
            sql.append(" FROM product AS p");
            sql.append(" LEFT JOIN stock s ON p.id = s.productid");
            sql.append(" GROUP BY p.id");
            sql.append(" ORDER BY total_quantity DESC");
            statement = connection.prepareStatement(sql.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                InfoProductResponse result = new InfoProductResponse();
                result.setId(resultSet.getInt("id"));
                result.setPrice(resultSet.getFloat("price"));
                result.setType(resultSet.getString("type"));
                result.setTotalQuantity(resultSet.getInt("total_quantity"));
                resultList.add(result);
            }
        } catch (Exception e) {
            eLogger.error("Error ProductDAO.findAll: {}", e.getMessage());
        } finally {
            releaseResource(connection, statement, resultSet);
        }
        return resultList;
    }
}
