package cs.vsu.goncharenko.entity.field;
import cs.vsu.goncharenko.entity.feature.AbstractFeature;
import cs.vsu.goncharenko.entity.feature.FoxFeature;
import cs.vsu.goncharenko.entity.feature.GooseFeature;
import cs.vsu.goncharenko.graph.Graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Field implements Serializable {
    private Graph<FieldCell> graph;

    public Graph<FieldCell> getGraph() {
        return graph;
    }

    public Field() {
        graph = new Graph();
        createField();
    }
   private void createField() {
       for (char i = 'A'; i <= 'G'; i++) {
           for (int j = 1; j <= 7; j++) {
               if(!((j == 1 || j == 2 || j == 6 || j == 7) && (i == 'A' || i == 'B' || i == 'F' || i == 'G'))) {
                   graph.addVertex(new FieldCell("" + i + j, null));
               }
           }
       }
   }

   public boolean isOnCell(final String name) {
       Iterator<FieldCell> iterator = graph.iterator();
       while (iterator.hasNext()){
           FieldCell fieldCell = iterator.next();
           if(name.equals(fieldCell.getNameCell())) {
               if(fieldCell.getContentCell() != null) {
                   return true;
               } else {
                   return false;
               }
           }
       }
       return false;
   }

   public void setCell(final FieldCell fieldCell) {
       Iterator<FieldCell> iterator = graph.iterator();
       while (iterator.hasNext()){
           FieldCell oldFieldCell = iterator.next();
           if(fieldCell.getNameCell().equals(oldFieldCell.getNameCell())) {
               try {
                   graph.replaceVertices(oldFieldCell, fieldCell);
               } catch (Exception e) {
                   e.printStackTrace();
               }
               break;
           }
       }

   }

   public FieldCell getCell(String name) {
       Iterator<FieldCell> iterator = graph.iterator();
       while (iterator.hasNext()){
           FieldCell fieldCell = iterator.next();
           if(name.equals(fieldCell.getNameCell())) {
               return fieldCell;
           }
       }
       return null;
   }

   public boolean isExist(String name) {
       Iterator<FieldCell> iterator = graph.iterator();
       while (iterator.hasNext()){
           FieldCell fieldCell = iterator.next();
           if(name.equals(fieldCell.getNameCell())) {
               return true;
           }
       }
       return false;
   }

    public boolean equals(Field field) {
        Iterator<FieldCell> iteratorThisField = this.graph.iterator();
        Iterator<FieldCell> iteratorComperedField = field.graph.iterator();

        while (iteratorThisField.hasNext() && iteratorComperedField.hasNext()) {
            FieldCell fieldCellThis = iteratorThisField.next();
            FieldCell fieldCellCompered = iteratorComperedField.next();
            if (!fieldCellThis.equals(fieldCellCompered)) {
                return false;
            }
        }
        if(iteratorThisField.hasNext() || iteratorComperedField.hasNext()) {
            return false;
        }
        return true;
    }

    public FieldContext context() {
        FieldContext fieldContext = new FieldContext();
        List<FieldCellContext> list = new ArrayList<>();
        Iterator<FieldCell> cells = this.graph.iterator();
        while (cells.hasNext()){
            list.add(cells.next().context());
        }
        fieldContext.setCells(list);
        return fieldContext;
    }

    public void deserialization(FieldContext fieldContext) {
        graph = new Graph<>();
        for (FieldCellContext fieldCellContext: fieldContext.getCells()) {
            AbstractFeature abstractFeature = null;
            if(fieldCellContext.getContentCell().equals("Fox")){
                abstractFeature = new FoxFeature(fieldCellContext.getNameCell());
            }
            if(fieldCellContext.getContentCell().equals("Goose")){
                abstractFeature = new GooseFeature(fieldCellContext.getNameCell());
            }
            FieldCell fieldCell = new FieldCell(fieldCellContext.getNameCell(), abstractFeature);
            graph.addVertex(fieldCell);
        }
    }

}
