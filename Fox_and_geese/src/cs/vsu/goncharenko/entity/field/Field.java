package cs.vsu.goncharenko.entity.field;

import cs.vsu.goncharenko.graph.Graph;

import java.util.Iterator;

public class Field {
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

}
