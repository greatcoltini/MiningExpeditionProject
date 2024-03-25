import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is a custom ImageView class for upgrades. In our constructor we require an image,
 * and then assign that it can be clicked.
 *
 * @author Colton Donkersgoed
 */
public class UpgradeImageView extends ImageView {

    /**
     * The constructor for the Upgrade Image View class. This constructor takes in the
     * following parameters and generates an ImageView with an image attached from it.
     * @param x: an integer specifying where to draw the image on the canvas
     * @param y: an integer specifying where to draw the image on the canvas
     * @param width: the width of the image, integer
     * @param height: the height of the image, integer
     * @param img: the link to the image to draw
     */
    public UpgradeImageView(int x, int y, int width, int height, Image img){
        super(img);
        this.relocate(x, y);
        this.resize(width, height);
        this.setPickOnBounds(true);
    }
}
