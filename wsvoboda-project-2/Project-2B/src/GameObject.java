import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

//------------------------------------------
// START COPIED CODE (from rohylj-project-1)
// All of the GameObject class was lifted
// from the Sprite class, except for the
// addition of the setPosition() method
// and the renaming of some variables.
//------------------------------------------

/**
 * A game entity.
 */
public abstract class GameObject {

    // The image used for the GameObject's appearance
    private final Image sprite;
    private Rectangle rect;
    private double angle;

    /**
     * Create a new GameObject.
     *
     * @param point    The starting point for the GameObject
     * @param imageSrc The image which will be rendered at the entity's point
     */
    public GameObject(Point point, String imageSrc) {
        this.sprite = new Image(imageSrc);
        this.rect = sprite.getBoundingBoxAt(point);
        this.angle = 0;
    }

    /**
     * Return the Rectangle expressing the GameObject's bounding box.
     *
     * @return The GameObject's Rectangle
     */
    public Rectangle getRect() {
        return new Rectangle(rect);
    }

    /**
     * Move the GameObject by a specified delta.
     *
     * @param dx The move delta vector
     */
    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }

    /**
     * Return a Point representing the center of the GameObject.
     *
     * @return The Point at the center of the GameObject
     */
    public Point getCenter() {
        return getRect().centre();
    }

    /**
     * Set the angle of the GameObject's rotation.
     *
     * @param angle The angle of the GameObject in radians
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Update the GameObject. Default behavior is to render the GameObject at its current position.
     */
    public void update(Input input) {
        sprite.draw(getCenter().x, getCenter().y, new DrawOptions().setRotation(angle));
    }

    //------------------------------------------
    // END COPIED CODE
    //------------------------------------------

    /**
     * Manually set the center of the GameObject to the given position.
     *
     * @param pos Point representing the desired position
     */
    public void setPosition(Point pos) {
        rect = sprite.getBoundingBoxAt(pos);
    }
}
