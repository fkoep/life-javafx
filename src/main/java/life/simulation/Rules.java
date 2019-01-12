package life.simulation;

import java.util.function.BiFunction;
import java.util.*;

public class Rules {
    public static int countNeighbors(World w, int x, int y){
        int n = 0;
        if (w.getCell(x - 1, y - 1).getValue()) n += 1;
        if (w.getCell(x    , y - 1).getValue()) n += 1;
        if (w.getCell(x + 1, y - 1).getValue()) n += 1;
        if (w.getCell(x - 1, y    ).getValue()) n += 1;
        if (w.getCell(x + 1, y    ).getValue()) n += 1;
        if (w.getCell(x - 1, y + 1).getValue()) n += 1;
        if (w.getCell(x    , y + 1).getValue()) n += 1;
        if (w.getCell(x + 1, y + 1).getValue()) n += 1;
        return n;
    } 

    public static boolean _G(World w, Cell c, int[] death, int[] birth){
        int n = countNeighbors(w, c.getX(), c.getY());
        if (c.getValue()) {
            for (int i = 0; i < death.length; ++i) {
                if (death[i] == n) return true;
            }
        } else {
            for (int i = 0; i < birth.length; ++i) {
                if (birth[i] == n) return true;
            }
        }
        return false;
    }

    public static boolean _2G3(World w, Cell c){
        return _G(w, c, new int[]{2, 3}, new int[]{3}); 
    }
    public static boolean _1245G3(World w, Cell c){
        return _G(w, c, new int[]{1, 2, 3, 4, 5}, new int[]{3}); 
    }
    public static boolean _G1357(World w, Cell c){
        return _G(w, c, new int[]{1, 3, 5, 7}, new int[]{1, 3, 5, 7}); 
    }
}
