package Model;

import java.awt.*;

public class Car {
    //void radar_detect_car()
    private Lidar lidar;
    private Radar radar;

    private int noCarCount;
    public int currentLane;
    public Point position;

    public Car(){
        lidar = new Lidar();
        radar = new Radar();
        noCarCount = 0;
        currentLane = 2;
        position = new Point(0,0);
    }

    // radar_detect_car()
    public void setRadar(int radar_number, int reading, int oldReading) {
        radar.setRadarReading(radar_number, reading, oldReading);
    }

    public void moveForward() {
        if (position.x > 95 && position.x < 100) {
            position.x = 100;
        }
        if (position.x < 100) {
            position.x += 5;
        } else {
            System.out.println("Model.Car is at the end of the street!");
        }
    }

    public Point whereIs(){

        return this.position;
    }


    public boolean leftLaneDetect() {
        //Radar_no_car()
        noCarCount = 0;
        //Radar_no_car()
        for(int i = 0; i < radar.radars.length; i++){
            if(0 <= radar.getRadarReading(i) && radar.getRadarReading(i) <= 50 && 0 <= radar.getOldReading(i) && radar.getOldReading(i) <= 50){
                return true;
            }
        }
        //Radar_no_car()
        if(0 <= lidar.getReading() && lidar.getReading() <= 50 && 0 <= lidar.getPreviousReading() && lidar.getPreviousReading() <= 50){
            return true;
        }
        //Radar_no_car()
        for(int i = 0; i < radar.radars.length; i++){
            if(radar.getRadarReading(i) == -1 && radar.getOldReading(i) == -1){
                noCarCount++;
            }
        }
        //Radar_no_car()
        if(lidar.getPreviousReading() == -1 && lidar.getReading() == -1){
            noCarCount++;
        }
        //Radar_no_car()
        if(noCarCount >= 2){
            return false;
        }

        //not_enough_readings()
        System.out.print("Not enough correct readings");
        //radar_detect_car_1()
        return true;
    }

    public void setLidar(int oldreading, int newreading) {
        lidar.setReading(oldreading);
        lidar.setReading(newreading);
    }

    public int isInleftMostLane(int currentLane) {
        if(currentLane == 0){
            System.out.println("You are in the left most lane!");
            return 0;
        }else {
            return --currentLane;
        }
    }

    public int leftLaneOccupied(int currentLane, boolean mockDataCheckLeftLane) {
        if(mockDataCheckLeftLane){
            System.out.println("Left lane is occupied!");
            return currentLane;
        }
        return --currentLane;
    }

    public int endOfRoad(int currentLongitude) {
        if(currentLongitude > 95){
            System.out.println("Can't move, end of road!");
            return currentLongitude;
        }
        currentLongitude += 5;
        return currentLongitude;
    }

    public void changeLane() {
        if(this.position.x > 100){
            System.out.println("Can't move, end of road!");
        }else if(this.position.x > 95){
            moveForward();
        }else {
            if(currentLane == 0){
                System.out.println("You are in the left most lane!");
                //Should call moveForward();
                moveForward();
            }
            //Should call checkLeftLane()
            else if(this.leftLaneDetect()){
                System.out.println("Left lane is occupied!");
                //Should call moveForward();
                moveForward();
            }
            else {
                //Should call moveForward();
                moveForward();
                this.position.y += 5;
                currentLane--;
            }
        }
    }
}