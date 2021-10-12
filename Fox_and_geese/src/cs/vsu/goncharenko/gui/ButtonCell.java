package cs.vsu.goncharenko.gui;

import javafx.scene.control.Button;

public class ButtonCell {
    private final String nameCell;
    private Button button;

    public ButtonCell(String nameCell, Button button) {
        this.nameCell = nameCell;
        this.button = button;
    }

    public String getNameCell() {
        return nameCell;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
