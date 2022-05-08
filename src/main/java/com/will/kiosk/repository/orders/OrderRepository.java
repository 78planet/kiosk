package com.will.kiosk.repository.orders;

import com.will.kiosk.dao.OrderDAO;
import com.will.kiosk.model.Orders;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {

    Orders insert(Orders order);

    List<Orders> findAll();

    Orders update(Orders order);

    void delete(UUID orderId);

    OrderDAO findById(UUID orderId);

}
