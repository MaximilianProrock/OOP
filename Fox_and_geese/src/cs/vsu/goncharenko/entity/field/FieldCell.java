package cs.vsu.goncharenko.entity.field;

import cs.vsu.goncharenko.entity.feature.AbstractFeature;

public class FieldCell {
    private final String nameCell;
    private AbstractFeature contentCell;

    public FieldCell(String nameCell, AbstractFeature contentCell) {
        this.nameCell = nameCell;
        this.contentCell = contentCell;
    }

    public String getNameCell() {
        return nameCell;
    }

    public AbstractFeature getContentCell() {
        return contentCell;
    }

    public boolean equals(Object obj) {
        FieldCell fieldCell = (FieldCell) obj;
        return this.nameCell.equals(fieldCell.nameCell);
    }

    public void setContentCell(AbstractFeature contentCell) {
        this.contentCell = contentCell;
    }
}
