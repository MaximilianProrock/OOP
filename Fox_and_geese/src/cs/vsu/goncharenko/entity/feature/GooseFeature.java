package cs.vsu.goncharenko.entity.feature;
public class GooseFeature extends AbstractFeature {

    private String name;

    public GooseFeature(String nameOfGoose, String cell) {
        super("Goose", cell);
        this.name = nameOfGoose;
    }
}
