package wrapper;

import Entities.Camera;
import org.lwjgl.util.vector.Vector3f;

public class Player {
    private final Camera camera;
    private final Inventory inventory;

    public Player() {
        this.camera = new Camera(new Vector3f(0, 64, 0), 0, 0, 0);
        this.inventory = new Inventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void move(World w) {
        camera.move(w);
    }

    public Camera getCamera() {
        return camera;
    }

    public Vector3f getPosition() {
        return camera.getPosition();
    }

    public float getYaw() {
        return camera.getRotY();
    }

    public float getPitch() {
        return camera.getRotX();
    }
}
