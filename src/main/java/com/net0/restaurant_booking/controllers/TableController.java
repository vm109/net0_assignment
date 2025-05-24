package com.net0.restaurant_booking.controllers;

import com.net0.restaurant_booking.dtos.Table;
import com.net0.restaurant_booking.services.TablesService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/restaurants/tables")
public class TableController {
    @Resource
    private TablesService tableService;

    @GetMapping("/getTableById")
    public ResponseEntity<Table> getTableById(@RequestParam() Long tableId) {

        if (tableId <= 0) {
            return ResponseEntity.
                    badRequest().
                    body(new Table("Table ID should be greater than 0"));
        }

        try {
            Table table = tableService.getTableById(tableId);
            if (table == null) {
                return ResponseEntity.
                        badRequest().
                        body(new Table("Table not found"));
            }

            return ResponseEntity.
                    ok().
                    body(table);

        } catch (Exception e) {
            return ResponseEntity.
                    badRequest().
                    body(new Table("Error occurred while fetching table details"));
        }
    }

    @GetMapping("/listAllTables")
    public ResponseEntity<List<Table>> listAllTables() {
        try {
            List<Table> tables = tableService.listAllTables();
            if (tables.isEmpty() || (tables.size() == 1 && Objects.equals(tables.get(0).getErrorMessage(), "No tables found"))) {
                return ResponseEntity.
                        ok().
                        body(List.of(new Table("No tables found")));
            }
            return ResponseEntity.
                    ok().
                    body(tables);
        } catch (Exception e) {
            return ResponseEntity.
                    badRequest().
                    body(List.of(new Table("Error occurred while fetching tables list")));
        }
    }

    @PostMapping("/addTable")
    public ResponseEntity<String> addTable(@RequestBody(required = true) Table table) {
        if (table == null
                || table.getNumberOfSeats() <= 0
                || StringUtils.isEmpty(table.gettableArea())
                || StringUtils.isEmpty(table.getTableName())
                || Objects.isNull(table.isAvailable())) {
            return handleError("Invalid table data provided");
        }

        try {
            tableService.addTable(table);
            return ResponseEntity.
                    ok().
                    body("Table added successfully");
        } catch (RuntimeException e) {
            return handleError("Error occurred while saving table");
        }
    }

    @PatchMapping("/updateTable")
    public ResponseEntity<Table> updateTable(@RequestBody Table updatedTable) {

        try {
            Table updated = tableService.updateTable(updatedTable);
            if (updated == null) {
                return ResponseEntity.
                        badRequest().
                        body(new Table("Table not found with ID: " + updatedTable.getId()));
            }
            return ResponseEntity.
                    ok().
                    body(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.
                    badRequest().
                    body(new Table("Error occurred while updating table: " + e.getMessage()));
        }
    }

    @DeleteMapping("/deleteTable")
    public ResponseEntity<String> deleteTable(@RequestParam Long tableId) {
        if (tableId <= 0) {
            return ResponseEntity.
                    badRequest().
                    body("Table ID should be greater than 0");
        }

        try {
            tableService.deleteTable(tableId);
            return ResponseEntity.
                    ok().
                    body("Table deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.
                    badRequest().
                    body("Error occurred while deleting table: " + e.getMessage());
        }
    }

    private ResponseEntity<String> handleError(String errorMessage) {
        return ResponseEntity.
                badRequest().
                body(errorMessage);
    }
}
