package cs.vsu.goncharenko.entity.feature;

public abstract class AbstractFeature {
    private String nameOfFeature;
    private String cell;

    protected AbstractFeature(String nameOfFeature, String cell) {
        this.nameOfFeature = nameOfFeature;
        this.cell = cell;
    }

    public String getNameOfFeature() {
        return nameOfFeature;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }
    public void setNameOfFeature(String nameOfFeature) {
        this.nameOfFeature = nameOfFeature;
    }

    public boolean equals(AbstractFeature feature) {
        return this.nameOfFeature == feature.getNameOfFeature() && this.getCell() == feature.getCell();
    }
}
