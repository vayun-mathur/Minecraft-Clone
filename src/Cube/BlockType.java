package Cube;

import Textures.ModelTexture;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.lwjgl.util.vector.Vector2f;
import wrapper.Rendering;

import java.io.IOException;

public class BlockType {
	public static BlockType GRASS;
	public static BlockType DIRT;
	public static BlockType STONE;
	public static BlockType TREEBARK;
	public static BlockType TREELEAF;

	public static void init(Rendering rendering) {
		JSONObject blockTex = null;
		try {
			blockTex = (JSONObject) new JSONParser().parse(new String(BlockType.class.getResourceAsStream("/res/textures/block/textures.json").readAllBytes()));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		assert blockTex != null;
		GRASS = new BlockType(1, new ModelTexture(rendering.getLoader().loadTexture("textures/block/grass_block_side")), true, getVector2((JSONObject) blockTex.get("grass_block_side")), getVector2((JSONObject) blockTex.get("grass_block_top")), getVector2((JSONObject) blockTex.get("dirt")));
		DIRT = new BlockType(2, new ModelTexture(rendering.getLoader().loadTexture("textures/block/dirt")), true, getVector2((JSONObject) blockTex.get("dirt")));
		STONE = new BlockType(3, new ModelTexture(rendering.getLoader().loadTexture("textures/block/stone")), true, getVector2((JSONObject) blockTex.get("stone")));
		TREEBARK = new BlockType(4, new ModelTexture(rendering.getLoader().loadTexture("textures/block/oak_log")), true, getVector2((JSONObject) blockTex.get("oak_log")), getVector2((JSONObject) blockTex.get("oak_log_top")), getVector2((JSONObject) blockTex.get("oak_log_top")));
		TREELEAF = new BlockType(5, new ModelTexture(rendering.getLoader().loadTexture("textures/block/oak_leaves")), false, getVector2((JSONObject) blockTex.get("oak_leaves")));

	}

	private static Vector2f getVector2(JSONObject obj) {
		return new Vector2f((Long)obj.get("x"), (Long) obj.get("y"));
	}

	Vector2f side, top, bottom;
	int id;
	ModelTexture tex;
	boolean solid;
	BlockType(int id, ModelTexture tex, boolean solid, Vector2f all) {
		this.side = all;
		this.top = all;
		this.bottom = all;
		this.id = id;
		this.tex = tex;
		this.solid = solid;
	}
	BlockType(int id, ModelTexture tex, boolean solid, Vector2f side, Vector2f top, Vector2f bottom) {
		this.side = side;
		this.top = top;
		this.bottom = bottom;
		this.id = id;
		this.tex = tex;
		this.solid = solid;
	}

	public ModelTexture getTex() {
		return tex;
	}

	public Vector2f getSide() {
		return side;
	}

	public Vector2f getTop() {
		return top;
	}

	public Vector2f getBottom() {
		return bottom;
	}

    public int getID() {
		return id;
    }

	public boolean isSolid() {
		return solid;
	}
}