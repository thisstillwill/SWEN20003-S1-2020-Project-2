import bagel.util.Point;

/**
 * A purchase item for tanks.
 */
public class TankPurchaseItem extends PurchaseItem{

    private static final Point POSITION = new Point(INITIAL_OFFSET, Y_POSITION);
    private static final String IMAGE_FILE = "res/images/tank.png";
    private static final int PRICE = 250;
    private final ActiveTowerFactory activeTowerFactory;

    /**
     * Create a new Tank Purchase Item.
     *
     * @param game The instance of the game associated with this Purchase Item
     */
    public TankPurchaseItem(ShadowDefend game) {
        super(game, POSITION, IMAGE_FILE, PRICE);
        activeTowerFactory = new ActiveTowerFactory(game);
    }

    /**
     * Place a new Tank at the given position.
     *
     * @param game The instance of the game associated with the Tank
     * @param position The position to place the Super Tank
     */
    @Override
    protected void placeTower(ShadowDefend game, Point position) {
        activeTowerFactory.getActiveTower(position, "tank");
    }
}
