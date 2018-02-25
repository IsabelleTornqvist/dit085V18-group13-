package View;
import Model.*;

public class CarLog {

    public void printLog(Car car) {
        System.out.print("Model.Car position: " + car.whereIs().x + " " + car.whereIs().y);
    }

}
