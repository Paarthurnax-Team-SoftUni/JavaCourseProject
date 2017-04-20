package controllers;

import annotations.Alias;
import dataHandler.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import models.sprites.PlayerCar;
import utils.constants.*;
import utils.stages.StageManager;
import utils.stages.StageManagerImpl;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ChooseCarController {

    public static final String FIRST_CAR_TOOLTIP = "Your first car. Let her sucking motivate you to win something.";
    public static final String SECOND_CAR_TOOLTIP = "Gives you double rockets... because we all know you are a psycho.";
    public static final String THIRD_CAR_TOOLTIP = "Double immortality bonus. We call it Chuck Norris's car.";
    public static final String FOURTH_CAR_TOOLTIP = "Doubles the bonus points timer. If you want to build a wall and declare bankruptcy - you will love it.";
    public static final String FIFTH_CAR_TOOLTIP = "Get 50 Hp from the health packs. This one is boring, so if you use it we want tell anyone.";
    public static final String SIXTH_CAR_TOOLTIP = "Doubles all bonuses. Congratulations you defeated SoftUni Rush. You either have lots of free time or fixed your result in the DB.";
    @FXML
    private Button goNextBtn;
    @FXML
    private Ellipse backgroundBox1;
    @FXML
    private Ellipse backgroundBox2;
    @FXML
    private Ellipse backgroundBox3;
    @FXML
    private Ellipse backgroundBox4;
    @FXML
    private Ellipse backgroundBox5;
    @FXML
    private Ellipse backgroundBox6;
    @FXML
    private ImageView locked1;
    @FXML
    private ImageView locked2;
    @FXML
    private ImageView locked3;
    @FXML
    private ImageView locked4;
    @FXML
    private ImageView locked5;
    @FXML
    private ImageView locked6;
    @FXML
    private ImageView car1;
    @FXML
    private ImageView car2;
    @FXML
    private ImageView car3;
    @FXML
    private ImageView car4;
    @FXML
    private ImageView car5;
    @FXML
    private ImageView car6;

    public void initialize() throws NoSuchFieldException, IllegalAccessException {
        long highScores = PlayerData.getInstance().getCurrentPlayer().getHighScore();
        showUnlockedCarsOnly(highScores);

        Tooltip.install(car1, new Tooltip(FIRST_CAR_TOOLTIP));
        Tooltip.install(car2, new Tooltip(SECOND_CAR_TOOLTIP));
        Tooltip.install(car3, new Tooltip(THIRD_CAR_TOOLTIP));
        Tooltip.install(car4, new Tooltip(FOURTH_CAR_TOOLTIP));
        Tooltip.install(car5, new Tooltip(FIFTH_CAR_TOOLTIP));
        Tooltip.install(car6, new Tooltip(SIXTH_CAR_TOOLTIP));

    }

    @FXML
    private void goToChooseMode(ActionEvent actionEvent) {
        Stage currentStage = (Stage) this.goNextBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();
        FXMLLoader loader = manager.loadSceneToStage(currentStage, ViewsConstants.CHOOSE_MODE_VIEW_PATH);
    }

    @FXML
    private void chooseCar(MouseEvent ev) throws NoSuchFieldException, IllegalAccessException {
        Node source = (Node) ev.getSource();
        String id = source.getId();
        backgroundFill(id.substring(id.length() - CarConstants.CARS_LIST_LENGTH_OFFSET));
        if (id.substring(0, 3).equals(CarConstants.CAR_STRING)) {
            PlayerData.getInstance().getCurrentPlayer().updateCar(this.generateCar(id));
            PlayerData.getInstance().updateCarId(id);
        }
        this.goNextBtn.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private PlayerCar generateCar(String id) {
        File carsFolder = new File(ResourcesConstants.CARS_CLASSES_PATH);
        PlayerCar playerCar = null;
        for (File file : carsFolder.listFiles()) {
            if (!file.isFile() || !file.getName().endsWith(ResourcesConstants.JAVA_EXTENSION)) {
                continue;
            }
            try {
                String className = file.getName().substring(0, file.getName().lastIndexOf("."));
                Class<PlayerCar> exeClass = (Class<PlayerCar>) Class.forName(ResourcesConstants.COMMAND_PACKAGE + className);
                if(!exeClass.isAnnotationPresent(Alias.class)) {
                    continue;
                }

                Alias alias = exeClass.getAnnotation(Alias.class);
                String value = alias.value();

                if(!value.equalsIgnoreCase(id)) {
                    continue;
                }

                Constructor carCtor = exeClass.getConstructor();
                playerCar = (PlayerCar) carCtor.newInstance();
            } catch (ReflectiveOperationException roe) {
                roe.printStackTrace();
            }
        }
        if (playerCar == null) {
            throw new IllegalStateException();
        }
        return playerCar;
    }

    @FXML
    private void showUnlockedCarsOnly(Long points) throws NoSuchFieldException, IllegalAccessException {
        Class<ChooseCarController> chooseCarControllerClass = ChooseCarController.class;
        for (int id = 1; id <= CarConstants.CARS_LIST.length; id++) {
            Field ellipseField = chooseCarControllerClass.getDeclaredField(ImagesShortcutConstants.BACKGROUND_STRING + id);
            Ellipse ellipse = ((Ellipse) ellipseField.get(this));
            ellipse.setStyle(null);
            Field lockedField = chooseCarControllerClass.getDeclaredField(ImagesShortcutConstants.LOCKED_CAR_STRING + id);
            ImageView locked = ((ImageView) lockedField.get(this));
            locked.setVisible(false);
            if (points < (id - 1) * GameplayConstants.CAR_UNLOCK_STEP_PTS) {
                ellipse.setStyle(StylesConstants.GREY_COLOUR);
                ellipse.toFront();
                locked.setVisible(true);
            }
        }
    }

    @FXML
    private void backgroundFill(String id) throws NoSuchFieldException, IllegalAccessException {
        long highScores = PlayerData.getInstance().getCurrentPlayer().getHighScore();
        showUnlockedCarsOnly(highScores);

        Class<ChooseCarController> chooseCarControllerClass = ChooseCarController.class;
        Field field = chooseCarControllerClass.getDeclaredField(ImagesShortcutConstants.BACKGROUND_STRING + id);
        ((Ellipse) field.get(this)).setStyle(StylesConstants.RED_COLOUR);
    }
}