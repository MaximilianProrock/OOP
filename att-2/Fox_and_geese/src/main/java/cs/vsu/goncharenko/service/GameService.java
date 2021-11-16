package cs.vsu.goncharenko.service;

import cs.vsu.goncharenko.entity.feature.*;
import cs.vsu.goncharenko.entity.field.Field;
import cs.vsu.goncharenko.entity.field.FieldCell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Iterator;

public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private GameService () {}

    public static void createField(Field field, String cellForFoxFeature, int countOfGoose) throws Exception {
        if(countOfGoose != 13 && countOfGoose != 15)
            throw new Exception("Incorrectly entered the number of geese");
        Iterator<FieldCell> iterator = field.getGraph().iterator();
        while (iterator.hasNext()) {
            FieldCell fieldCell = iterator.next();
            if(fieldCell.getNameCell().equals("D7")) {
                break;
            }
        }
        while (iterator.hasNext()) {
            FieldCell fieldCell = iterator.next();
            fieldCell.setContentCell(new GooseFeature(fieldCell.getNameCell()));
            logger.info("The Goose feature is installed on the cell: " + fieldCell.getNameCell());
        }
        if (countOfGoose == 15) {
            field.setCell(new FieldCell("D1", new GooseFeature( "D1")));
            logger.info("The Goose feature is installed on the cell: " + "D1");
            field.setCell(new FieldCell("D7", new GooseFeature("D7")));
            logger.info("The Goose feature is installed on the cell: " + "D7");
        }
        setFoxFeature(cellForFoxFeature, field);
        logger.info("The Fox feature is installed on the cell: " + cellForFoxFeature);
    }

    public static void setCellToField(String nameCell, String nameOfFeature, Field field) {
        if(field.isExist(nameCell) && !field.isOnCell(nameCell)) {
            if(nameOfFeature.equals("Goose")) {
                field.setCell(new FieldCell(nameCell, new GooseFeature(nameCell)));
            }
            if(nameOfFeature.equals("Fox")) {
                field.setCell(new FieldCell(nameCell, new FoxFeature(nameCell)));
            }
        }
    }

    private static void setFoxFeature(String cell, Field field) throws Exception {
        if(!field.isExist(cell))
            throw new Exception("This cell is not exist!");
        if(field.isOnCell(cell))
            throw new Exception("This cell is already occupied");

        field.setCell(new FieldCell(cell, new FoxFeature(cell)));
    }

    public static void createEdgeBetweenCells(String cell, Field field) {
        char[] coordinates = cell.toCharArray();
        String newCell = "";
        if(field.isOnCell(cell)) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    newCell = "" + (char)(coordinates[0] + (i - 1)) + (char)(coordinates[1] + (j - 1));
                    if(cell.equals(newCell) || !field.isExist(newCell)) {
                        continue;
                    }

                    if(cell.equals("E2") && newCell.equals("F3") || cell.equals("F3") && newCell.equals("E2")){
                        continue;
                    }
                    if(cell.equals("C2") && newCell.equals("B3") || cell.equals("B3") && newCell.equals("C2")){
                        continue;
                    }
                    if(cell.equals("E6") && newCell.equals("F5") || cell.equals("F5") && newCell.equals("E6")){
                        continue;
                    }
                    if(cell.equals("C6") && newCell.equals("B5") || cell.equals("B5") && newCell.equals("C6")){
                        continue;
                    }

                    if(field.isOnCell(newCell)) {
                        continue;
                    }
                    field.getGraph().createEdge(field.getCell(cell), field.getCell(newCell));

                }
            }
        }
    }

    public static void removeAllEdge(String cell, Field field) {
        char[] coordinates = cell.toCharArray();
        String newCell = "";

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newCell = "" + (char)(coordinates[0] + (i - 1)) + (char)(coordinates[1] + (j - 1));
                if(cell.equals(newCell) || !field.isExist(newCell)) {
                    continue;
                }
                if(field.getGraph().isEdge(field.getCell(cell), field.getCell(newCell))){
                    field.getGraph().removeEdge(field.getCell(cell), field.getCell(newCell));
                }
            }
        }

    }

}
