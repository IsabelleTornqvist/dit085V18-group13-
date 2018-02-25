import Controller.Actuator;

public class Main {

    public static void main(String[] args) {
        Actuator controller = new Actuator();
        while(controller.car.position.x < 100){
            controller.move();
            controller.update();
            System.out.println();
        }
    }

}
