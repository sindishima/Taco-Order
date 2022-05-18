package com.example.documentStorage.repositories;

import com.example.documentStorage.domains.Order;

public interface OrderRepository {
    Order save(Order order);
}
