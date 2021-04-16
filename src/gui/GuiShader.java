package gui;

import Entities.Camera;
import Shaders.ShaderProgram;
import Toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

public class GuiShader extends ShaderProgram {
	
	private static final String vertexFile = "/gui/vertexShader.txt";
	private static final String fragmentFile = "/gui/fragmentShader.txt";

	public GuiShader() {
		super(vertexFile, fragmentFile);
	}

	int location_translation;
	int location_scale;

	@Override
	protected void bindAttributes() {

		super.bindAttribute("position", 0);
		super.bindAttribute("textureCoords", 1);
		
	}

	@Override
	protected void getAllUniformLocations() {
		location_translation = super.getUniformLocation("translation");
		location_scale = super.getUniformLocation("scale");
	}

	public void loadPosition(Vector2f translation) {
		super.load2DVector(location_translation, translation);
	}

	public void loadScale(Vector2f scale) {
		super.load2DVector(location_scale, scale);
	}
	
}
