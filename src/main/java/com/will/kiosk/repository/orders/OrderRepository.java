package com.will.kiosk.repository.orders;

import com.will.kiosk.model.Orders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Orders insert(Orders order);

    List<Orders> findAll();

    Orders update(Orders order);

    void delete(UUID orderId);

    Optional<Orders> findById(UUID orderId);

}
