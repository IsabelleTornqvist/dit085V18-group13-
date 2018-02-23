import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.awt.*;
import static org.mockito.Mockito.*;

public class ActuatorTest {

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
	public void testMoveNormal() {
		int oldPos = ac.car.whereIs().x;
		ac.move();
		assertEquals(oldPos + 5, ac.car.whereIs().x);
	}

	@Test
	public void testMoveEndOfStreet() {
		ac.car.position.x = 100;
		ac.move();
		assertEquals(100, ac.car.whereIs().x);
	}

	@Test
	public void testMoveCloseEndOfStreet() {
		ac.car.position.x = 97;
		ac.move();
		assertEquals(100, ac.car.whereIs().x);
	}


	@Test
	public void testSwitchLaneNormal() {
		when(car.leftLaneDetect()).thenReturn(false);
		ac.switchLane();
		assertEquals(5, ac.car.whereIs().y);
		assertEquals(1, ac.car.currentLane);
		assertEquals(5, ac.car.whereIs().x);
	}

	@Test
    public void testSwitchLaneLeftLaneOccupied(){
        when(car.leftLaneDetect()).thenReturn(true);
        ac.switchLane();
        assertEquals(0, ac.car.whereIs().y);
        assertEquals(2, ac.car.currentLane);
        assertEquals(5, ac.car.whereIs().x);
    }

    @Test
    public void testSwitchLaneMostLeft(){
	    ac.car.currentLane = 0;
	    ac.car.position.y = 10;
	    ac.switchLane();
        assertEquals(10, ac.car.whereIs().y);
        assertEquals(0, ac.car.currentLane);
        assertEquals(5, ac.car.whereIs().x);
    }

    @Test
    public void testSwitchLaneTwoLeft(){
        when(car.leftLaneDetect()).thenReturn(false);
        ac.switchLane();
        ac.switchLane();
        assertEquals(10, ac.car.whereIs().y);
        assertEquals(0, ac.car.currentLane);
        assertEquals(10, ac.car.whereIs().x);
    }

    @Test
    public void testSwitchLaneAlmostEndOfStreet(){
        when(car.leftLaneDetect()).thenReturn(false);
        ac.car.position.x = 97;
        ac.switchLane();
        assertEquals(0, ac.car.whereIs().y);
        assertEquals(2, ac.car.currentLane);
        assertEquals(100, ac.car.whereIs().x);
    }

    @Test
    public void testSwitchLaneEndOfStreet(){
        when(car.leftLaneDetect()).thenReturn(false);
        ac.car.currentLane = 0;
        ac.car.position.x = 100;
        ac.switchLane();
        assertEquals(0, ac.car.whereIs().y);
        assertEquals(0, ac.car.currentLane);
        assertEquals(100, ac.car.whereIs().x);
    }

}
