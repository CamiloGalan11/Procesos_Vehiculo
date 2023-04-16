package com.Procesos_Vehiculos.API.service;

import com.Procesos_Vehiculos.API.models.Car;
import com.Procesos_Vehiculos.API.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD

import java.util.List;

@Service
public class CarServicesImp implements CarService {
    @Autowired
    private CarRepository carRepository;

    public Car getCar(Long id){return carRepository.findById(id).get();}
        @Override
        public Boolean createCar(Car car){
        try{
            carRepository.save(car);
            return true;
        }catch (Exception e){
            return false;
        }
        }
        @Override
    public List<Car>allCars(){return carRepository.findAll();}

    @Override
    public Boolean updateCar(Long id, Car car){
        try{
            Car carBD = carRepository.findById(id).get();

            carBD.setCarBrand(car.getCarBrand());
            carBD.setColor(car.getColor());
            carBD.setState(car.getState());
            carBD.setPrice(car.getPrice());
            carBD.setYear(car.getYear());
            carBD.setModel(car.getModel());
            Car carUp = carRepository.save(carBD);
            return true;
        }catch (Exception e){
            return false;
        }
=======
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CarServicesImp implements CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Car getCar(Long id) {
        return carRepository.findById(id).orElse(null);
    }
    @Override
    public Car createCar(Long id ) {
        String url = "https://myfakeapi.com/api/cars/"+id;
        //Extraer de la api la data que viene mapeada
        Map<String, Map<String, Object>> map = restTemplate.getForObject(url, HashMap.class);
        assert map != null; //Verifica que no venga null
        Map<String, Object> obj = map.get("Car"); //Extrae los valores de la key "Car" que vienen mapeados tambien

        Car car = new Car(); //Nueva instancia de Car
        car.setId(id); //El id que se pasó por la url
        car.setType( (String) obj.get("car") ); //Asignamos el dato que recupera del map (obj.get)
        car.setModel( (String) obj.get("car_model") );
        car.setColor((String) obj.get("car_color"));
        car.setYear((Integer) obj.get("car_model_year"));
        car.setPrice((String) obj.get("price"));
        car.setReference((String) obj.get("car_vin"));
        car.setAvailability((Boolean) obj.get("availability"));

        return carRepository.save(car);
    }
        @Override
    public List<Car>allCars() {return carRepository.findAll();}

    @Override
    public Boolean updateCar(Long id, Car car) {

        Car carBD = carRepository.findById(id).orElse(null);

        if (carBD == null) {
            return false;
        }

        carBD.setType(car.getType());
        carBD.setColor(car.getColor());
        carBD.setPrice(car.getPrice());
        carBD.setYear(car.getYear());
        carBD.setModel(car.getModel());

        carRepository.save(carBD);

        return true;
>>>>>>> develop/yindry/parcial1
    }
}

