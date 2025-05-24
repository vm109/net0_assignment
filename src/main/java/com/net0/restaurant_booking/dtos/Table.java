package com.net0.restaurant_booking.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Table {

    private Long id;
    private int numberOfSeats;
    private String tableArea;
    private Boolean isAvailable;
    private String tableName;

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    private Long restaurantId;


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String errorMessage;

    public Table() {
    }
    public Table(int numberOfSeats, String tableArea, Boolean isAvailable) {
        this.numberOfSeats = numberOfSeats;
        this.tableArea = tableArea;
        this.isAvailable = isAvailable;
    }

    public Table(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String gettableArea() {
        return tableArea;
    }

    public void settableArea(String tableArea) {
        this.tableArea = tableArea;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setisAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
