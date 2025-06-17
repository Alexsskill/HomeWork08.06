package org.example.homework.repository;

import org.example.homework.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByBrandAndColor(String brand, String color);

    List<Car> findByBrand(String brand);

    List<Car> findByColor(String color);

    @Query("SELECT c FROM Car c ORDER BY CASE WHEN :by = 'price' THEN c.price END ASC, " +
            "CASE WHEN :by = 'year' THEN c.year END ASC")
    List<Car> sortByFieldAsc(@Param("by") String by);

    @Query("SELECT c FROM Car c ORDER BY CASE WHEN :by = 'price' THEN c.price END DESC, " +
            "CASE WHEN :by = 'year' THEN c.year END DESC")
    List<Car> sortByFieldDesc(@Param("by") String by);
}