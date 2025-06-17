package org.example.homework.service;

import lombok.RequiredArgsConstructor;
import org.example.homework.model.Car;
import org.example.homework.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car updatedCar) {
        if (!carRepository.existsById(id)) {
            return null;
        }
        updatedCar.setId(id);
        return carRepository.save(updatedCar);
    }

    public Car setNewPrice(Long id, double price) {
        Optional<Car> existingCarOpt = carRepository.findById(id);
        if (existingCarOpt.isPresent()) {
            Car car = existingCarOpt.get();
            car.setPrice(price);
            return carRepository.save(car);
        }
        return null;
    }

    public boolean deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            return false;
        }
        carRepository.deleteById(id);
        return true;
    }

    public List<Car> filterCars(String brand, String color) {
        if (brand != null && color != null) {
            return carRepository.findByBrandAndColor(brand, color);
        } else if (brand != null) {
            return carRepository.findByBrand(brand);
        } else if (color != null) {
            return carRepository.findByColor(color);
        } else {
            return carRepository.findAll();
        }
    }

    public List<Car> sortCars(String by, String order) {
        if ("asc".equalsIgnoreCase(order)) {
            return carRepository.sortByFieldAsc(by);
        } else {
            return carRepository.sortByFieldDesc(by);
        }
    }
}
