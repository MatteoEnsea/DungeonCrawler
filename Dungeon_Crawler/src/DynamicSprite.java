import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {
    boolean isWalking = true;
    double speed = 0;
    final int spriteSheetNumberOfColumn = 10;
    int timeBetweenFrame = 200;
    Direction direction = Direction.NORTH;
    int life = 20;

    public DynamicSprite(Image image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void move() {
        switch (direction) {
            case NORTH -> {
                this.y -= speed;
            }
            case SOUTH -> {
                this.y += speed;
            }
            case EAST -> {
                this.x += speed;
            }
            case WEST -> {
                this.x -= speed;
            }
        }
    }

    private boolean isMovingPossible(ArrayList<SolidSprite> environment) {
        Rectangle2D.Double moved = null;
        switch (direction) {
            case EAST:
                moved = new Rectangle2D.Double(x + speed,y,width, height);
                break;
            case WEST:
                moved = new Rectangle2D.Double(x - speed,y,width, height);
                break;
            case NORTH:
                moved = new Rectangle2D.Double(x,y-speed,width, height);
                break;
            case SOUTH:
                moved = new Rectangle2D.Double(x,y+speed,width, height);
                break;
        }

        for (Sprite s : environment) {
            if (s != this) {
                if (((SolidSprite) s).getHitBox().intersects(moved)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void moveIfPossible(ArrayList<SolidSprite> environment) {
        if (isMovingPossible(environment)) {
            move();
        }
    }

    @Override
    public void draw(Graphics g) {
        int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberOfColumn);


        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                (int) (index * this.width), (int) (direction.getFrameLineNumber() * height),
                (int) ((index + 1) * this.width), (int) ((direction.getFrameLineNumber() + 1) * this.height), null);
    }
}
