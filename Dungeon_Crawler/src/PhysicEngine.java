import java.util.ArrayList;

public class PhysicEngine implements Engine {
    ArrayList<DynamicSprite> movingSpriteList = new ArrayList<>();
    ArrayList<SolidSprite> environnement;
    private Playground currentPlayground;
    private GameEngine gameEngine;
    private RenderEngine renderEngine;


    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void setRenderEngine(RenderEngine renderEngine) {
        this.renderEngine = renderEngine;
    }

    public void addToMovingSpriteList(DynamicSprite s) {
        movingSpriteList.add(s);
    }

    public void setEnvironment(ArrayList<SolidSprite> environnement) {
        this.environnement = environnement;
    }

    public void setCurrentPlayground(Playground playground) {
        this.currentPlayground = playground;
    }

    /**
     * Charge le prochain niveau : réinitialise tout l'environnement actuel et le met à jour
     * @param level : niveau actuel
     */
    public void loadNextLevel(Level level) {
        try {
            if (level.hasNextLevel()) {
                Playground nextLevel = level.loadNextLevel();

                // Mise à jour de l'environnement et du niveau actuel
                currentPlayground = nextLevel;
                environnement = nextLevel.getSolidSpriteList();

                // Réinitialisation de la liste des objets mobiles dans le moteur physique
                movingSpriteList.clear();
                gameEngine.hero.setPosition(200, 300);
                addToMovingSpriteList(gameEngine.hero);

                // Mise à jour du RenderEngine
                renderEngine.clearRenderList();
                renderEngine.addToRenderList(nextLevel.getSpriteList());
                renderEngine.addToRenderList(gameEngine.hero); // Ajoute à la liste de rendu

                System.out.println("Niveau suivant chargé !");
            } else {
                System.out.println("Vous avez terminé tous les niveaux !");
                // Fin de jeu, comme un écran de victoire ou un retour au menu, non fait dans cette version
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void clearEnvironment() {
        environnement.clear();
    }


    @Override
    public void update(GameState gameState) {
        switch (gameState) {
            case Running:
                for (DynamicSprite s : movingSpriteList) {
                    s.moveIfPossible(environnement);
                    if (currentPlayground.isPlayerInExit(gameEngine.hero)) {
                        loadNextLevel(Main.levelManager);
                    }
                }
                break;
            default:
                break;
        }


    }

}
