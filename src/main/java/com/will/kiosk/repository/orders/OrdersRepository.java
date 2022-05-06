package com.will.kiosk.repository.orders;

import com.will.kiosk.model.Orders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdersRepository {

    Orders insert(Orders category);

    List<Orders> findAll();

    Orders update(Orders category);

    void delete(Orders category);

    Optional<Orders> findById(UUID categoryId);

}
