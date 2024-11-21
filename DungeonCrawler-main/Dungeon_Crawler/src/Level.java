import java.util.ArrayList;

public class Level {
    private ArrayList<String> levelPaths;
    static int currentLevelIndex;
    private Playground currentPlayground;

    public Level() {
        levelPaths = new ArrayList<>();
        currentLevelIndex = 0;

        levelPaths.add("./data/level1.txt");
        levelPaths.add("./data/level2.txt");
        levelPaths.add("./data/level3.txt");
    }

    public static void setCurrentLevelIndex(int LevelIndex) {
        currentLevelIndex = LevelIndex;
    }

    public Playground loadCurrentLevel() throws Exception {
        if (currentLevelIndex < 0 || currentLevelIndex >= levelPaths.size()) {
            throw new Exception("Niveau inexistant !");
        }
        currentPlayground = new Playground(levelPaths.get(currentLevelIndex));
        return currentPlayground;
    }

    public Playground loadNextLevel() throws Exception {
        currentLevelIndex++;
        return loadCurrentLevel();
    }

    public boolean hasNextLevel() {
        return currentLevelIndex + 1 < levelPaths.size();
    }

    public Playground getCurrentPlayground() {
        return currentPlayground;
    }

    public void reset() {
        currentLevelIndex = 0;

    }
}

