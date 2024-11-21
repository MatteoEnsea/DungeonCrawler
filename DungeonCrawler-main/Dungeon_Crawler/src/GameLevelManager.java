/**
 * Interface qui permet de gérer les changements de niveau dans le main.
 */
public interface GameLevelManager {
    void loadLevel(int levelIndex) throws Exception;
    void resetLevel() throws Exception;
}
