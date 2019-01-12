package life.simulation;
import java.util.function.BiFunction;
import java.util.stream.*;

public class DummyWorld implements World {
    private class DummyCell implements Cell {
        private int x;
        private int y;

        private DummyCell(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public int getX(){ return x; }

        @Override
        public int getY(){ return y; }

        @Override
        public boolean getValue(){ return false; }

        @Override
        public void setValue(boolean v){}
    }

    public DummyWorld(){}

    @Override
    public int getWidth(){ return 0; }

    @Override
    public int getHeight(){ return 0; }

    @Override
    public Cell getCell(int x, int y){ return new DummyCell(x, y); }

    @Override
    public Stream<Cell> streamCells(){ return Stream.of(); }

    @Override
    public World step(BiFunction<World, Cell, Boolean> p){ return this; }
}
