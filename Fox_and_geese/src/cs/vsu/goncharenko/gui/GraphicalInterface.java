package cs.vsu.goncharenko.gui;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import cs.vsu.goncharenko.entity.field.Field;
import cs.vsu.goncharenko.service.GameService;

import java.util.ArrayList;
import java.util.List;

public class GraphicalInterface extends Application {

    private int countOfClicks = 0;
    private List<ButtonCell> buttonCellsList = new ArrayList<>();

    private final int FIELD_HEIGHT = 600;
    private final int FIELD_WIDTH = 600;

    private final String STYLE_FOR_NON_CELL =
            "-fx-background-radius: 5em; " +
            "-fx-min-width: " + FIELD_WIDTH / 7 +";" +
            "-fx-min-height: " + FIELD_HEIGHT / 7 +";" +
            "-fx-max-width: " + FIELD_WIDTH / 7 +";" +
            "-fx-max-height: " + FIELD_HEIGHT / 7 +";" +
            "-fx-background-color: white;" +
            "-fx-border-color: black;" +
            "-fx-border-radius: 5em;" +
            "-fx-background-insets: 0px; ";

    private final String STYLE_FOR_FOX_CELL =
            "-fx-background-radius: 5em; " +
                    "-fx-min-width: " + FIELD_WIDTH / 7 +";" +
                    "-fx-min-height: " + FIELD_HEIGHT / 7 +";" +
                    "-fx-max-width: " + FIELD_WIDTH / 7 +";" +
                    "-fx-max-height: " + FIELD_HEIGHT / 7 +";" +
                    "-fx-background-color: orange;" +
                    "-fx-border-color: black;" +
                    "-fx-border-radius: 5em;" +
                    "-fx-background-insets: 0px; ";

    private final String STYLE_FOR_GOOSE_CELL =
            "-fx-background-radius: 5em; " +
                    "-fx-min-width: " + FIELD_WIDTH / 7 +";" +
                    "-fx-min-height: " + FIELD_HEIGHT / 7 +";" +
                    "-fx-max-width: " + FIELD_WIDTH / 7 +";" +
                    "-fx-max-height: " + FIELD_HEIGHT / 7 +";" +
                    "-fx-background-color: gray;" +
                    "-fx-border-color: black;" +
                    "-fx-border-radius: 5em;" +
                    "-fx-background-insets: 0px; ";

    private final String STYLE_FOR_FOX_CELL_SELECTED =
            "-fx-background-radius: 5em; " +
                    "-fx-min-width: " + FIELD_WIDTH / 7 +";" +
                    "-fx-min-height: " + FIELD_HEIGHT / 7 +";" +
                    "-fx-max-width: " + FIELD_WIDTH / 7 +";" +
                    "-fx-max-height: " + FIELD_HEIGHT / 7 +";" +
                    "-fx-background-color: orange;" +
                    "-fx-border-color: blue;" +
                    "-fx-border-radius: 5em;" +
                    "-fx-background-insets: 0px; ";

    private final String STYLE_FOR_GOOSE_CELL_SELECTED =
            "-fx-background-radius: 5em; " +
                    "-fx-min-width: " + FIELD_WIDTH / 7 +";" +
                    "-fx-min-height: " + FIELD_HEIGHT / 7 +";" +
                    "-fx-max-width: " + FIELD_WIDTH / 7 +";" +
                    "-fx-max-height: " + FIELD_HEIGHT / 7 +";" +
                    "-fx-background-color: gray;" +
                    "-fx-border-color: blue;" +
                    "-fx-border-radius: 5em;" +
                    "-fx-background-insets: 0px; ";

    private final String STYLE_FOR_NON_CELL_SELECTED =
            "-fx-background-radius: 5em; " +
                    "-fx-min-width: " + FIELD_WIDTH / 7 +";" +
                    "-fx-min-height: " + FIELD_HEIGHT / 7 +";" +
                    "-fx-max-width: " + FIELD_WIDTH / 7 +";" +
                    "-fx-max-height: " + FIELD_HEIGHT / 7 +";" +
                    "-fx-background-color: green;" +
                    "-fx-border-color: blue;" +
                    "-fx-border-radius: 5em;" +
                    "-fx-background-insets: 0px; ";


    @Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = new GridPane();
        Field field = new Field();
        GameService.createField(field, "C4", 13);
        for (int i = 1; i <= 7; i++) {
            for (char j = 'A'; j <= 'G'; j++) {
                if(!((i == 1 || i == 2 || i == 6 || i == 7) && (j == 'A' || j == 'B' || j == 'F' || j == 'G'))) {
                    ButtonCell buttonCell = new ButtonCell("" + j + i, new Button());
                    buttonCell.getButton().setStyle(STYLE_FOR_NON_CELL);

                    if(field.isOnCell("" + j + i)) {
                        if (field.getCell("" + j + i).getContentCell().getNameOfFeature().equals("Goose")) {
                            buttonCell.getButton().setStyle(STYLE_FOR_GOOSE_CELL);
                        }
                        if (field.getCell("" + j + i).getContentCell().getNameOfFeature().equals("Fox")) {
                            buttonCell.getButton().setStyle(STYLE_FOR_FOX_CELL);
                        }
                    }
                    this.buttonCellsList.add(buttonCell);

                    buttonCell.getButton().setOnAction(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            countOfClicks++;
                            if(field.getCell(buttonCell.getNameCell()).getContentCell() == null){
                                buttonCell.getButton().setStyle(STYLE_FOR_NON_CELL);
                            } else {
                                if (field.getCell(buttonCell.getNameCell()).getContentCell().getNameOfFeature().equals("Fox")) {
                                    buttonCell.getButton().setStyle(STYLE_FOR_FOX_CELL_SELECTED);
                                }
                                if (field.getCell(buttonCell.getNameCell()).getContentCell().getNameOfFeature().equals("Goose")) {
                                    buttonCell.getButton().setStyle(STYLE_FOR_GOOSE_CELL_SELECTED);
                                }
                            }
                            clearButtons(field);
                        }

                    });

                    gridPane.add(buttonCell.getButton(), i, j);
                }

            }
        }


        stage.setScene(new Scene(gridPane, FIELD_HEIGHT, FIELD_HEIGHT));
        stage.show();
    }

    private void clearButtons(Field field) {
        if(countOfClicks == 2) {
            for (int k = 0; k < buttonCellsList.size(); k++) {
                String cell = buttonCellsList.get(k).getNameCell();

                if(field.isOnCell(cell)) {
                    if(field.getCell(cell).getContentCell().getNameOfFeature().equals("Fox")){
                        buttonCellsList.get(k).getButton().setStyle(STYLE_FOR_FOX_CELL);
                    }
                    if(field.getCell(cell).getContentCell().getNameOfFeature().equals("Goose")){
                        buttonCellsList.get(k).getButton().setStyle(STYLE_FOR_GOOSE_CELL);
                    }
                }else {
                    buttonCellsList.get(k).getButton().setStyle(STYLE_FOR_NON_CELL);
                }
            }
            countOfClicks = 0;
        }
    }

    /*@Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = new GridPane();
        Field field = new Field();
        GameService.createField(field, "C4", 13);
        for (int i = 1; i <= 7; i++) {
            for (char j = 'A'; j <= 'G'; j++) {
                Circle circle = new Circle();
                Color color = Color.WHITE;
                if(!((i == 1 || i == 2 || i == 6 || i == 7) && (j == 'A' || j == 'B' || j == 'F' || j == 'G'))) {
                    if(field.isOnCell("" + j + i)) {
                        if (field.getCell("" + j + i).getContentCell().getNameOfFeature().equals("Goose")) {
                            color = Color.GRAY;
                        }
                        if (field.getCell("" + j + i).getContentCell().getNameOfFeature().equals("Fox")) {
                            color = Color.ORANGE;
                        }
                    }
                    circle.setFill(color);
                    circle.setStroke(Color.BLACK);
                    gridPane.add(circle, i, j);
                    circle.radiusProperty().bind(gridPane.widthProperty().divide(14.5));
                    circle.radiusProperty().bind(gridPane.heightProperty().divide(14.5));
                }
            }
        }
        stage.setResizable(false);
        stage.setScene(new Scene(gridPane, 600, 600));
        stage.show();
    }*/
}
