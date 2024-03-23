import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

public class UpgradeImageView extends ImageView {

    public UpgradeImageView(int x, int y, int width, int height, Image img){
        super(img);
        this.relocate(x, y);
        this.resize(width, height);
        this.setPickOnBounds(true);
    }
}
