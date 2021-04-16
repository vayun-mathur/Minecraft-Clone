package wrapper;

import RenderEngine.Loader;
import RenderEngine.MasterRenderer;
import Shaders.StaticShader;
import gui.GuiObject;
import org.lwjgl.util.vector.Vector3f;

import static MienKraft.MainGameLoop.entities;
import static wrapper.World.WORLD_SIZE;

public class Rendering {
    Loader loader;
    StaticShader shader;
    MasterRenderer renderer;
    public Rendering() {
        loader = new Loader();
        GuiObject.init(loader);
        shader = new StaticShader();
        renderer = new MasterRenderer();
    }

    public Loader getLoader() {
        return loader;
    }

    public StaticShader getShader() {
        return shader;
    }

    public MasterRenderer getRenderer() {
        return renderer;
    }

    public void prepare(Game game) {
        for (int i = 0; i < entities.size(); i++) {

            Vector3f origin = entities.get(i).getPosition();

            int distX = (int) (game.getPlayer().getPosition().x - origin.x);
            int distZ = (int) (game.getPlayer().getPosition().z - origin.z);

            if (distX < 0) {
                distX = -distX;
            }
            if (distZ < 0) {
                distZ = -distZ;
            }

            if ((distX <= WORLD_SIZE) && (distZ <= WORLD_SIZE)) {

                renderer.addEntity(entities.get(i));

            }

        }
    }

    public void render(Game game) {
        renderer.render(game.getPlayer().getCamera());
    }

    public void cleanUp() {
        loader.cleanUp();
        shader.cleanUp();
    }
}
