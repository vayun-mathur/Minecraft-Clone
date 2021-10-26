package Chunks;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Cube.Block;
import Cube.Vertex;
import Models.CubeModel;

public class ChunkMesh {
	
	private final List<Vertex> vertices;
	
	private final List<Float> positionsList;
	private final List<Float> uvsList;
	private final List<Float> normalsList;
	
	public float[] positions, uvs, normals;
	
	public Chunk chunk;

	public ChunkMesh(Chunk chunk) {
		
		this.chunk = chunk;
		
		vertices = new ArrayList<>();
		positionsList = new ArrayList<>();
		uvsList = new ArrayList<>();
		normalsList = new ArrayList<>();
		
		buildMesh();
		populateLists();
		
	}
	
	public void update(Chunk chunk) {
		
		this.chunk = chunk;
		
		buildMesh();
		populateLists();
		
	}
	
	private void buildMesh() {
		
		// Loop through block in chunk and determine which faces are visible
		for(int x=0;x<32;x++) {
			for(int y=0;y<256;y++) {
				for(int z=0;z<32;z++) {
					Block block = chunk.blocks[x][y][z];
					if(doesNotExist(block)) continue;
					boolean[] existArr = new boolean[] {
							x==31 || notSolidBlock(chunk.blocks[x + 1][y][z]),
							x==0 || notSolidBlock(chunk.blocks[x - 1][y][z]),
							y==255 || notSolidBlock(chunk.blocks[x][y + 1][z]),
							y==0 || notSolidBlock(chunk.blocks[x][y - 1][z]),
							z==31 || notSolidBlock(chunk.blocks[x][y][z + 1]),
							z==0 || notSolidBlock(chunk.blocks[x][y][z - 1])
					};
					for(int i=0;i<6;i++) {
						if(existArr[i])
						for (int k = 0; k < 6; k++) {
							Vector2f tex;
							if(i==2) tex = block.type.getTop();
							else if(i==3) tex = block.type.getBottom();
							else tex = block.type.getSide();
							vertices.add(new Vertex(new Vector3f(CubeModel.POS[i][k].x + block.x, CubeModel.POS[i][k].y + block.y, CubeModel.POS[i][k].z + block.z), CubeModel.UV(tex, 32)[k], CubeModel.NORMALS[k]));
						}
					}
				}
			}
		}
		
	}

	private boolean doesNotExist(Block block) {
		return block == null;
	}

	private boolean notSolidBlock(Block block) {
		if(doesNotExist(block)) return true;
		return !block.type.isSolid();
	}

	private void populateLists() {

		for (Vertex vertex : vertices) {

			positionsList.add(vertex.positions.x);
			positionsList.add(vertex.positions.y);
			positionsList.add(vertex.positions.z);
			uvsList.add(vertex.uvs.x);
			uvsList.add(vertex.uvs.y);
			normalsList.add(vertex.normals.x);
			normalsList.add(vertex.normals.y);
			normalsList.add(vertex.normals.z);

		}
		
		positions = new float[positionsList.size()];
		uvs = new float[uvsList.size()];
		normals = new float[normalsList.size()];
		
		for (int i = 0; i < positionsList.size(); i++) {
			positions[i] = positionsList.get(i);
		}
		
		for (int i = 0; i < uvsList.size(); i++) {
			uvs[i] = uvsList.get(i);
		}
		
		for (int i = 0; i < normalsList.size(); i++) {
			normals[i] = normalsList.get(i);
		}
		
		cleanup();
		
	}
	
	public void cleanup() {
    	normalsList.clear();
    	uvsList.clear();
    	positionsList.clear();
    	vertices.clear();
    }

}
