package RenderEngine;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import Models.RawModel;

public class Loader {
	
	static List<Integer> vaos = new ArrayList<>();
	static List<Integer> vbos = new ArrayList<>();
	static List<Integer> textures = new ArrayList<>();
	
	public RawModel loadToVAO(float[] vertices, int[] indices, float[] uv) {
		
		int vaoID = createVAO();
		storeDataInAttributeList(vertices, 0, 3);
		storeDataInAttributeList(uv, 1, 2);
		bindIndicesBuffer(indices);
		GL30.glBindVertexArray(0);
		
		return new RawModel(vaoID, indices.length);
		
	}

	public RawModel loadToVAO2D(float[] vertices) {

		int vaoID = createVAO();
		storeDataInAttributeList(vertices, 0, 2);
		GL30.glBindVertexArray(0);

		return new RawModel(vaoID, vertices.length/2);

	}
	
	public RawModel loadToVAO(float[] vertices, float[] uv) {
		
		int vaoID = createVAO();
		storeDataInAttributeList(vertices, 0, 3);
		storeDataInAttributeList(uv, 1, 2);
		GL30.glBindVertexArray(0);
		
		return new RawModel(vaoID, vertices.length/3);
		
	}
	
	private int createVAO() {
		
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		
		return vaoID;
		
	}
	
	public int loadTexture(String fileName) {
		
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", Loader.class.getResourceAsStream("/res/" + fileName + ".PNG"));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -4);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert texture != null;
		int textureID = texture.getTextureID();
		textures.add(textureID);
		
		return textureID;
		
	}
	
	private void storeDataInAttributeList(float[] data, int attributeNumber, int dimentions) {
		
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, dimentions, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	
	private void bindIndicesBuffer(int[] indices) {
		
		int vboID =GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
	}
	
	IntBuffer storeDataInIntBuffer(int[] data) {
		
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
		
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
		
	}
	
	public void cleanUp() {
		
		for (int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		
		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		
		for (int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
		
	}

}
