package com.will.kiosk.repository.orders;

import com.will.kiosk.dao.OrderDAO;
import com.will.kiosk.dao.OrderItemDAO;
import com.will.kiosk.dto.OrderDTO;
import com.will.kiosk.model.OrderItem;
import com.will.kiosk.model.OrderStatus;
import com.will.kiosk.model.Orders;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @Transactional
    public Orders insert(Orders order) {
        jdbcTemplate.update("INSERT INTO orders(order_id, order_status, order_table_number, created_at)" +
                " VALUES(UUID_TO_BIN(:orderId), :orderStatus, :orderTableNumber, :createdAt)", toOrderParamMap(order));

        order.getOrderItems().forEach(orderItem ->
                jdbcTemplate.update("INSERT INTO order_item(order_id, product_id, quantity)" +
                        " VALUES(UUID_TO_BIN(:orderId), :productId, :quantity)", toOrderItemParamMap(orderItem, order.getOrderId()) ));
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
                        " WHERE order_id = UUID_TO_BIN(:orderId)", toOrderParamMap(order));
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
    @Transactional
    public OrderDAO findById(UUID orderId) {
        Orders order = jdbcTemplate.queryForObject("SELECT * FROM orders WHERE order_id = UUID_TO_BIN(:orderId)",
                    Collections.singletonMap("orderId", orderId.toString().getBytes()),
                    orderRowMapper);

        List<OrderItemDAO> orderItems =  jdbcTemplate.query("SELECT p.product_id, product_name, order_item.quantity quantity, price" +
                            " FROM order_item" +
                            " JOIN product p ON order_item.product_id = p.product_id" +
                            " WHERE order_id = UUID_TO_BIN(:orderId);",
                    Collections.singletonMap("orderId", orderId.toString().getBytes()),
                    orderItemRowMapper);

        return new OrderDAO(order.getOrderId(), order.getOrderStatus(), order.getOrderTableNumber(), orderItems);
    }

    private static final RowMapper<Orders> orderRowMapper = (resultSet, i) -> {
        var orderId = toUUID(resultSet.getBytes("order_id"));
        var orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        var orderTableNumber = resultSet.getInt("order_table_number");
        var createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        return new Orders(orderId, orderStatus, orderTableNumber, null, createdAt);
    };

    private static final RowMapper<OrderItemDAO> orderItemRowMapper = (resultSet, i) -> {
        var productId = resultSet.getInt("product_id");
        var productName = resultSet.getString("product_name");
        var quantity = resultSet.getInt("quantity");
        var price = resultSet.getInt("price");
        return new OrderItemDAO(productId, productName, quantity, price);
    };

    private final Map<String, Object> toOrderParamMap(Orders order) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", order.getOrderId().toString().getBytes());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("orderTableNumber", order.getOrderTableNumber());
        paramMap.put("createdAt", order.getCreatedAt());
        return paramMap;
    }


    private Map<String, Object> toOrderItemParamMap(OrderItem item, UUID orderId) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId.toString().getBytes());
        paramMap.put("productId", item.productId());
        paramMap.put("quantity", item.quantity());
        return paramMap;
    }
}
