import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

/**
 * An active tower.
 */
public abstract class ActiveTower extends GameObject {

    private static final int MILLISECONDS_PER_SECOND = 1000;
    private final ShadowDefend game;
    private final ProjectileFactory projectileFactory;
    private final String projectileType;
    private final int attackRadius;
    private final int projectileCooldown;
    private double frameCount;

    /**
     * Create a new Active Tower.
     *
     * @param game The instance of the game associated with this tower
     * @param position Point to place tower
     * @param imageFile The tower's sprite
     * @param projectileType The type of projectile this tower launches
     * @param attackRadius Range in pixels tower can attack
     * @param projectileCooldown Delay in milliseconds before each attack
     */
    public ActiveTower(ShadowDefend game, Point position, String imageFile, String projectileType, int attackRadius,
                       int projectileCooldown) {
        super(position, imageFile);
        this.game = game;
        this.projectileFactory = new ProjectileFactory(game);
        this.projectileType = projectileType;
        this.attackRadius = attackRadius;
        this.projectileCooldown = projectileCooldown;
        this.frameCount = Integer.MAX_VALUE;
        this.game.attach(this);
    }

    /**
     * Update the current state of the Active Tower. The Active Tower chooses a target within its radius, if one exists,
     * and fires a Projectile at it. Once a Projectile is fired, the Active Tower cannot fire until a cooldown period
     * has elapsed.
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {
        // Increase the frame counter by the current timescale
        frameCount += ShadowDefend.getTimescale();

        // If the Active Tower can attack, find a target and attack if possible
        if (frameCount / ShadowDefend.getFPS() * MILLISECONDS_PER_SECOND >= projectileCooldown) {
            Slicer target;
            if ((target = findTarget()) != null) {
                // Fire a Projectile at the target
                projectileFactory.getProjectile(getCenter(), target, projectileType);
                frameCount = 0;
                // Face the target
                Point currentPoint = getCenter();
                Point targetPoint = target.getCenter();
                double angle = Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x);
                angle += Math.PI / 2;
                setAngle(angle);
            }
        }
        super.update(input);
    }

    /* Find the first Slicer in play that is within the Active Tower's attack radius. Slicers are considered in the
       order they were spawned. Since Slicers follow a linear path, this method guarantees the Active Tower will attack
       the Slicer that is both within its attack radius and is closest to the end. */
    private Slicer findTarget() {
        List<Slicer> currentSlicers = game.getSlicers();
        // Obtain where we currently are
        Point currentPoint = getCenter();
        for (Slicer slicer : currentSlicers) {

            //------------------------------------------
            // START COPIED CODE (from rohylj-project-1)
            // Copied code is from vector logic from
            // Rohyl's Slicer class to find distance
            // from target.
            //------------------------------------------
            // Obtain where we are trying to target
            Point candidatePoint = slicer.getCenter();
            // Convert them to vectors to perform some very basic vector math
            Vector2 candidate = candidatePoint.asVector();
            Vector2 current = currentPoint.asVector();
            Vector2 distance = candidate.sub(current);
            // Distance we are (in pixels) away from our target point
            double magnitude = distance.length();
            //------------------------------------------
            // END COPIED CODE
            //------------------------------------------

            // Return target if distance is within radius
            if (magnitude <= attackRadius) return slicer;
        }
        return null; // A target could not be found within range
    }
}
