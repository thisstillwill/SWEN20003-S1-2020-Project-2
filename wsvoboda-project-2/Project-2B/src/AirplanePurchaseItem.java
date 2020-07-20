import bagel.util.Point;

/**
 * A purchase item for airplanes.
 */
public class AirplanePurchaseItem extends PurchaseItem{

    private static final Point POSITION = new Point(INITIAL_OFFSET + ITEM_OFFSET * 2, Y_POSITION);
    private static final String IMAGE_FILE = "res/images/airsupport.png";
    private static final int PRICE = 500;

    /**
     * Create a new Airplane Purchase Item.
     *
     * @param game The instance of the game associated with this Purchase Item.
     */
    public AirplanePurchaseItem(ShadowDefend game) {
        super(game, POSITION, IMAGE_FILE, PRICE);
    }

    /**
     * Return if a new Airplane can be placed at the given position. A position that intersects a game panel or is
     * outside of the game window is invalid.
     *
     * @param position The position to initially place the Airplane
     * @return Boolean representing if an Airplane can be placed
     */
    @Override
    protected boolean canPlace(Point position) {
        if (game.intersectsPanels(position)) return false;
        else if (position.x < 0 || position.x > ShadowDefend.getWidth() || position.y < 0
                || position.y > ShadowDefend.getHeight()) return false;
        else return true;
    }

    /**
     * Place a new Airplane at the given position.
     *
     * @param game The instance of the game associated with the Airplane
     * @param position The position to initially place the Airplane
     */
    @Override
    protected void placeTower(ShadowDefend game, Point position) {
        new Airplane(game, position);
    }
}
