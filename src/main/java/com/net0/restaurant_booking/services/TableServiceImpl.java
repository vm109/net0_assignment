package com.net0.restaurant_booking.services;

import com.net0.restaurant_booking.dtos.Table;
import com.net0.restaurant_booking.persistence.daos.RestaurantsRepository;
import com.net0.restaurant_booking.persistence.models.Tables;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.net0.restaurant_booking.persistence.daos.TablesRepository;

import java.util.List;
import java.util.Objects;

@Service
public class TableServiceImpl implements TablesService {
    @Resource
    private TablesRepository tablesRepository;

    @Resource
    private RestaurantsRepository restaurantsRepository;

    @Override
    public Table getTableById(Long id) {
       Tables tables =  tablesRepository.getTablesById(id);
        if (tables == null) {
            return null;
        }
        Table table = new Table();
        table.setId(tables.getId());
        table.setisAvailable(tables.isAvailable());
        table.settableArea(tables.gettableArea());
        return table;
    }

    @Override
    public List<Table> listAllTables() {
        List<Tables> tablesList = tablesRepository.findAll();
        if (tablesList.isEmpty()) {
            return List.of(new Table("No tables found"));
        }

        return tablesList.stream().map(tables -> {
            Table table = new Table();
            table.setId(tables.getId());
            table.setisAvailable(tables.isAvailable());
            table.settableArea(tables.gettableArea());
            table.setNumberOfSeats(tables.getCapacity());
            return table;
        }).toList();
    }

    @Override
    public void addTable(Table table) throws RuntimeException {
        Tables tables = new Tables();
        tables.setId(table.getId());
        tables.setAvailable(table.isAvailable());
        tables.settableArea(table.gettableArea());
        tables.setCapacity(table.getNumberOfSeats());
        tables.setName(table.getTableName());
        tables.setRestaurants(restaurantsRepository.findById(table.getRestaurantId()).get());
        try {
            tablesRepository.save(tables);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while saving table: " + e.getMessage());
        }
    }

    @Override
    public Table updateTable(Table updatedTable) throws RuntimeException {
        Tables existingTable = tablesRepository.getTablesById(updatedTable.getId());

        if (existingTable == null) {
            throw new RuntimeException("Table not found with ID: " + updatedTable.getId());
        }

        if(Objects.nonNull(updatedTable.isAvailable())) {
            existingTable.setAvailable(updatedTable.isAvailable());
        }

        if(StringUtils.isNotEmpty(updatedTable.gettableArea())) {
            existingTable.settableArea(updatedTable.gettableArea());
        }
        if(updatedTable.getNumberOfSeats() > 0) {
            existingTable.setCapacity(updatedTable.getNumberOfSeats());
        }
        if(StringUtils.isNotEmpty(updatedTable.getTableName())) {
            existingTable.setName(updatedTable.getTableName());
        }

        try {
            tablesRepository.save(existingTable);
            return convertTableEntityToDto(existingTable);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while updating table: " + e.getMessage());
        }
    }


    private Table convertTableEntityToDto(Tables tables) {
        Table table = new Table();
        table.setId(tables.getId());
        table.setisAvailable(tables.isAvailable());
        table.settableArea(tables.gettableArea());
        table.setNumberOfSeats(tables.getCapacity());
        table.setTableName(tables.getName());
        return table;
    }

    @Override
    public void deleteTable(Long id) throws RuntimeException {
        Tables existingTable = tablesRepository.getTablesById(id);
        if (existingTable == null) {
            throw new RuntimeException("Table not found with ID: " + id);
        }
        try {
            tablesRepository.delete(existingTable);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting table: " + e.getMessage());
        }
    }

    @Override
    public List<Table> getTablesByRestaurantId(Long restaurantId) throws RuntimeException {
        if (restaurantId <= 0) {
            throw new RuntimeException("Restaurant ID should be greater than 0");
        }

        List<Tables> tablesList = tablesRepository.findByRestaurantsId(restaurantId);
        if (tablesList.isEmpty()) {
            return List.of(new Table("No tables found for the given restaurant ID"));
        }

        return tablesList.stream().map(tables -> {
            Table table = new Table();
            table.setId(tables.getId());
            table.setisAvailable(tables.isAvailable());
            table.settableArea(tables.gettableArea());
            table.setNumberOfSeats(tables.getCapacity());
            table.setTableName(tables.getName());
            return table;
        }).toList();
    }
}
