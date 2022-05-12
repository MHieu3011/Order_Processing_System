package com.ptit.ops.dao.impl;

import com.ptit.ops.dao.OrderDAO;
import com.ptit.ops.entities.OrderEntity;
import com.ptit.ops.exception.CommonException;
import com.ptit.ops.factory.MySQLConnectionFactory;
import com.ptit.ops.global.ConfigInfo;
import com.ptit.ops.global.ErrorCode;
import com.ptit.ops.model.response.InfoOrderResponse;
import com.ptit.ops.utils.DateTimeUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class OrderDAOImpl extends AbstractDAO implements OrderDAO {

    @Override
    public InfoOrderResponse create(OrderEntity entity) throws Exception {
        InfoOrderResponse result = new InfoOrderResponse();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MySQLConnectionFactory.getInstance().getMySQLConnection();
            connection.setAutoCommit(false);
            /*
                trước khi thêm hóa đơn cần kiểm tra xem số lượng sản phẩm có productId trong kho có còn đủ không
                nếu có thì giảm luôn số sản phẩm đó
            */
            String sql1 = "UPDATE " + ConfigInfo.DB_STOCK + " s SET s.quantity = s.quantity - ? WHERE s.productid = ? AND s.quantity >= ? LIMIT 1";
            statement = connection.prepareStatement(sql1);
            int i = 1;
            statement.setInt(i++, entity.getAmount());
            statement.setInt(i++, entity.getProductId());
            statement.setInt(i++, entity.getAmount());
            int check = statement.executeUpdate();
            if (check > 0) {
                String sql2 = "INSERT INTO " + ConfigInfo.DB_ORDER + "(customerid, productid, amount, orderdate) VALUES(?, ?, ?, ?)";
                statement = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
                int j = 1;
                statement.setInt(j++, entity.getCustomerId());
                statement.setInt(j++, entity.getProductId());
                statement.setInt(j++, entity.getAmount());
                statement.setLong(j++, entity.getOrderDate());
                statement.executeUpdate();
                resultSet = statement.getGeneratedKeys();
                while (resultSet.next()) {
                    result.setId(resultSet.getInt(1));
                }
                result.setCustomerId(entity.getCustomerId());
                result.setProductId(entity.getProductId());
                result.setAmount(entity.getAmount());
                result.setOrderDate(DateTimeUtils.formatTimeInSec(entity.getOrderDate()));
                connection.commit();
            } else {
                result.setOrderDate("quantity");
                throw new CommonException(ErrorCode.QUANTITY_PRODUCT_INVALID, "quantity product in stock smaller amount product in order");
            }
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            eLogger.error("Error OrderDAO.insert order: {}", e.getMessage());
        } finally {
            releaseResource(connection, statement, resultSet);
        }
        return result;
    }
}
