package org.example.homework.controller;

import org.example.homework.model.Car;
import org.example.homework.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarById(@PathVariable Long id) {
        Optional<Car> car = carService.getCarById(id);
        return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.createCar(car));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Long id, @RequestBody Car updatedCar) {
        Car updated = carService.updateCar(id, updatedCar);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Car not found with ID: " + id);
        }
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> setNewPrice(@PathVariable Long id, @RequestParam double price) {
        Optional<Car> existingCarOpt = carService.getCarById(id);
        if (existingCarOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Car not found with ID: " + id);
        }
        return ResponseEntity.ok(carService.setNewPrice(id, price));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCar(@PathVariable Long id) {
        boolean deleted = carService.deleteCar(id);
        return ResponseEntity.ok(deleted);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Car>> filterCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color) {
        return ResponseEntity.ok(carService.filterCars(brand, color));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Car>> sortCars(
            @RequestParam String by,
            @RequestParam String order) {
        return ResponseEntity.ok(carService.sortCars(by, order));
    }
}
