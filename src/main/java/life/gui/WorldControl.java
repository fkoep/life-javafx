package life.gui;

import life.simulation.*;
import com.google.gson.GsonBuilder;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Slider;
import javafx.stage.*;
import javafx.util.Duration;
import java.util.function.BiFunction;
import java.io.*;
import java.util.*;

public class WorldControl {
    private WorldPane world;
    private Timeline timeline = new Timeline();
    private int cycleMs = 500;

    @FXML
    private ComboBox<String> selectRuleBox;
    private List<BiFunction<World, Cell, Boolean>> rules = Arrays.asList(Rules::_2G3, Rules::_1245G3, Rules::_G1357);

    @FXML
    private Slider cellSizeSlider;
    @FXML
    private Slider speedSlider;

    public WorldControl(WorldPane w){
        world = w;
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    public void initialize() {
        selectRuleBox.getItems().addAll("2G3", "1245G3", "G1357");
        selectRuleBox.getSelectionModel().select(0);

        // Slider doesn't generate a proper event for some reason, so we have to hook up 
        // with its value property.
        cellSizeSlider.valueProperty().addListener((_obs, _old, val) -> {
            world.setCellSize(val.intValue());
        });
        speedSlider.valueProperty().addListener((_obs, _old, val) -> {
            cycleMs = val.intValue();
            if (isRunning()) run();
        });
    }

    @FXML
    public void step(){
        world.evolveWorld();
    }

    @FXML
    public boolean isRunning(){
        return timeline.getStatus() == Animation.Status.RUNNING;
    }

    @FXML
    public void run(){
        if (isRunning()) stop();
        timeline.getKeyFrames().add(new KeyFrame(new Duration(cycleMs), _ev -> step()));
        timeline.play();
    }

    @FXML
    public void stop(){
        timeline.getKeyFrames().clear();
        timeline.stop();
    }

    @FXML
    public void clear(){
        world.modifyWorld(w -> w.streamCells().forEach(c -> c.setValue(false)));
    }

    @FXML
    public void randomize(){
        Random r = new Random();
        world.modifyWorld(w -> w.streamCells().forEach(c -> c.setValue(r.nextBoolean())));
    }

    @FXML
    public void toggleDrawChange(ActionEvent ev){
        world.setDrawChange(((ToggleButton) ev.getSource()).isSelected());
    }

    @FXML
    public void setForeColor(ActionEvent ev){
        world.setForeColor(((ColorPicker) ev.getSource()).getValue());
    }

    @FXML
    public void setBackColor(ActionEvent ev){
        world.setBackColor(((ColorPicker) ev.getSource()).getValue());
    }

    @FXML
    public void setDeathColor(ActionEvent ev){
        world.setDeathColor(((ColorPicker) ev.getSource()).getValue());
    }

    @FXML
    public void setBirthColor(ActionEvent ev){
        world.setBirthColor(((ColorPicker) ev.getSource()).getValue());
    }

    @FXML
    public void setRule(){
        world.setRule(rules.get(selectRuleBox.getSelectionModel().getSelectedIndex()));
    }

    @FXML
    public void openFile() throws IOException{
        File file = (new FileChooser()).showOpenDialog(new Stage());
        if (file == null) return;
        try (Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
            world.mapWorld(_w -> (new GsonBuilder().create()).fromJson(reader, SimpleWorld.class));
        }
    }

    @FXML
    public void saveFile() throws IOException {
        File file = (new FileChooser()).showSaveDialog(new Stage());
        if (file == null) return;
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
            world.modifyWorld(w -> (new GsonBuilder().create()).toJson(w, writer));
        }
    }
}
