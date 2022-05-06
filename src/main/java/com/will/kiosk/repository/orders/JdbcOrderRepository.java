package com.will.kiosk.repository.orders;

import com.will.kiosk.model.OrderStatus;
import com.will.kiosk.model.Orders;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.will.kiosk.JdbcUtils.toLocalDateTime;
import static com.will.kiosk.JdbcUtils.toUUID;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Orders insert(Orders order) {
        var update = jdbcTemplate.update("INSERT INTO orders(order_id, order_status, order_table_number, created_at)" +
                " VALUES(UUID_TO_BIN(:orderId), :orderStatus, :orderTableNumber, :createdAt)", toParamMap(order));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return order;
    }

    @Override
    public List<Orders> findAll() {
        return jdbcTemplate.query("select * from orders", orderRowMapper);
    }

    @Override
    public Orders update(Orders order) {
        var update = jdbcTemplate.update(
                "UPDATE orders SET order_status = :orderStatus, order_table_number = :orderTableNumber" +
                        " WHERE order_id = UUID_TO_BIN(:orderId)", toParamMap(order));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return order;
    }

    @Override
    public void delete(UUID orderId) {
        var update = jdbcTemplate.update(
                "DELETE FROM orders WHERE order_id = UUID_TO_BIN(:orderId)",
                     Collections.singletonMap("orderId", orderId.toString().getBytes()));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
    }

    @Override
    public Optional<Orders> findById(UUID orderId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM orders WHERE order_id = UUID_TO_BIN(:orderId)",
                    Collections.singletonMap("orderId", orderId.toString().getBytes()),
                    orderRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static final RowMapper<Orders> orderRowMapper = (resultSet, i) -> {
        var orderId = toUUID(resultSet.getBytes("order_id"));
        var orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        var orderTableNumber = resultSet.getInt("order_table_number");
        var createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        return new Orders(orderId, orderStatus, orderTableNumber, createdAt);
    };

    private final Map<String, Object> toParamMap(Orders order) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", order.getOrderId().toString().getBytes());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("orderTableNumber", order.getOrderTableNumber());
        paramMap.put("createdAt", order.getCreatedAt());
        return paramMap;
    }
}
