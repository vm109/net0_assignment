package com.net0.restaurant_booking.services;

import com.net0.restaurant_booking.dtos.Table;
import com.net0.restaurant_booking.persistence.models.Tables;

import java.util.List;

public interface TablesService {
    Table getTableById(Long id);
    List<Table> listAllTables();
    void addTable(Table table) throws RuntimeException;
    Table updateTable(Table table) throws RuntimeException;
    void deleteTable(Long id) throws RuntimeException;

    List<Table> getTablesByRestaurantId(Long restaurantId) throws RuntimeException;
}
