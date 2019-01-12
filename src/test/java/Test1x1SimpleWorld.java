import org.junit.Test;
import static org.junit.Assert.*;
import life.simulation.*;
import java.util.*;
 
public class Test1x1SimpleWorld {
    @Test
    public void creation() {
        World w = new SimpleWorld(1, 1, Arrays.asList(true));

        assertEquals(w.getWidth(), 1);
        assertEquals(w.getHeight(), 1);
        assertEquals(w.streamCells().count(), 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void creationFail1() {
        World w = new SimpleWorld(1, 1, Arrays.asList());
    }

    @Test(expected=IllegalArgumentException.class)
    public void creationFail2() {
        World w = new SimpleWorld(1, 1, Arrays.asList(true, true));
    }

    @Test(expected=IllegalArgumentException.class)
    public void creationFail3() {
        World w = new SimpleWorld(-1, -1, Arrays.asList(true));
    }

    @Test
    public void zeroCell() {
        World w = new SimpleWorld(1, 1, Arrays.asList(true));
        Cell c = w.getCell(0, 0);

        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 0);
        assertEquals(c.getValue(), true);
        c.setValue(false);
        assertEquals(c.getValue(), false);
    }

    @Test
    public void outsideCell() {
        World w = new SimpleWorld(1, 1, Arrays.asList(true));
        Cell c = w.getCell(100, -200);

        assertEquals(c.getX(), 100);
        assertEquals(c.getY(), -200);
        assertEquals(c.getValue(), false);
        c.setValue(true);
        assertEquals(c.getValue(), false);
    }

    @Test
    public void step(){
        World w1 = new SimpleWorld(1, 1, Arrays.asList(true));
        World w2 = w1.step((_w, c) -> !c.getValue());

        assertNotSame(w1, w2);
        assertEquals(w1.getWidth(), w2.getWidth());
        assertEquals(w1.getHeight(), w2.getHeight());
        assertEquals(w1.streamCells().count(), w2.streamCells().count());

        Cell c1 = w1.getCell(0, 0);
        Cell c2 = w2.getCell(0, 0);
        assertNotSame(c1, c2);
        assertNotEquals(c1.getValue(), c2.getValue());
    }
}
