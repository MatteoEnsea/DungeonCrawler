import java.awt.*;

public class Sprite implements Displayable {

    protected Image image;
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    public Sprite(Image image, double x, double y, double width, double height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }

}
