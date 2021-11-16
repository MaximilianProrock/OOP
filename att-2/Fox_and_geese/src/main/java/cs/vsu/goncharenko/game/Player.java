package cs.vsu.goncharenko.game;


public class Player {
    private final String nameOfPlayer;
    private final String feature;
    
    public Player(String nameOfPlayer, String feature) {
        this.nameOfPlayer = nameOfPlayer;
        this.feature = feature;
    }

    public PlayerContext context() {
        PlayerContext playerContext = new PlayerContext();
        playerContext.setNameOfPlayer(nameOfPlayer);
        playerContext.setFeature(feature);
        return playerContext;

    }

    public String getNameOfPlayer() {
        return nameOfPlayer;
    }

    public String getFeature() {
        return feature;
    }
}
