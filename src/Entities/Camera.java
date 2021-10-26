package Entities;

import Chunks.Chunk;
import Chunks.Coordinate2;
import wrapper.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	Vector3f position;
	float rotX;
	float rotY;
	float rotZ;
	
	private static final float SHIFT_SPEED = 0.2f;
	private static final float REG_SPEED = 0.1f;
	private static final float turnSpeed = 0.1f;
	
	public Camera(Vector3f position, float rotX, float rotY, float rotZ) {

		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		
	}

	float yspeed = 0;
	
	public void move(World world) {
		float speed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)?SHIFT_SPEED:REG_SPEED;
		float moveZ;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			moveZ = -speed;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			moveZ = speed;
		} else {
			moveZ = 0;
		}
		float moveX;
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			moveX = -speed;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			moveX = speed;
		} else {
			moveX = 0;
		}
		
		rotX += -Mouse.getDY() * turnSpeed;
		rotY += Mouse.getDX() * turnSpeed;

		int xR = Math.round(position.x);
		int zR = Math.round(position.z);
		
		float dx = (float) -(moveZ * Math.sin(Math.toRadians(rotY)) + moveX * Math.cos(Math.toRadians(rotY)));
		float dz = (float) (moveZ * Math.cos(Math.toRadians(rotY)) - moveX * Math.sin(Math.toRadians(rotY)));

		boolean intersectingBlock = false;
		int x = Math.floorMod(xR, 32);
		int y = (int)position.y;
		int z = Math.floorMod(zR, 32);
		Chunk m = world.chunks.get(new Coordinate2(xR - x, zR - z));
		boolean headheight = m.blocks[x][y+1][z] != null;
		boolean bodyheight = m.blocks[x][y][z] != null;
		boolean belowheight = m.blocks[x][y-1][z] != null;
		if(bodyheight && !headheight) {
			yspeed = 0.1f;
		}
		if(belowheight) {
			intersectingBlock = true;
		}
		if(intersectingBlock) {
			if(yspeed < 0) yspeed = 0;
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				yspeed = 0.2f;
			}
		} else {
			yspeed -= 0.01;
		}

		tryMove(dx, dz, world);


		position.y += yspeed;
		
	}

	private void tryMove(float dx, float dz, World world) {
		int xR = Math.round(position.x+dx*3);
		int zR = Math.round(position.z+dz*3);

		int x = Math.floorMod(xR, 32);
		int y = (int)position.y;
		int z = Math.floorMod(zR, 32);

		Chunk m = world.chunks.get(new Coordinate2(xR - x, zR - z));
		boolean headheight = m.blocks[x][y+1][z] != null;
		boolean bodyheight = m.blocks[x][y][z] != null;
		boolean belowheight = m.blocks[x][y-1][z] != null;
		if(!headheight) {
			position.x += dx;
			position.z += dz;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRotX() {
		return rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

}
