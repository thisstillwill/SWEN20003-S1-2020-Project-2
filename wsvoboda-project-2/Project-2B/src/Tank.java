import bagel.util.Point;

/**
 * A tank.
 */
public class Tank extends ActiveTower {

    private static final String IMAGE_FILE = "res/images/tank.png";
    private static final String projectileType = "tankprojectile";
    private static final int ATTACK_RADIUS = 100;
    private static final int PROJECTILE_COOLDOWN = 1000;

    /**
     * Create a new Tank.
     *
     * @param game The instance of the game associated with this Active Tower
     * @param position Point to place tower
     */
    public Tank(ShadowDefend game, Point position) {
        super(game, position, IMAGE_FILE, projectileType, ATTACK_RADIUS, PROJECTILE_COOLDOWN);
    }
}
