package cs.vsu.goncharenko.entity.field;

import cs.vsu.goncharenko.entity.feature.AbstractFeature;

import java.io.Serializable;

public class FieldCellContext implements Serializable {
    public String getNameCell() {
        return nameCell;
    }

    public void setNameCell(String nameCell) {
        this.nameCell = nameCell;
    }

    public String getContentCell() {
        return contentCell;
    }

    public void setContentCell(String contentCell) {
        this.contentCell = contentCell;
    }

    private String nameCell;
    private String contentCell;
}
