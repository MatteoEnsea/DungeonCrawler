import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;
    private GameState gameState;
    private LevelManager level;

    public RenderEngine() {
        this.renderList = new ArrayList<>();
    }

    public void setRenderList(ArrayList<Displayable> renderList) {
        this.renderList = renderList;
    }

    public void addToRenderList(Displayable displayable) {
        if (!renderList.contains(displayable)) {
            renderList.add(displayable);
        }
    }

    public void addToRenderList(ArrayList<Displayable> displayable) {
        if (!renderList.contains(displayable)) {
            renderList.addAll(displayable);
        }
    }

    public void displayTitleScreen(Graphics g) {
        g.drawString("Bienvenue !", 100, 100);
        g.drawString("Appuyez sur ESCAPE pour commencer !", 200, 200);
    }

    public void displayGameOverScreen(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1920, 1080);
        g.setColor(Color.RED);
        g.drawString("GAME OVER", 200, 200);
        g.setColor(Color.WHITE);
        g.drawString("Appuyez sur ENTRER pour revenir au menu", 150, 230);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        repaint();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (gameState == GameState.TitleScreen) {
            displayTitleScreen(g);
        } else if (gameState == GameState.GameOver) {
            displayGameOverScreen(g);
        } else if (gameState == GameState.Running) {
            for (Displayable displayable : renderList) {
                displayable.draw(g);
            }
        } else if (gameState == GameState.Paused) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.drawString("Jeu en pause", 100, 100);
            g.drawString("Appuyez sur ESC pour continuer", 100, 130);
        }
    }

    public void clearRenderList() {
        renderList.clear();
    }

    @Override
    public void update(GameState gameState) {
        this.gameState = gameState;
        repaint();
    }

}
