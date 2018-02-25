package Model;

public class Radar {

    int[] radars = new int[3];
    int[] oldreading = new int[3];

    public int getRadarReading(int radar_number) {
        return radars[radar_number];
    }

    public int getOldReading(int radar_number){
        return oldreading[radar_number];
    }

    public void setRadarReading(int radar_number, int reading, int old_reading) {
        oldreading[radar_number] = old_reading;
        radars[radar_number] = reading;
    }
}


