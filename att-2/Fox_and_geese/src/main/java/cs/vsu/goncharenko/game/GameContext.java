package cs.vsu.goncharenko.game;

import cs.vsu.goncharenko.entity.field.FieldContext;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class GameContext implements Serializable {

    private static final long serialVersionUID = 1L;

    private FieldContext field;
    private Integer countOfGeese;
    private String cellForFoxFeature;
    private Queue<PlayerContext> players = new LinkedList<>();
    private boolean victory = false;

    public FieldContext getField() {
        return field;
    }

    public void setField(FieldContext field) {
        this.field = field;
    }

    public Integer getCountOfGeese() {
        return countOfGeese;
    }

    public void setCountOfGeese(Integer countOfGeese) {
        this.countOfGeese = countOfGeese;
    }

    public String getCellForFoxFeature() {
        return cellForFoxFeature;
    }

    public void setCellForFoxFeature(String cellForFoxFeature) {
        this.cellForFoxFeature = cellForFoxFeature;
    }

    public Queue<PlayerContext> getPlayers() {
        return players;
    }

    public void setPlayers(Queue<PlayerContext> players) {
        this.players = players;
    }

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }
}
