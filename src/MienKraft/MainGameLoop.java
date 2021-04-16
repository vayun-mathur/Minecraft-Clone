package MienKraft;

import Cube.BlockType;
import Entities.Entity;
import RenderEngine.DisplayManager;
import Textures.ModelTexture;
import gui.GuiObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import wrapper.Game;
import wrapper.Input;
import wrapper.Rendering;

import java.util.ArrayList;
import java.util.List;

public class MainGameLoop {

	public static List<Entity> entities = new ArrayList<>();
	
	public static boolean closeDisplay = false;

	private static Rendering renderer;

	private static Game game;

	static GuiObject obj;

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		renderer = new Rendering();
		BlockType.init(renderer);
		game = new Game(renderer.getLoader());
		obj = new GuiObject(new ModelTexture(renderer.getLoader().loadTexture("textures/gui/inventory")),
				new Vector2f(176.f/255/4, -164.f/255/2),
				new Vector2f((float)Display.getHeight()/Display.getWidth(), 1f));

		while (!Display.isCloseRequested()) {

			run();

			Input.update();
			DisplayManager.updateDisplay();
			
		}
		renderer.cleanUp();
		DisplayManager.closeDisplay();

	}

	static String gameState = "game";

	public static void run() {
		if(gameState.equals("game")) runGame();
		else if(gameState.equals("inventory")) runInventory();
	}

	public static void runInventory() {
		game.updateWorld(renderer.getLoader());
		game.getPlayer().getInventory().renderPrepare(renderer);
		renderer.getRenderer().addGUI(obj);

		game.getPlayer().getInventory().input();

		renderer.prepare(game);
		renderer.render(game);
		if(Input.getKeyDown(Keyboard.KEY_E)) {
			gameState = "game";
			Mouse.setGrabbed(true);
		}
	}

	public static void runGame() {
		game.movePlayer();

		if(Input.getMouseButtonDown(0)) {
			BlockType type = dig();
			game.getPlayer().getInventory().addItem(type);
		}

		game.updateWorld(renderer.getLoader());

		renderer.prepare(game);
		renderer.render(game);

		if(Input.getKeyDown(Keyboard.KEY_E)) {
			gameState = "inventory";
			Mouse.setGrabbed(false);
		}
	}

	public static BlockType dig() {
		Matrix4f dirmatrix = ((Matrix4f)new Matrix4f().setIdentity())
				.rotate((float)Math.toRadians(180-game.getPlayer().getYaw()), new Vector3f(0, 1, 0))
				.rotate((float)Math.toRadians(game.getPlayer().getPitch()), new Vector3f(1, 0, 0));
		Vector3f dirVector = new Vector3f(Matrix4f.transform(dirmatrix, new Vector4f(0, 0, 1, 0), null));
		dirVector = (Vector3f) dirVector.normalise(null).scale(0.01f);
		Vector3f origin = new Vector3f(game.getPlayer().getPosition());
		int i=0;
		while(game.getWorld().getBlock(Math.round(origin.x), Math.round(origin.y), Math.round(origin.z)) == null) {
			Vector3f.add(origin, dirVector, origin);
			i++;
			if(i>10000) {
				return null;
			}
		}
		BlockType type = game.getWorld().getBlock(Math.round(origin.x), Math.round(origin.y), Math.round(origin.z)).type;
		game.getWorld().removeBlock(Math.round(origin.x), Math.round(origin.y), Math.round(origin.z), renderer);
		return type;
	}

}
