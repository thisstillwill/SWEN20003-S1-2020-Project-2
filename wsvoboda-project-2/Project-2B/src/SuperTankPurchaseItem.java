import bagel.util.Point;

/**
 * A purchase item for super tanks.
 */
public class SuperTankPurchaseItem extends PurchaseItem {

    private static final Point POSITION = new Point(INITIAL_OFFSET + ITEM_OFFSET, Y_POSITION);
    private static final String IMAGE_FILE = "res/images/supertank.png";
    private static final int PRICE = 600;
    private final ActiveTowerFactory activeTowerFactory;

    /**
     * Create a new Super Tank Purchase Item.
     *
     * @param game The instance of the game associated with this Purchase Item
     */
    public SuperTankPurchaseItem(ShadowDefend game) {
        super(game, POSITION, IMAGE_FILE, PRICE);
        activeTowerFactory = new ActiveTowerFactory(game);
    }

    /**
     * Place a new Super Tank at the given position.
     *
     * @param game The instance of the game associated with the Super Tank
     * @param position The position to place the Super Tank
     */
    @Override
    protected void placeTower(ShadowDefend game, Point position) {
        activeTowerFactory.getActiveTower(position, "supertank");
    }
}
