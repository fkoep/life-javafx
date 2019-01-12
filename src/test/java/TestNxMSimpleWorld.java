import org.junit.Test;
import static org.junit.Assert.*;
import life.simulation.*;
import java.util.stream.*;
import java.util.*;
 
public class TestNxMSimpleWorld {
    private final int N = 42;
    private final int M = 100;
    private final List<Boolean> values(){
        return IntStream.range(0, 42 * 100)
            .mapToObj(i -> true)
            .collect(Collectors.toList());
    }

    @Test
    public void creation() {
        World w = new SimpleWorld(N, M, values());

        assertEquals(w.getWidth(), N);
        assertEquals(w.getHeight(), M);
        assertEquals(w.streamCells().count(), N * M);
    }

    @Test(expected=IllegalArgumentException.class)
    public void creationFail1() {
        World w = new SimpleWorld(N * M, 0, values());
    }

    @Test(expected=IllegalArgumentException.class)
    public void creationFail2() {
        World w = new SimpleWorld(N + 1, M + 1, values());
    }

    @Test(expected=IllegalArgumentException.class)
    public void creationFail3() {
        World w = new SimpleWorld(-N, -M, values());
    }

    @Test
    public void zeroCell() {
        World w = new SimpleWorld(N, M, values());
        Cell c = w.getCell(0, 0);

        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 0);
        assertEquals(c.getValue(), true);
        c.setValue(false);
        assertEquals(c.getValue(), false);
    }

    @Test
    public void lastCell() {
        World w = new SimpleWorld(N, M, values());
        Cell c = w.getCell(N - 1, M - 1);

        assertEquals(c.getX(), N - 1);
        assertEquals(c.getY(), M - 1);
        assertEquals(c.getValue(), true);
        c.setValue(false);
        assertEquals(c.getValue(), false);
    }

    @Test
    public void outsideCell1() {
        World w = new SimpleWorld(N, M, values());
        Cell c = w.getCell(N, M);

        assertEquals(c.getX(), N);
        assertEquals(c.getY(), M);
        assertEquals(c.getValue(), false);
        c.setValue(true);
        assertEquals(c.getValue(), false);
    }

    @Test
    public void outsideCell2() {
        World w = new SimpleWorld(N, M, values());
        Cell c = w.getCell(-1, -1);

        assertEquals(c.getX(), -1);
        assertEquals(c.getY(), -1);
        assertEquals(c.getValue(), false);
        c.setValue(true);
        assertEquals(c.getValue(), false);
    }

    @Test
    public void streamCells() {
        World w = new SimpleWorld(N, M, values());

        int[] i = new int[]{0};
        w.streamCells().forEach(c -> {
            assertEquals(i[0], c.getY() * N + c.getX());
            i[0] += 1;
        });
    }

    @Test
    public void step(){
        World w1 = new SimpleWorld(N, M, values());
        World w2 = w1.step((_w, c) -> !c.getValue());

        assertNotSame(w1, w2);
        assertEquals(w1.getWidth(), w2.getWidth());
        assertEquals(w1.getHeight(), w2.getHeight());
        assertEquals(w1.streamCells().count(), w2.streamCells().count());

        w1.streamCells().forEach(c1 -> {
            Cell c2 = w2.getCell(c1.getX(), c1.getY());
            assertNotSame(w1, w2);
            assertSame(c1.getX(), c2.getX());
            assertSame(c1.getY(), c2.getY());
            assertNotEquals(c1.getValue(), c2.getValue());
        });
    }
}
