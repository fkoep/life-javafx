import org.junit.Test;
import static org.junit.Assert.*;
import life.simulation.*;
 
public class TestDummyWorld {
    @Test
    public void creation() {
        World w = new DummyWorld();

        assertEquals(w.getWidth(), 0);
        assertEquals(w.getHeight(), 0);
        assertEquals(w.streamCells().count(), 0);
    }

    @Test
    public void zeroCell() {
        World w = new DummyWorld();
        Cell c = w.getCell(0, 0);

        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 0);
        assertEquals(c.getValue(), false);
        c.setValue(true);
        assertEquals(c.getValue(), false);
    }

    @Test
    public void outsideCell() {
        World w = new DummyWorld();
        Cell c = w.getCell(100, -200);

        assertEquals(c.getX(), 100);
        assertEquals(c.getY(), -200);
        assertEquals(c.getValue(), false);
        c.setValue(true);
        assertEquals(c.getValue(), false);
    }

    @Test
    public void step(){
        World w = new DummyWorld();

        w.step((_w, _c) -> { fail("this should not be called"); return false; });
    }
}
