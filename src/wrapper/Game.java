package wrapper;

import RenderEngine.Loader;

public class Game {
    private final Player player;
    private final World world;

    public Game(Loader loader) {
        player = new Player();

        world = new World(loader, this);
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public void movePlayer() {
        player.move(world);
    }

    public void updateWorld(Loader loader) {
        world.update(loader);
    }
}
