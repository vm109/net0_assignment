package com.net0.restaurant_booking.persistence.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Tables {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;
    private int capacity;
    @Enumerated(EnumType.STRING)
    private TableArea tableArea;
    private boolean isAvailable;

    private enum TableArea {
        TERRACE,
        OUTDOOR,
        INDOOR
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String gettableArea() {
        return tableArea.toString();
    }

    public void settableArea(String tableArea) {
        this.tableArea = TableArea.valueOf(tableArea.toUpperCase());
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
