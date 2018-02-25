import Model.Lidar;
import org.junit.Test;

import static org.junit.Assert.*;

public class LidarTest {
    Lidar lidar = new Lidar();

    @Test
    public void lidar_reading() {
        lidar.setReading(5);
        assertEquals(5, lidar.getReading());
    }
    @Test
    public void lidar_previousReading(){
        lidar.setReading(10);
        lidar.setReading(15);
        assertEquals(10, lidar.getPreviousReading());
    }

}