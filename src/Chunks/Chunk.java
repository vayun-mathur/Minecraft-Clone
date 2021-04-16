package Chunks;

import Cube.Block;
import org.lwjgl.util.vector.Vector3f;

public class Chunk {
	
	public Block[][][] blocks;
	public Coordinate2 origin;
	private final ChunkMesh mesh;
	
	public Chunk(Block[][][] blocks, Coordinate2 origin) {
		
		this.blocks = blocks;
		this.origin = origin;
		this.mesh = new ChunkMesh(this);
		
	}

	public void removeBlock(int x, int y, int z) {
		blocks[x][y][z] = null;
		mesh.update(this);
	}

	public Block[][][] getBlocks() {
		return blocks;
	}

	public Coordinate2 getOrigin() {
		return origin;
	}

	public ChunkMesh getMesh() {
		return mesh;
	}
}
