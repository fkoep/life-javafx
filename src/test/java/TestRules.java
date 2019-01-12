import org.junit.Test;
import static org.junit.Assert.*;
import life.simulation.*;
import java.util.stream.*;
import java.util.*;
 
public class TestRules {
    @Test
    public void countNoNeighbors(){
        assertEquals(Rules.countNeighbors(new DummyWorld(), 0, 0), 0);
        assertEquals(Rules.countNeighbors(new SimpleWorld(1, 1, Arrays.asList(true)), 0, 0), 0);
        assertEquals(Rules.countNeighbors(new SimpleWorld(1, 1, Arrays.asList(true)), -2, -2), 0);
    }

    @Test
    public void countOneNeighbor(){
        assertEquals(Rules.countNeighbors(new SimpleWorld(1, 1, Arrays.asList(true)), 1, 1), 1);
        assertEquals(Rules.countNeighbors(new SimpleWorld(1, 1, Arrays.asList(true)), -1, -1), 1);
    }

    @Test
    public void countSomeNeighbors() {
        World w = new SimpleWorld(3, 1, Arrays.asList(true, false, true));
        assertEquals(Rules.countNeighbors(w, 1, 0), 2);
        assertEquals(Rules.countNeighbors(w, 0, 1), 1);
        assertEquals(Rules.countNeighbors(w, 1, -1), 2);
    }

    private final int N = 42;
    private final int M = 100;
    private final List<Boolean> values(){
        return IntStream.range(0, 42 * 100)
            .mapToObj(i -> i % 2 == 0)
            .collect(Collectors.toList());
    }

    @Test
    public void countOddEvenNeighbors() {
        World w = new SimpleWorld(N, M, values());
        assertEquals(Rules.countNeighbors(w, 0, 0), 1);
        assertEquals(Rules.countNeighbors(w, 1, 0), 4);
        assertEquals(Rules.countNeighbors(w, 0, 1), 2);
    }

    @Test
    public void _2G3(){
        World w = new SimpleWorld(N, M, values());
        assertEquals(Rules._2G3(w, w.getCell(0, 0)), false);
        assertEquals(Rules._2G3(w, w.getCell(1, 0)), false);
        assertEquals(Rules._2G3(w, w.getCell(0, 1)), true);
    }

    @Test
    public void _1245G3(){
        World w = new SimpleWorld(N, M, values());
        assertEquals(Rules._1245G3(w, w.getCell(0, 0)), true);
        assertEquals(Rules._1245G3(w, w.getCell(1, 0)), false);
        assertEquals(Rules._1245G3(w, w.getCell(0, 1)), true);
    }

    @Test
    public void _G1357(){
        World w = new SimpleWorld(N, M, values());
        assertEquals(Rules._G1357(w, w.getCell(0, 0)), true);
        assertEquals(Rules._G1357(w, w.getCell(1, 0)), false);
        assertEquals(Rules._G1357(w, w.getCell(0, 1)), false);
    }
}
