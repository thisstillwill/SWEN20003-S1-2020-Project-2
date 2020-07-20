import bagel.util.Point;

/**
 * A super tank.
 */
public class SuperTank extends ActiveTower {

    private static final String IMAGE_FILE = "res/images/supertank.png";
    private static final String projectileType = "supertankprojectile";
    private static final int ATTACK_RADIUS = 150;
    private static final int PROJECTILE_COOLDOWN = 500;

    /**
     * Create a new Super Tank.
     *
     * @param game The instance of the game associated with this Active Tower
     * @param position Point to place tower
     */
    public SuperTank(ShadowDefend game, Point position) {
        super(game, position, IMAGE_FILE, projectileType, ATTACK_RADIUS, PROJECTILE_COOLDOWN);
    }
}
