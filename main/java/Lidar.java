public class Lidar {
    int previousReading;
    int reading;

    void setReading(int reading){
        this.previousReading = this.reading;
        this.reading = reading;
    }

    int getPreviousReading(){
        return previousReading;
    }

    int getReading(){
        return reading;
    }
}
