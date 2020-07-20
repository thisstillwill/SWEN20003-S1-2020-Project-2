import bagel.util.Point;

/**
 * Generates Projectiles based on given information.
 */
public class ProjectileFactory {

    private final ShadowDefend game;

    /**
     * Create a new Projectile Factory.
     *
     * @param game The instance of the game associated with this Projectile Factory
     */
    public ProjectileFactory(ShadowDefend game) {
        this.game = game;
    }

    /**
     * Create a new Projectile based on the given Projectile type.
     *
     * @param position Point to spawn Projectile
     * @param target The Projectile's target
     * @param projectileType The type of Projectile to be generated
     * @return The new Projectile
     */
    public Projectile getProjectile(Point position, Slicer target, String projectileType) {
        if (position == null || target == null)
            return null;
        if (projectileType.equalsIgnoreCase("tankprojectile"))
            return new TankProjectile(game, position, target);
        else if (projectileType.equalsIgnoreCase("supertankprojectile"))
            return new SuperTankProjectile(game, position, target);
        else return null;
    }
}
