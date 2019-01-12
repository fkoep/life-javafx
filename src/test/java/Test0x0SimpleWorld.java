import org.junit.Test;
import static org.junit.Assert.*;
import life.simulation.*;
import java.util.*;
 
public class Test0x0SimpleWorld {
    @Test
    public void creation() {
        World w = new SimpleWorld(0, 0, Arrays.asList());

        assertEquals(w.getWidth(), 0);
        assertEquals(w.getHeight(), 0);
        assertEquals(w.streamCells().count(), 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void creationFail1() {
        World w = new SimpleWorld(0, 0, Arrays.asList(true));
    }

    @Test(expected=IllegalArgumentException.class)
    public void creationFail2() {
        World w = new SimpleWorld(1, 0, Arrays.asList(true));
    }

    @Test
    public void zeroCell() {
        World w = new SimpleWorld(0, 0, Arrays.asList());
        Cell c = w.getCell(0, 0);

        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 0);
        assertEquals(c.getValue(), false);
        c.setValue(true);
        assertEquals(c.getValue(), false);
    }

    @Test
    public void outsideCell() {
        World w = new SimpleWorld(0, 0, Arrays.asList());
        Cell c = w.getCell(100, -200);

        assertEquals(c.getX(), 100);
        assertEquals(c.getY(), -200);
        assertEquals(c.getValue(), false);
        c.setValue(true);
        assertEquals(c.getValue(), false);
    }

    @Test
    public void step(){
        World w = new SimpleWorld(0, 0, Arrays.asList());

        w.step((_w, _c) -> { fail("this should not be called"); return false; });
    }
}
