import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

/**
 * A slicer enemy.
 */
public abstract class Slicer extends GameObject {

    private final ShadowDefend game;
    protected final List<Point> polyline;
    private final double speed;
    private int health;
    private final int reward;
    private final int penalty;
    protected int targetPointIndex;
    private boolean finished;
    protected final SlicerFactory slicerFactory;

    /**
     * Create a new Slicer.
     *
     * @param game The instance of the game associated with this Slicer
     * @param health The health of the Slicer
     * @param reward The money earned upon elimination
     * @param penalty The lives lost upon finishing
     * @param speed The speed of the Slicer in pixels/frame
     * @param imageFile The Slicer's sprite
     * @param polyline The polyline that the Slicer must traverse (must have at least 1 point)
     */
    public Slicer(ShadowDefend game, List<Point> polyline, String imageFile, double speed, int health, int reward,
                  int penalty) {
        super(polyline.get(0), imageFile);
        this.game = game;
        this.speed = speed;
        this.health = health;
        this.reward = reward;
        this.penalty = penalty;
        this.polyline = polyline;
        this.targetPointIndex = 1;
        this.finished = false;
        this.game.attach(this);
        slicerFactory = new SlicerFactory(game);
    }

    /**
     * Updates the current state of the Slicer. The slicer moves towards its next target point in
     * the polyline at its specified movement rate.
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {
        if (finished) {
            return;
        }
        // If all health is lost remove the Slicer and reward the player
        if (health <= 0) {
            game.addMoney(reward);
            deathEffect();
            this.game.detach(this);
            return;
        }

        //------------------------------------------
        // START COPIED CODE (from rohylj-project-1)
        // Code here is identical to original source
        // implementation of Slicer update.
        //------------------------------------------
        // Obtain where we currently are, and where we want to be
        Point currentPoint = getCenter();
        Point targetPoint = polyline.get(targetPointIndex);
        // Convert them to vectors to perform some very basic vector math
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 distance = target.sub(current);
        // Distance we are (in pixels) away from our target point
        double magnitude = distance.length();
        // Check if we are close to the target point
        if (magnitude < speed * ShadowDefend.getTimescale()) {
            // Check if we have reached the end
            if (targetPointIndex == polyline.size() - 1) {
                finished = true;
                game.reduceLives(penalty);
                this.game.detach(this);
                return;
            } else {
                // Make our focus the next point in the polyline
                targetPointIndex += 1;
            }
        }
        // Move towards the target point
        // We do this by getting a unit vector in the direction of our target, and multiplying it
        // by the speed of the slicer (accounting for the timescale)
        super.move(distance.normalised().mul(speed * ShadowDefend.getTimescale()));
        // Update current rotation angle to face target point
        setAngle(Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x));
        super.update(input);
        //------------------------------------------
        // END COPIED CODE
        //------------------------------------------
    }

    /**
     * Set the position of the Slicer manually.
     *
     * @param position The Point representing the desired position
     * @param newTargetPointIndex The index of the Point the Slicer should start targeting
     */
    public void setPosition(Point position, int newTargetPointIndex) {
        super.setPosition(position);
        targetPointIndex = newTargetPointIndex;
    }

    /**
     * Perform an action or behavior upon death.
     */
    protected abstract void deathEffect();

    /**
     * Reduce the health of the Slicer by a specified amount.
     *
     * @param damage The amount of damage to take
     */
    public void takeDamage(int damage) {
        health -= damage;
    }

    //------------------------------------------
    // START COPIED CODE (from rohylj-project-1)
    //------------------------------------------
    /**
     * Return if the Slicer has reached the end.
     *
     * @return Boolean representing if the Slicer is finished
     */
    public boolean isFinished() {
        return finished;
    }
    //------------------------------------------
    // END COPIED CODE
    //------------------------------------------
}
