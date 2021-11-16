package cs.vsu.goncharenko.entity.field;

import java.util.List;

public class FieldContext {
    public List<FieldCellContext> getCells() {
        return cells;
    }

    public void setCells(List<FieldCellContext> cells) {
        this.cells = cells;
    }

    private List<FieldCellContext> cells;
}
