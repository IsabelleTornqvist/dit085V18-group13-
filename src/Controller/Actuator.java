package Controller;

import View.*;
import Model.*;

public class Actuator {

    public Car car = new Car();
    private CarLog carLog = new CarLog();


    public void move() {
        car.moveForward();
    }


    public void switchLane() {
        car.changeLane();
    }

    public void update(){
        carLog.printLog(car);
    }

}
