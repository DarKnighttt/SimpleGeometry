package com.darknight.simplegeometry;

import java.util.List;

public interface IDatabaseHandler {

    void addLevel(Level lvl);
    Level getLevel(int id);
    List<Level> getAllLevels();
    void updateLevel(Level lvl);
    void clearProgress();
}
