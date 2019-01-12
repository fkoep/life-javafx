package life.gui;

import life.simulation.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import java.util.function.*;

public class WorldPane extends Pane {
    private final Canvas canvas = new Canvas();
    private final GraphicsContext graphics = canvas.getGraphicsContext2D();

    private World prev = new DummyWorld();
    private World current;
    private BiFunction<World, Cell, Boolean> rule = Rules::_2G3;
    
    private int xoff = 0;
    private int yoff = 0;

    private int cellSize = 10;
    private Color foreColor = Color.BLACK;
    private Color backColor = Color.WHITE;
    private Color deathColor = Color.RED;
    private Color birthColor = Color.BLUE;
    private boolean drawChange = false;

    public WorldPane(World world){
        current = world;

        canvas.widthProperty().bind(prefWidthProperty());
        canvas.heightProperty().bind(prefHeightProperty());
        getChildren().add(canvas);
        widthProperty().addListener(_e -> redraw());
        heightProperty().addListener(_e -> redraw());

        setFocusTraversable(true); // we may receive Keyboard events
        addEventFilter(MouseEvent.MOUSE_PRESSED, this::onBrushEvent);
        addEventFilter(MouseEvent.MOUSE_DRAGGED, this::onBrushEvent);
        addEventFilter(KeyEvent.KEY_PRESSED, this::onMoveEvent);
    }

    private void onBrushEvent(MouseEvent e) {
        MouseButton b = e.getButton();
        if (b != MouseButton.PRIMARY && b != MouseButton.SECONDARY) return;
        if (e.getX() < 0 || e.getY() < 0) return;
        int x = (int) (e.getX() / cellSize) + xoff;
        int y = (int) (e.getY() / cellSize) + yoff;
        modifyWorld(w -> w.getCell(x, y).setValue(b == MouseButton.PRIMARY));
    }

    private void onMoveEvent(KeyEvent e) {
        KeyCode c = e.getCode();
        switch (c) {
            case LEFT:  case KP_LEFT:  xoff -= 1; break;
            case RIGHT: case KP_RIGHT: xoff += 1; break;
            case UP:    case KP_UP:    yoff -= 1; break;
            case DOWN:  case KP_DOWN:  yoff += 1; break;
            default: return;
        }
        redraw();
    }

    public void setRule(BiFunction<World, Cell, Boolean> r){ rule = r; }

    public void mapWorld(UnaryOperator<World> f){
        current = f.apply(current);
        redraw();
    }

    public void modifyWorld(Consumer<World> f){
        mapWorld(w -> { f.accept(w); return w; });
    }

    public void evolveWorld(){
        prev = current;
        mapWorld(w -> current.step(rule));
    }

    public void setCellSize(int s){ cellSize = s; redraw(); }
    public void setForeColor(Color c){ foreColor = c; redraw(); }
    public void setBackColor(Color c){ backColor = c; redraw(); }
    public void setDeathColor(Color c){ deathColor = c; redraw(); }
    public void setBirthColor(Color c){ birthColor = c; redraw(); }
    public void setDrawChange(boolean b){ drawChange = b; redraw(); }

    private void drawCell(Color c, int x, int y){
        graphics.setFill(c);
        graphics.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
    }

    private void redraw(){
        int w = (int) Math.ceil(canvas.getWidth() / cellSize);
        int h = (int) Math.ceil(canvas.getHeight() / cellSize);

        graphics.setFill(backColor);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int x = 0; x < w; ++x){
            for (int y = 0; y < h; ++y){
                boolean prevVal = prev.getCell(x + xoff, y + yoff).getValue();
                boolean currentVal = current.getCell(x + xoff, y + yoff).getValue();

                if (currentVal) {
                    drawCell(foreColor, x, y);
                }
                if (drawChange && !currentVal && prevVal) {
                    drawCell(deathColor, x, y);
                } else if (drawChange && currentVal && !prevVal) {
                    drawCell(birthColor, x, y);
                }
            }
        }
    }
}
