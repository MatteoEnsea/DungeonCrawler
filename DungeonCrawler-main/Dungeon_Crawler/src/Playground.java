import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Playground {
    private ArrayList<Sprite> environment = new ArrayList<>();

    public Playground(String pathName) {
        try {
            final Image imageTree = ImageIO.read(new File("./img/tree.png"));
            final Image imageGrass = ImageIO.read(new File("./img/grass.png"));
            final Image imageRock = ImageIO.read(new File("./img/rock.png"));
            final Image imageTrap = ImageIO.read(new File("./img/trap.png"));
            final int imageTreeWidth = imageTree.getWidth(null);
            final int imageTreeHeight = imageTree.getHeight(null);
            final int imageGrassWidth = imageGrass.getWidth(null);
            final int imageGrassHeight = imageGrass.getHeight(null);
            final int imageRockWidth = imageRock.getWidth(null);
            final int imageTrapWidth = imageTrap.getWidth(null);
            final int imageTrapHeight = imageTrap.getHeight(null);


            final int imageRockHeight = imageRock.getHeight(null);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();
            int lineNumber = 0;
            int columnNumber = 0;
            while (line != null) {
                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    switch (element) {
                        case 'T':
                            environment.add(new SolidSprite(imageTree, columnNumber * imageTreeWidth,
                                    lineNumber * imageTreeHeight, imageTreeWidth, imageTreeHeight));
                            break;
                        case ' ':
                            environment.add(new Sprite(imageGrass, columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight, imageGrassWidth, imageGrassHeight));
                            break;
                        case 'R':
                            environment.add(new SolidSprite(imageRock, columnNumber * imageRockWidth,
                                    lineNumber * imageRockHeight, imageRockWidth, imageRockHeight));
                            break;
                        case '.': // Représente une sortie
                            environment.add(new ExitSprite(imageTrap, columnNumber * imageTrapWidth,
                                    lineNumber * imageTrapHeight, imageTrapWidth, imageTrapHeight));
                            break;
                    }
                    columnNumber++;
                }
                columnNumber = 0;
                lineNumber++;
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SolidSprite> getSolidSpriteList() {
        ArrayList<SolidSprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add((SolidSprite) sprite);
        }
        return solidSpriteArrayList;
    }

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }

    /**
     *Vérifie si le joueur (hero) est sur une sortie
     * Utilisée lors de l'update du PhysicEngine
     * @param player
     * @return True : est sur une sortie
     */
    public boolean isPlayerInExit(DynamicSprite player) {
        for (Sprite sprite : environment) {
            if (sprite instanceof ExitSprite) {
                ExitSprite exit = (ExitSprite) sprite;
                if (exit.getHitBox().intersects(player.getHitBox())) {
                    System.out.println("Collision détectée avec la sortie!");
                    return true;
                }
            }
        }
        return false;
    }


}