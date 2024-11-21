import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;
    private GameState gameState;
    private Level level;
    private DynamicSprite hero;

    public void setHero(DynamicSprite hero) {
        this.hero = hero;
    }

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
        Font titleFont = new Font("Arial", Font.BOLD, 40);
        g.setFont(titleFont);
        g.drawString("Bienvenue !", 700, 300);
        g.drawString("Appuyez sur ESCAPE pour commencer !", 500, 540);
    }

    public void displayGameOverScreen(Graphics g) {
        Font titleFont = new Font("Arial", Font.BOLD, 40);
        g.setFont(titleFont);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1920, 1080);
        g.setColor(Color.RED);
        g.drawString("GAME OVER", 700, 300);
        g.setColor(Color.WHITE);
        g.drawString("Appuyez sur ENTRER pour revenir au menu", 500, 540);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        repaint();
    }

    public void drawHealthBar(Graphics g, int currentLife, int maxLife) {
        int barWidth = 200;
        int barHeight = 20;
        int x = 10;
        int y = 10;

        double lifePercentage = (double) currentLife / maxLife; //Calcul en pourcentage les PV

        //Affiche la barre de vie avec des couleurs "Habituelles"
        g.setColor(Color.GRAY);
        g.fillRect(x, y, barWidth, barHeight);

        g.setColor(Color.GREEN);
        g.fillRect(x, y, (int) (barWidth * lifePercentage), barHeight);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);

        g.setColor(Color.WHITE);
        g.drawString(currentLife + " / " + maxLife, x + barWidth / 2 - 20, y + 15);
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
            drawHealthBar(g, hero.getLife(), 20);
        } else if (gameState == GameState.Paused) {
            Font titleFont = new Font("Arial", Font.BOLD, 40);
            g.setFont(titleFont);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.drawString("Jeu en pause", 700, 300);
            g.drawString("Appuyez sur ESC pour continuer",500 , 540);
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
