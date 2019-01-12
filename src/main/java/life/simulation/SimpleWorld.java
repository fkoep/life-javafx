package life.simulation;
import java.util.function.BiFunction;
import java.util.stream.*;
import java.util.*;

public class SimpleWorld implements World {
    private class SimpleCell implements Cell {
        private int x;
        private int y;

        private SimpleCell(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public int getX(){ return x; }

        @Override
        public int getY(){ return y; }

        @Override
        public boolean getValue(){
            if (x < 0 || y < 0 || x >= width || y >= height) {
                return false;
            }
            return values.get(y * width + x); 
        }

        @Override
        public void setValue(boolean v){
            if (x < 0 || y < 0 || x >= width || y >= height) {
                return;
            }
            values.set(y * width + x, v);
        }
    }

    private int width;
    private int height;
    private List<Boolean> values;

    public SimpleWorld(int width, int height, List<Boolean> values){
        if (Math.abs(width) * height != values.size()) {
            throw new IllegalArgumentException("width and height must match up number of values");
        }
        this.width = width;
        this.height = height;
        this.values = values;
    }

    public SimpleWorld(int width, int height){
        this(width, height, IntStream.range(0, width * height)
                .mapToObj(_i -> false)
                .collect(Collectors.<Boolean>toList()));
    }

    @Override
    public int getWidth(){ return width; }

    @Override
    public int getHeight(){ return height; }

    @Override
    public Cell getCell(int x, int y){ return new SimpleCell(x, y); }

    @Override
    public Stream<Cell> streamCells(){ 
        return IntStream.range(0, width * height)
            .mapToObj(i -> new SimpleCell(i % width, i / width));
    }

    @Override
    public World step(BiFunction<World, Cell, Boolean> p){
        return new SimpleWorld(width, height, streamCells()
                .map(c -> p.apply(this, c))
                .collect(Collectors.<Boolean>toList()));
    }
}
