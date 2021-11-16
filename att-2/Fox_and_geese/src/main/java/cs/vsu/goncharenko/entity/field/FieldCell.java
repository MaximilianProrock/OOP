package cs.vsu.goncharenko.entity.field;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
        boolean resName = this.nameCell.equals(fieldCell.nameCell);
        boolean resContent = false;
        if(this.contentCell == null || fieldCell.contentCell == null){
            if(this.contentCell == null && fieldCell.contentCell == null) {
                resContent = true;
            }
        }else {
            resContent = this.contentCell.equals(fieldCell.contentCell);
        }
        return resName && resContent;
    }

    public void setContentCell(AbstractFeature contentCell) {
        this.contentCell = contentCell;
    }

    public FieldCellContext context(){
        FieldCellContext fieldCellContext = new FieldCellContext();
        fieldCellContext.setNameCell(nameCell);
        if (contentCell == null) {
            fieldCellContext.setContentCell("" + null);
        }else {
            fieldCellContext.setContentCell("" + contentCell.getNameOfFeature());
        }
        return fieldCellContext;
    }

}
