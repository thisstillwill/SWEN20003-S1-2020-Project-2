import bagel.util.Point;

/**
 * A super tank projectile.
 */
public class SuperTankProjectile extends Projectile {

    private static final String IMAGE_FILE = "res/images/supertank_projectile.png";
    private static final int DAMAGE = 3;

    /**
     * Create a new Super Tank Projectile.
     *
     * @param game The instance of the game associated with this Projectile
     * @param position Point to spawn Projectile
     * @param target The Projectile's target
     */
    public SuperTankProjectile(ShadowDefend game, Point position, Slicer target) {
        super(game, position, IMAGE_FILE, target, DAMAGE);
    }
}
