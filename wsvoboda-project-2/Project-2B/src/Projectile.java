import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

/**
 * A projectile.
 */
public abstract class Projectile extends GameObject {

    private static final double SPEED = 10;
    private final ShadowDefend game;
    private final Slicer target;
    private final int damage;

    /**
     * Create a new Projectile.
     *
     * @param game The instance of the game associated with this Projectile
     * @param position Point to spawn Projectile
     * @param imageFile The Projectile's sprite
     * @param target The Projectile's target
     * @param damage The Projectile's damage
     */
    public Projectile(ShadowDefend game, Point position, String imageFile, Slicer target, int damage) {
        super(position, imageFile);
        this.game = game;
        this.target = target;
        this.damage = damage;
        this.game.attach(this);
    }

    /**
     * Update the current state of the Projectile. The Projectile moves towards its target enemy at its specified
     * movement rate.
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {
        // Check if the target is gone (i.e. already destroyed or has reached the end)
        if (target.isFinished() || target == null)
            this.game.detach(this);
        // Check if we have hit the target
        if (hasHit()) {
            target.takeDamage(damage);
            this.game.detach(this);
        }

        //------------------------------------------
        // START COPIED CODE (from rohylj-project-1)
        // Logic and naming is the same, but sources
        // of target point is different due to
        // context.
        //------------------------------------------
        // Obtain where we currently are, and where we want to be
        Point currentPoint = getCenter();
        Point targetPoint = target.getCenter();
        // Convert them to vectors to perform some very basic vector math
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 distance = target.sub(current);
        // Distance we are (in pixels) away from our target point
        double magnitude = distance.length();
        // Move towards the target point
        // We do this by getting a unit vector in the direction of our target, and multiplying it
        // by the speed of the slicer (accounting for the timescale)
        super.move(distance.normalised().mul(SPEED * ShadowDefend.getTimescale()));
        //------------------------------------------
        // END COPIED CODE
        //------------------------------------------

        // Draw the Projectile
        super.update(input);
    }

    /* Return if the center of the Projectile intersects with the bounding box of the target. */
    private boolean hasHit() {
        return target.getRect().intersects(getCenter());
    }
}
