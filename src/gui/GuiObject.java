package gui;

import Models.RawModel;
import RenderEngine.Loader;
import Textures.ModelTexture;
import org.lwjgl.util.vector.Vector2f;

public class GuiObject {

    private static RawModel model;

    private final ModelTexture texture;
    private final Vector2f position;
    private final Vector2f size;

    public static void init(Loader loader) {
        float[] positions = new float[] {
                -1, 1,
                -1, -1,
                1, 1,
                1, -1
        };
        model = loader.loadToVAO2D(positions);
    }

    public GuiObject(ModelTexture texture, Vector2f position, Vector2f size) {
        this.texture = texture;
        this.position = position;
        this.size = size;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getSize() {
        return size;
    }

    public static RawModel getModel() {
        return model;
    }
}
