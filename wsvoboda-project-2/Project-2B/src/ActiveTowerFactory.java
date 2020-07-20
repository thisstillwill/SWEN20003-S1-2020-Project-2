import bagel.util.Point;

/**
 * Generates Active Towers based on given information.
 */
public class ActiveTowerFactory {

    private final ShadowDefend game;

    /**
     * Create a new Active Tower Factory.
     *
     * @param game The instance of the game associated with this Active Tower Factory
     */
    public ActiveTowerFactory(ShadowDefend game) {
        this.game = game;
    }

    /**
     * Create a new Active Tower based on the given Active Tower type.
     *
     * @param position Point to place tower
     * @param activeTowerType The type of Active Tower to be generated
     * @return The new Active Tower
     */
    public ActiveTower getActiveTower(Point position, String activeTowerType) {
        if (position == null || activeTowerType == null)
            return null;
        if (activeTowerType.equalsIgnoreCase("tank"))
            return new Tank(game, position);
        else if (activeTowerType.equalsIgnoreCase("supertank"))
            return new SuperTank(game, position);
        else return null;
    }
}
