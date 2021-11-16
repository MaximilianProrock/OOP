package cs.vsu.goncharenko.gui;
import com.sun.javafx.tk.quantum.PrimaryTimer;
import cs.vsu.goncharenko.reader.ReaderFromFile;
import cs.vsu.goncharenko.writer.WriterToFile;
import cs.vsu.goncharenko.game.Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class GraphicalInterface extends Application {

    private int countOfClicks = 0;
    private Game game;

    private final int FIELD_HEIGHT = 600;
    private final int FIELD_WIDTH = 600;

    private ButtonCell prevButton;

    private Label labelPlayers = new Label();

    private int ticksFromStart = 0;

    Queue<String[]> moves = fillMoves();

    private boolean showScript = false;

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

    private final String STYLE_FOR_CELL_SELECTED = "-fx-border-color: blue;";


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Fox and Geese");

        Label labelErrors = new Label();
        TextField textField = new TextField();
        textField.setPrefColumnCount(5);


        TextField fieldForCountOfGeese = new TextField();
        fieldForCountOfGeese.setPrefColumnCount(2);
        fieldForCountOfGeese.setText("15");

        TextField fieldForCellForFoxFeature = new TextField();
        fieldForCountOfGeese.setPrefColumnCount(2);
        fieldForCellForFoxFeature.setText("C4");

        TextField fieldForNamePlayerForFox = new TextField();
        fieldForCountOfGeese.setPrefColumnCount(10);
        fieldForNamePlayerForFox.setText("Player1");

        TextField fieldForNamePlayerForGeese = new TextField();
        fieldForCountOfGeese.setPrefColumnCount(10);
        fieldForNamePlayerForGeese.setText("Player2");

        Button buttonStart = new Button("Start");

        FlowPane root = new FlowPane(
                Orientation.VERTICAL,
                10,
                10,
                fieldForCountOfGeese,
                fieldForCellForFoxFeature,
                fieldForNamePlayerForFox,
                fieldForNamePlayerForGeese,
                buttonStart,
                labelErrors
        );

        buttonStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                createGameInterface(
                        fieldForCountOfGeese,
                        fieldForCellForFoxFeature,
                        fieldForNamePlayerForFox,
                        fieldForNamePlayerForGeese,
                        labelErrors,
                        stage
                );
            }
        });
        Scene scene = new Scene(root, 250, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void createGameInterface(
            TextField fieldForCountOfGeese,
            TextField fieldForCellForFoxFeature,
            TextField fieldForNamePlayerForFox,
            TextField fieldForNamePlayerForGeese,
            Label labelErrors,
            Stage stage
    ) {
        GridPane gridPane = new GridPane();
        try {
            createField(
                    gridPane,
                    Integer.parseInt(fieldForCountOfGeese.getText()),
                    fieldForCellForFoxFeature.getText(),
                    fieldForNamePlayerForFox.getText(),
                    fieldForNamePlayerForGeese.getText()
            );
        } catch (Exception e) {
            labelErrors.setText(e.getMessage());
            return;
        }
        labelPlayers.setText("" + game.getPlayer());

        Button buttonSave = new Button("Save");
        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    WriterToFile.write(game, new File("save_file.json"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button buttonLoad = new Button("Load");
        buttonLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    game = ReaderFromFile.read(new File("save_file.json"));
                    labelPlayers.setText("" + game.getPlayer());
                    updateField(gridPane);
                } catch (IOException e) {
                    labelErrors.setText("The last save is missing");
                }
            }
        });

        Button buttonScript = new Button("PlayScript");
        buttonScript.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (!moves.isEmpty()) {
                        TimeUnit.SECONDS.sleep(1);
                        assert moves.peek() != null;
                        game.move(moves.peek()[0], moves.peek()[1]);
                        updateField(gridPane);
                        moves.poll();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                actionPerformed(actionEvent);
            }

            private void actionPerformed(ActionEvent actionEvent) {
            }
        });
        FlowPane gamePane = new FlowPane(
                Orientation.VERTICAL,
                10,
                10,
                labelPlayers,
                gridPane,
                buttonSave,
                buttonLoad,
                buttonScript,
                labelErrors
        );
        Scene scene = new Scene(gamePane, 610, 740);

        stage.setScene(scene);
    }

    public void createField(
            GridPane gridPane,
            int countOfGeese,
            String cellForFoxFeature,
            String namePlayerForFox,
            String namePlayerForGeese
    ) throws Exception {
        game = new Game(countOfGeese, cellForFoxFeature, namePlayerForFox, namePlayerForGeese);
        game.startGame();
        updateField(gridPane);
    }


    private void updateField(GridPane gridPane) {
        if(game.isVictory()) {
            labelPlayers.setText(game.getPlayer() + " - Winner!!!");
            for (int i = 1; i <= 7; i++) {
                for (char j = 'A'; j <= 'G'; j++) {
                    if(!((i == 1 || i == 2 || i == 6 || i == 7) && (j == 'A' || j == 'B' || j == 'F' || j == 'G'))) {
                        ButtonCell buttonCell = new ButtonCell("" + j + i, new Button());
                        buttonCell.getButton().setStyle(STYLE_FOR_NON_CELL);

                        if(game.getField().isOnCell("" + j + i)) {
                            if (game.getField().getCell("" + j + i).getContentCell().getNameOfFeature().equals("Goose")) {
                                buttonCell.getButton().setStyle(STYLE_FOR_GOOSE_CELL);
                            }
                            if (game.getField().getCell("" + j + i).getContentCell().getNameOfFeature().equals("Fox")) {
                                buttonCell.getButton().setStyle(STYLE_FOR_FOX_CELL);
                            }
                        }
                        gridPane.add(buttonCell.getButton(), i, j);
                    }

                }
            }

            return;
        }
        for (int i = 1; i <= 7; i++) {
            for (char j = 'A'; j <= 'G'; j++) {
                if(!((i == 1 || i == 2 || i == 6 || i == 7) && (j == 'A' || j == 'B' || j == 'F' || j == 'G'))) {
                    ButtonCell buttonCell = new ButtonCell("" + j + i, new Button());
                    buttonCell.getButton().setStyle(STYLE_FOR_NON_CELL);

                    if(game.getField().isOnCell("" + j + i)) {
                        if (game.getField().getCell("" + j + i).getContentCell().getNameOfFeature().equals("Goose")) {
                            buttonCell.getButton().setStyle(STYLE_FOR_GOOSE_CELL);
                        }
                        if (game.getField().getCell("" + j + i).getContentCell().getNameOfFeature().equals("Fox")) {
                            buttonCell.getButton().setStyle(STYLE_FOR_FOX_CELL);
                        }
                    }
                    buttonCell.getButton().setOnAction(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            move(gridPane, buttonCell);
                        }

                    });
                    gridPane.add(buttonCell.getButton(), i, j);
                }

            }
        }

    }


    private void move(GridPane gridPane, ButtonCell buttonCell) {
        countOfClicks++;
        if (countOfClicks == 2) {
            if(prevButton != null) {
                if(game.getField().isOnCell(prevButton.getNameCell())) {
                    game.move(prevButton.getNameCell(), buttonCell.getNameCell());
                    labelPlayers.setText("" + game.getPlayer());
                    updateField(gridPane);
                    countOfClicks = 0;
                }
            }
        }
        if(game.getField().getCell(buttonCell.getNameCell()).getContentCell() == null){
            buttonCell.getButton().setStyle(STYLE_FOR_NON_CELL);
        } else {
            if (game.getField().getCell(buttonCell.getNameCell()).getContentCell().getNameOfFeature().equals("Fox") ||
                    game.getField().getCell(buttonCell.getNameCell()).getContentCell().getNameOfFeature().equals("Goose")) {
                buttonCell.getButton().setStyle(buttonCell.getButton().getStyle() + STYLE_FOR_CELL_SELECTED);
            }
        }
        prevButton = buttonCell;
    }

    private Queue<String[]> fillMoves() {
        Queue<String[]> moves = new LinkedList<>();
        moves.add(new String[]{"C4", "C3"});
        moves.add(new String[]{"D1", "C2"});
        moves.add(new String[]{"C3", "C2"});
        moves.add(new String[]{"E1", "D1"});
        moves.add(new String[]{"C1", "D1"});
        moves.add(new String[]{"E2", "D2"});
        moves.add(new String[]{"E1", "D2"});
        moves.add(new String[]{"E3", "D3"});
        moves.add(new String[]{"C3", "D3"});
        moves.add(new String[]{"E4", "D4"});
        moves.add(new String[]{"E3", "D4"});
        moves.add(new String[]{"E5", "D5"});
        moves.add(new String[]{"C5", "D5"});
        moves.add(new String[]{"D7", "D6"});
        moves.add(new String[]{"E5", "D6"});
        moves.add(new String[]{"E7", "D7"});
        moves.add(new String[]{"C7", "D7"});
        moves.add(new String[]{"F4", "E4"});
        moves.add(new String[]{"E7", "E6"});
        return moves;
    }
}
