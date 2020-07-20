import bagel.util.Point;

import java.util.Date;

/**
 * A tank projectile.
 */
public class TankProjectile extends Projectile {

    private static final String IMAGE_FILE = "res/images/tank_projectile.png";
    private static final int DAMAGE = 1;

    /**
     * Create a new Tank Projectile.
     *
     * @param game The instance of the game associated with this Projectile
     * @param position Point to spawn Projectile
     * @param target The Projectile's target
     */
    public TankProjectile(ShadowDefend game, Point position, Slicer target) {
        super(game, position, IMAGE_FILE, target, DAMAGE);
    }
}
