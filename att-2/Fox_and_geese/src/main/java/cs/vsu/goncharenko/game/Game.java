package cs.vsu.goncharenko.game;
import cs.vsu.goncharenko.entity.field.Field;
import cs.vsu.goncharenko.entity.field.FieldCell;
import cs.vsu.goncharenko.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;


public class Game {

    private Field field;
    private Integer countOfGeese;
    private String cellForFoxFeature;

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);


    private Queue<Player> players = new LinkedList<>();

    public boolean isVictory() {
        return victory;
    }

    private boolean victory = false;

    public Game() {
        this.cellForFoxFeature = "";
        this.field = new Field();
    }

    public Game(int countOfGeese, String cellForFoxFeature, String namePlayerForFox, String namePlayerForGeese) throws Exception {
        this.field = new Field();
        this.countOfGeese = countOfGeese;
        this.cellForFoxFeature = cellForFoxFeature;

        if (namePlayerForFox.equals("") || namePlayerForGeese.equals(""))
            throw new Exception("The player's name is not entered");
        players.add(new Player(namePlayerForFox, "Fox"));
        players.add(new Player(namePlayerForGeese, "Goose"));

    }

    public void startGame() throws Exception {
        GameService.createField(field, cellForFoxFeature, countOfGeese);
    }

    public void move(String nameCellWithFeature, String nameOfSelectedCell) {
        assert players.peek() != null;
        if (!victory) {
            String str1 = players.peek().getFeature();
            String str2 = field.getCell(nameCellWithFeature).getContentCell().getNameOfFeature();
            if (str1.equals(str2)) {
                moveFeature(nameCellWithFeature, nameOfSelectedCell, field);
                if (players.peek().getFeature().equals("Goose")) {
                    victory = checkingVictoryOfGeese(cellForFoxFeature);
                }
                if(countOfGeese < 6) {
                    victory = true;
                }
                if (!field.isOnCell(nameCellWithFeature) && !victory) {
                    logger.info("The " + players.peek().getFeature()
                            + " descended from cell " + nameCellWithFeature
                            + " to cell " + nameOfSelectedCell
                    );
                    Player player = players.poll();
                    players.add(player);
                }
            }
        }
    }

    private boolean checkingVictoryOfGeese(String cellForFoxFeature) {
        Field fieldForAnalysis = createFieldForAnalysis();
        int countOfGeeseForAnalysis = countOfGeese;
        char[] coordinates = cellForFoxFeature.toCharArray();
        String newCell = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newCell = "" + (char)(coordinates[0] + (i - 1)) + (char)(coordinates[1] + (j - 1));
                if(!fieldForAnalysis.isExist(newCell) || cellForFoxFeature.equals(newCell)) {
                    continue;
                }
                moveFeature(cellForFoxFeature, newCell, fieldForAnalysis);
                if(!field.equals(fieldForAnalysis)) {
                    return false;
                }
                fieldForAnalysis = createFieldForAnalysis();
                countOfGeese = countOfGeeseForAnalysis;
            }
        }
        return true;
    }

    private Field createFieldForAnalysis() {
        Field fieldRes = new Field();
        for (char i = 'A'; i <= 'G'; i++) {
            for (int j = 1; j <= 7; j++) {
                if(!((j == 1 || j == 2 || j == 6 || j == 7) && (i == 'A' || i == 'B' || i == 'F' || i == 'G'))) {
                    fieldRes.setCell(new FieldCell("" + i + j, field.getCell("" + i + j).getContentCell()));
                }
            }
        }

        return fieldRes;
    }


    private void moveFeature(String nameCellWithFeature, String nameOfSelectedCell, Field field){
        if(field.isExist(nameCellWithFeature) && field.isExist(nameOfSelectedCell)) {
            GameService.createEdgeBetweenCells(nameCellWithFeature, field);
            if(field.isOnCell(nameCellWithFeature)) {
                if(field.getCell(nameCellWithFeature).getContentCell().getNameOfFeature().equals("Fox") && field.isOnCell(nameOfSelectedCell)) {
                    eatingGoose(nameCellWithFeature, nameOfSelectedCell, field);
                } else if(!field.isOnCell(nameOfSelectedCell)) {
                    if (field.getGraph().isEdge(field.getCell(nameCellWithFeature), field.getCell(nameOfSelectedCell))) {
                        cellExchange(nameCellWithFeature, nameOfSelectedCell, field);
                        if(!field.isOnCell(nameCellWithFeature) && players.peek().getFeature().equals("Fox")) {
                            cellForFoxFeature = nameOfSelectedCell;
                        }
                    }
                }
            }
            GameService.removeAllEdge(nameCellWithFeature, field);
        }

    }

    private void eatingGoose(String foxCell, String gooseCell, Field field){
        if(!field.isExist(foxCell) || !field.isExist(gooseCell)){
            return;
        }
        if(!field.isOnCell(foxCell) || !field.isOnCell(gooseCell)){
            return;
        }
        if(field.getCell(foxCell).getContentCell().getNameOfFeature().equals("Fox")
                && field.getCell(gooseCell).getContentCell().getNameOfFeature().equals("Goose")) {
            char[] coordinatesFox = foxCell.toCharArray();
            char[] coordinatesGoose = gooseCell.toCharArray();
            int y = coordinatesGoose[0] - coordinatesFox[0];
            int x = coordinatesGoose[1] - coordinatesFox[1];
            if (x < 2 && y < 2) {
                String newCell = "" + (char)(coordinatesFox[0] + y * 2) + (char)(coordinatesFox[1] + x * 2);
                if(!field.isExist(newCell)){
                    return;
                }
                if(field.isOnCell(newCell)){
                    return;
                }
                if(foxCell.equals("A4") && newCell.equals("C2") || foxCell.equals("C2") && newCell.equals("A4")){
                    return;
                }
                if(foxCell.equals("A4") && newCell.equals("C6") || foxCell.equals("C6") && newCell.equals("A4")){
                    return;
                }
                if(foxCell.equals("G4") && newCell.equals("E2") || foxCell.equals("E2") && newCell.equals("G4")){
                    return;
                }
                if(foxCell.equals("G4") && newCell.equals("E6") || foxCell.equals("E6") && newCell.equals("G4")){
                    return;
                }

                if(foxCell.equals("D1") && newCell.equals("B3") || foxCell.equals("B3") && newCell.equals("D1")){
                    return;
                }
                if(foxCell.equals("D1") && newCell.equals("F3") || foxCell.equals("F3") && newCell.equals("D1")){
                    return;
                }
                if(foxCell.equals("D7") && newCell.equals("B5") || foxCell.equals("B5") && newCell.equals("D7")){
                    return;
                }
                if(foxCell.equals("D7") && newCell.equals("F5") || foxCell.equals("F5") && newCell.equals("D7")){
                    return;
                }

                countOfGeese--;
                field.getCell(gooseCell).setContentCell(null);
                GameService.removeAllEdge(gooseCell, field);
                cellExchange(foxCell, newCell, field);
                cellForFoxFeature = newCell;
                foxCell = newCell;
                newCell = "" + (char)(coordinatesFox[0] + y * 3) + (char)(coordinatesFox[1] + x * 3);
                logger.info("The Goose in the " +  gooseCell +  " cage was eaten");
                eatingGoose(foxCell, newCell, field);

            }
        }
    }

    private static void cellExchange(String oldCell, String newCell, Field field) {
        FieldCell buffer = field.getCell(oldCell);
        buffer.getContentCell().setCell(newCell);
        field.getCell(newCell).setContentCell(buffer.getContentCell());
        field.getCell(oldCell).setContentCell(null);
        GameService.removeAllEdge(oldCell, field);
        GameService.createEdgeBetweenCells(field.getCell(newCell).getNameCell(), field);
    }

    public GameContext context() {
       GameContext gameContext = new GameContext();
       gameContext.setField(field.context());
       gameContext.setCountOfGeese(countOfGeese);
       gameContext.setCellForFoxFeature(cellForFoxFeature);
       gameContext.setPlayers(contextPlayers());
       gameContext.setVictory(victory);
       return gameContext;
    }

    public void deserialization(GameContext gameContext) {
        countOfGeese = gameContext.getCountOfGeese();
        cellForFoxFeature = gameContext.getCellForFoxFeature();
        victory = gameContext.isVictory();
        field.deserialization(gameContext.getField());
        deserializationPlayers(gameContext.getPlayers());
    }

    private Queue<PlayerContext> contextPlayers() {
        Queue<PlayerContext> queue = new LinkedList<>();
        Player player = players.poll();
        players.add(player);
        PlayerContext playerContext= player.context();
        queue.add(playerContext);

        player = players.poll();
        players.add(player);
        playerContext= player.context();
        queue.add(playerContext);

        return queue;

    }

    private void deserializationPlayers(Queue<PlayerContext> players) {
        this.players.poll();
        this.players.poll();
        PlayerContext playerContext = players.poll();
        assert playerContext != null;
        Player player = new Player(playerContext.getNameOfPlayer(), playerContext.getFeature());
        this.players.add(player);

        playerContext = players.poll();
        assert playerContext != null;
        player = new Player(playerContext.getNameOfPlayer(), playerContext.getFeature());
        this.players.add(player);
    }



    public String getPlayer() {
        assert players.peek() != null;
        assert players.peek() != null;
        return players.peek().getNameOfPlayer() + " - " + players.peek().getFeature();
    }

    public Field getField() {
        return field;
    }

    public Queue<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Queue<Player> players) {
        this.players = players;
    }

    public void setCountOfGeese(int countOfGeese) {
        this.countOfGeese = countOfGeese;
    }

    public void setCellForFoxFeature(String cellForFoxFeature) {
        this.cellForFoxFeature = cellForFoxFeature;
    }
}
