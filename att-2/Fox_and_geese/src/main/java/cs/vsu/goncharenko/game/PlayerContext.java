package cs.vsu.goncharenko.game;

public class PlayerContext {

    public String getNameOfPlayer() {
        return nameOfPlayer;
    }

    public void setNameOfPlayer(String nameOfPlayer) {
        this.nameOfPlayer = nameOfPlayer;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    private String nameOfPlayer;
    private String feature;
}
