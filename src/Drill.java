import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * An implementation of the Drill variation of Worker.
 *
 * @author Colton Donkersgoed
 *
 * TODO: Docstring
 */
public class Drill extends Worker{

    /** The image for the Drill **/
    private Image img = new Image(new FileInputStream("src/assets/drillWorker.png"));

    /**
     *  Creates an instance of the Drill class.
     * @param x: int representing where to draw the Drill
     * @param y: int representing where to draw the Drill
     * @throws FileNotFoundException: throws an error if we can't find the image
     */
    public Drill (int x, int y) throws FileNotFoundException {
        super(x, y);
        this.setAmount(5);
        this.setType("Drill");
    }

    /**
     * Draws the Drill object onto the given GraphicsContext
     * @param gc: The GraphicsContext with which to draw the worker
     */
    public void draw(GraphicsContext gc){
        gc.drawImage(img, getX(), getY());
    }


}
