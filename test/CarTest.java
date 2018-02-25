import Controller.Actuator;
import Model.Car;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CarTest {

    @Mock
    private Car myCar;
    Actuator ac;


    @Before
    public void setUp() throws Exception{
        myCar = Mockito.spy(Car.class);
        ac = Mockito.spy(Actuator.class);
        ac.car = this.myCar;
    }

    @Test
    public void radar_detect_car_1() {
        myCar.setRadar(0, 0, 50);
        myCar.setRadar(1, -1, 50);
        myCar.setRadar(2, -1, 50);
        myCar.setLidar(-1, 50);
        assertEquals(true, myCar.leftLaneDetect());
    }
    @Test
    public void radar_detect_car_2() {
        myCar.setRadar(0, -1, 50);
        myCar.setRadar(1, 0, 50);
        myCar.setRadar(2, -1, 50);
        myCar.setLidar(-1, 50);
        assertEquals(true, myCar.leftLaneDetect());
    }
    @Test
    public void radar_detect_car_3() {
        myCar.setRadar(0, -1, 50);
        myCar.setRadar(1, -1, 50);
        myCar.setRadar(2, 0, 50);
        myCar.setLidar(-1, 50);
        assertEquals(true, myCar.leftLaneDetect());
    }
    @Test
    public void lidar_detect_car() {
        myCar.setRadar(0, -1, 50);
        myCar.setRadar(1, -1, 50);
        myCar.setRadar(2, -1, 50);
        myCar.setLidar(0, 50);
        assertEquals(true, myCar.leftLaneDetect());
    }
    @Test
    public void radar_no_car(){
        myCar.setRadar(0, -1, -1);
        myCar.setRadar(1, -1, -1);
        myCar.setRadar(2, 0, -1);
        myCar.setLidar(-1, 50);
        assertEquals(false, myCar.leftLaneDetect());
    }
    @Test
    public void radar_no_car2(){
        myCar.setRadar(0, -1, -1);
        myCar.setRadar(1, 0, -1);
        myCar.setRadar(2, -1, -1);
        myCar.setLidar(-1, 50);
        assertEquals(false, myCar.leftLaneDetect());
    }
    @Test
    public void radar_no_car3(){
        myCar.setRadar(0, 0, -1);
        myCar.setRadar(1, -1, -1);
        myCar.setRadar(2, -1, -1);
        myCar.setLidar(-1, 50);
        assertEquals(false, myCar.leftLaneDetect());
    }
    @Test
    public void lidar_radar_no_car_1(){
        myCar.setRadar(0, -1, -1);
        myCar.setRadar(1, 0, -1);
        myCar.setRadar(2, 0, -1);
        myCar.setLidar(-1, -1);
        assertEquals(false, myCar.leftLaneDetect());
    }
    @Test
    public void lidar_radar_no_car_2(){
        myCar.setRadar(0, 0, -1);
        myCar.setRadar(1, -1, -1);
        myCar.setRadar(2, 0, -1);
        myCar.setLidar(-1, -1);
        assertEquals(false, myCar.leftLaneDetect());
    }
    @Test
    public void lidar_radar_no_car_3(){
        myCar.setRadar(0, 0, -1);
        myCar.setRadar(1, 0, -1);
        myCar.setRadar(2, -1, -1);
        myCar.setLidar(-1, -1);
        assertEquals(false, myCar.leftLaneDetect());
    }
    @Test
    public void not_enough_readings(){
        myCar.setRadar(0, -1, -1);
        myCar.setRadar(1, 0, -1);
        myCar.setRadar(2, 0, -1);
        myCar.setLidar(0, -1);
        assertEquals(true, myCar.leftLaneDetect());
    }
    @Test
    public void not_enough_readings_2() {
        myCar.setRadar(0, -2, -2);
        myCar.setRadar(1, -1, -1);
        myCar.setRadar(2, 51, -51);
        myCar.setLidar(51, 51);
        assertEquals(true, myCar.leftLaneDetect());
    }
    @Test
    public void not_enough_readings_3() {
        myCar.setRadar(0, -2, -2);
        myCar.setRadar(1, -2, -2);
        myCar.setRadar(2, -1, -1);
        myCar.setLidar(51, 51);
        assertEquals(true, myCar.leftLaneDetect());
    }
    @Test
    public void not_enough_readings_4() {
        myCar.setRadar(0, -2, -2);
        myCar.setRadar(1, -2, -2);
        myCar.setRadar(2, 51, -51);
        myCar.setLidar(-1, -1);
        assertEquals(true, myCar.leftLaneDetect());
    }
    @Test
    public void testMoveForwardNormal() {
        myCar.moveForward();
        assertEquals(5, myCar.whereIs().x);
        myCar.moveForward();
        assertEquals(10, myCar.whereIs().x);
    }

    @Test
    public void testMoveForwardEndOfStreet(){
        myCar.position.x = 95;
        myCar.moveForward();
        assertEquals(100, myCar.whereIs().x);
        myCar.moveForward();
        assertEquals(100, myCar.whereIs().x);

    }

    @Test
    public void testChangeLaneTooFar(){
        myCar.position.x = 105;
        when(myCar.leftLaneDetect()).thenReturn(false);
        myCar.changeLane();
        assertEquals(105, myCar.whereIs().x);
    }


    @Test
    public void testChangeLaneLeftMostLane(){
        myCar.currentLane = 0;
        when(myCar.leftLaneDetect()).thenReturn(false);
        myCar.changeLane();
        assertEquals(0, myCar.whereIs().y);
    }

    @Test
    public void testMoveForwardCloseToEnd(){
        myCar.position.x = 97;
        myCar.moveForward();
        assertEquals(100, myCar.whereIs().x);
    }

    @Test
    public void testWhereIsStandingStill() {
        assertEquals(myCar.position.x, myCar.whereIs().x);
        assertEquals(myCar.position.y, myCar.whereIs().y);

    }

    @Test
    public void testWhereIsMoveForward(){
        assertEquals(myCar.position.x, myCar.whereIs().x);
        assertEquals(myCar.position.y, myCar.whereIs().y);
        myCar.moveForward();
        System.out.println(myCar.position.x + " " + myCar.whereIs().x);
        assertEquals(myCar.position.x, myCar.whereIs().x);
        assertEquals(myCar.position.y, myCar.whereIs().y);
    }

    @Test
    public void testIsNotInLeftMostLane(){
        Car test = new Car();
        //Should call whereIs()
        int result = test.isInleftMostLane(2);
        assertEquals(1,result);
    }

    @Test
    public void testIsInLeftMostLane(){
        Car test = new Car();
        //Should call whereIs()
        int result1 = test.isInleftMostLane(0);
        assertEquals(0, result1);
    }


    @Test
    public void testLaneOccupied(){
        Car test = new Car();
        int currentLane = 2;
        boolean checkLeftLane = true;
        int result = test.leftLaneOccupied(currentLane, checkLeftLane);
        assertEquals(2,currentLane);
    }
    @Test
    public void testLaneNotOccupied(){
        Car test = new Car();
        int currentLane = 2;
        boolean checkLeftLane = false;
        int result2 = test.leftLaneOccupied(currentLane, checkLeftLane);
        assertEquals(1, result2);
    }

    @Test
    public void testEndOfRoad(){
        Car test = new Car();
        int currentLongitude = 96;
        int result = test.endOfRoad(currentLongitude);
        assertEquals(96, result);
    }
    @Test
    public void testNotEndOfRoad(){
        Car test = new Car();
        int currentLongitude = 50;
        int result2 = test.endOfRoad(currentLongitude);
        assertEquals(55, result2);
    }

    @Test
    public void testDontChangeLane(){
        when(myCar.leftLaneDetect()).thenReturn(true);
        myCar.currentLane = 2;
        myCar.changeLane();
        assertEquals(2, myCar.currentLane);
    }
    @Test
    public void testChangeLane(){
        when(myCar.leftLaneDetect()).thenReturn(false);
        myCar.currentLane = 2;
        myCar.changeLane();
        assertEquals(1, myCar.currentLane);
    }

    @Test
    public void testDontChangeLane2(){
        when(myCar.leftLaneDetect()).thenReturn(false);
        myCar.currentLane = 0;
        //test.changeLane(checkLeftLane);
        assertEquals(0, myCar.currentLane);

    }
    @Test
    public void testChangeLane2(){
        myCar.currentLane = 2;
        boolean checkLeftLane = false;
        when(myCar.leftLaneDetect()).thenReturn(false);
        myCar.changeLane();
        assertEquals(1, myCar.currentLane);
        when(myCar.leftLaneDetect()).thenReturn(true);
        myCar.changeLane();
        assertEquals(1, myCar.currentLane);

    }
    @Test
    public void testDontChangeLaneDueToOccupied(){
        Car test = new Car();
        test.currentLane = 1;
        when(myCar.leftLaneDetect()).thenReturn(true);
        test.changeLane();
        assertEquals(1, test.currentLane);

    }


    @Test
    public void testScenarioChangeLaneOccupied(){
        // Model.Car starts to move forward
        ac.move();
        ac.move();
        // Model.Car tries to turn left
        when(myCar.leftLaneDetect()).thenReturn(true);
        ac.switchLane();
        // Check that the car did not turn left
        assertEquals(new Point(15,0), myCar.whereIs());
        // Continues to move until it reaches the end of the street
        while(myCar.whereIs().x < 100){
            ac.move();
        }
        // Check that it reaches the end of the street and hasn not changed lane
        assertEquals(new Point(100,0),myCar.whereIs());
    }

    @Test
    public void testScenarioChangeToLaneVacant(){
        // Model.Car starts to move forward
        ac.move();
        ac.move();
        // Model.Car tries to turn left
        when(myCar.leftLaneDetect()).thenReturn(false);
        ac.switchLane();
        // Check that the car did turn left
        assertEquals(new Point(15,5), myCar.whereIs());
        // Continues to move until it reaches the end of the street
        while(myCar.whereIs().x < 100){
            ac.move();
        }
        // Check that it reaches the end of the street and has changed lane
        assertEquals(new Point(100,5),myCar.whereIs());
    }

    @Test
    public void testScenarioEndOfRoad(){
        //Mocking the lidar output to detect no car in neighboring lane
        myCar.setLidar(-1,-1);
        for(int i = 0; i < 3; i++){
            myCar.setRadar(i, -1,-1);
        }

        //Mocking the start position by assigning values manually
        myCar.position.y = 5;
        myCar.position.x = 0;

        //Asserting the expected value: the car will not move sideways (y-position). It will not move f
        Point expectedPosition = new Point(100,5);

        while (myCar.whereIs().x < expectedPosition.x){
            ac.move();
        }
        //Checking the left lane for vacancy, with mocked values
        //Trying to change lane
        ac.switchLane();

        Point result = myCar.whereIs();
        //Expected: should not change lane.
        assertEquals(expectedPosition, result);
    }

    @Test
    public void scenario4() {

        for (int i = 0; i < 5; i++) {
            ac.move();
        }
        myCar.setLidar(-1, -1);
        for (int i = 0; i < 3; i++) {
            myCar.setRadar(i, -1, -1);
        }
        ac.switchLane();
        assertEquals(new Point(30, 5), myCar.whereIs());
        assertEquals(1, myCar.currentLane);
        for (int i = 0; i < 4; i++) {
            ac.move();
        }
        assertEquals(new Point(50, 5), myCar.whereIs());
        assertEquals(1, myCar.currentLane);
        for (int i = 0; i < 2; i++) {
            ac.car.setRadar(i, 231, 98098);
        }
        ac.switchLane();
        assertEquals(new Point(55, 10), myCar.whereIs());
        assertEquals(0, myCar.currentLane);
        for (int i = 0; i < 9; i++) {
            ac.move();
        }
        assertEquals(new Point(100, 10), myCar.whereIs());
        assertEquals(0, myCar.currentLane);

    }
}