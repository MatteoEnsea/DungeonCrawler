import java.util.ArrayList;

public class LevelManager {
    private ArrayList<String> levelPaths;
    private int currentLevelIndex;
    private Playground currentPlayground;

    public LevelManager() {
        levelPaths = new ArrayList<>();
        currentLevelIndex = 0;

        levelPaths.add("./data/level1.txt");
        levelPaths.add("./data/level2.txt");
        levelPaths.add("./data/level3.txt");
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
}

