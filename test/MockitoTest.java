import Controller.Actuator;
import Model.Car;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MockitoTest {

    @Mock
    private Car car;
    Actuator ac;


    @Before
    public void setUp() throws Exception{
        car = Mockito.spy(Car.class);
        ac = Mockito.spy(Actuator.class);
        ac.car = this.car;
    }

    @Test
    public void testScenarioChangeLaneOccupied(){
        // Model.Car starts to move forward
        ac.move();
        ac.move();
        // Model.Car tries to turn left
        when(car.leftLaneDetect()).thenReturn(true);
        ac.switchLane();
        // Check that the car did not turn left
        assertEquals(new Point(15,0), car.whereIs());
        // Continues to move until it reaches the end of the street
        while(car.whereIs().x < 100){
            ac.move();
        }
        // Check that it reaches the end of the street and hasn not changed lane
        assertEquals(new Point(100,0),car.whereIs());
    }

    @Test
    public void testScenarioChangeToLaneVacant(){
        // Model.Car starts to move forward
        ac.move();
        ac.move();
        // Model.Car tries to turn left
        when(car.leftLaneDetect()).thenReturn(false);
        ac.switchLane();
        // Check that the car did turn left
        assertEquals(new Point(15,5), car.whereIs());
        // Continues to move until it reaches the end of the street
        while(car.whereIs().x < 100){
            ac.move();
        }
        // Check that it reaches the end of the street and has changed lane
        assertEquals(new Point(100,5),car.whereIs());
    }

    @Test
    public void testEndOfRoad(){
        //Mocking the lidar output to detect no car in neighboring lane
        car.setLidar(-1,-1);
        for(int i = 0; i < 3; i++){
            car.setRadar(i, -1,-1);
        }

        //Mocking the start position by assigning values manually
        car.position.y = 5;
        car.position.x = 0;

        //Asserting the expected value: the car will not move sideways (y-position). It will not move f
        Point expectedPosition = new Point(100,5);

        while (car.whereIs().x < expectedPosition.x){
            ac.move();
        }
        //Checking the left lane for vacancy, with mocked values
        //Trying to change lane
        ac.switchLane();

        Point result = car.whereIs();
        //Expected: should not change lane.
        assertEquals(expectedPosition, result);
    }

    @Test
    public void scenario4() {

        for (int i = 0; i < 5; i++) {
            ac.move();
        }
        car.setLidar(-1, -1);
        for (int i = 0; i < 3; i++) {
            car.setRadar(i, -1, -1);
        }
        ac.switchLane();
        assertEquals(new Point(30, 5), car.whereIs());
        assertEquals(1, car.currentLane);
        for (int i = 0; i < 4; i++) {
            ac.move();
        }
        assertEquals(new Point(50, 5), car.whereIs());
        assertEquals(1, car.currentLane);
        for (int i = 0; i < 2; i++) {
            ac.car.setRadar(i, 231, 98098);
        }
        ac.switchLane();
        assertEquals(new Point(55, 10), car.whereIs());
        assertEquals(0, car.currentLane);
        for (int i = 0; i < 9; i++) {
            ac.move();
        }
        assertEquals(new Point(100, 10), car.whereIs());
        assertEquals(0, car.currentLane);

    }

}
