package cs.vsu.goncharenko.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs.vsu.goncharenko.entity.field.FieldCell;
import cs.vsu.goncharenko.game.Game;
import cs.vsu.goncharenko.game.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;

public class WriterToFile {

    private WriterToFile() {
    }

    public static void write(Game game, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        ObjectMapper objectMapper = new ObjectMapper();
        writer.write(objectMapper.writeValueAsString(game.context()));
        writer.flush();
    }
}
