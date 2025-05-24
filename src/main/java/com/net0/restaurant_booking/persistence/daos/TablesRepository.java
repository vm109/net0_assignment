package com.net0.restaurant_booking.persistence.daos;

import com.net0.restaurant_booking.persistence.models.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablesRepository extends JpaRepository<Tables, Long> {
    Tables getTablesById(Long id);
    @Query("SELECT t FROM Tables t where t.restaurants.restaurantId = ?1")
    List<Tables> findByRestaurantsId(Long restaurantId);
}
