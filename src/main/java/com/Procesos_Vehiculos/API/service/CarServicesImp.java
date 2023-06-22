package com.Procesos_Vehiculos.API.service;

import com.Procesos_Vehiculos.API.models.Car;
import com.Procesos_Vehiculos.API.models.User;
import com.Procesos_Vehiculos.API.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarServicesImp implements CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Car getCar(Long id){
        String url = "https://myfakeapi.com/api/cars/"+id;
        Car car = restTemplate.getForObject(url, Car.class);
        return car;
//        return carRepository.findById(id).get();
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
            car.setUserId(new User(1L));

            return carRepository.save(car);
        }
        @Override
    public List<Car>allCars(){return carRepository.findAll();}

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
    }
}

