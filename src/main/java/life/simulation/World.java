package life.simulation;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public interface World {
    int getWidth();
    int getHeight();
    Cell getCell(int x, int y);
    Stream<Cell> streamCells();
    World step(BiFunction<World, Cell, Boolean> rule);
}
