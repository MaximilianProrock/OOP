package cs.vsu.goncharenko.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs.vsu.goncharenko.game.Game;
import cs.vsu.goncharenko.game.GameContext;
import cs.vsu.goncharenko.game.Player;
import cs.vsu.goncharenko.service.GameService;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ReaderFromFile {

    private ReaderFromFile() {
    }

    public static Game read(File file) throws IOException {
        Game game = new Game();
        FileReader reader = new FileReader(file);
        StringBuilder builder = new StringBuilder();
        int c;
        int countOfGeese = 0;
        while((c = reader.read()) != -1){
            builder.append((char) c);
        }
        ObjectMapper objectMapper = new ObjectMapper();

        game.deserialization(objectMapper.readValue(builder.toString(), GameContext.class));
        return game;
    }
}
