package game.engine.world;


import game.engine.entity.Entity;
import game.engine.entity.Player;
import game.engine.main.GameClass;





public class World {
	
	int sizex;
	int sizey;
	Chunk[] chunkarray;
	Entity[] entityarray;
	Player player;
	GameClass mainclass;

	public World(GameClass m,int sx, int sy) {
		mainclass=m;
		sizex = sx;
		sizey = sy;
		chunkarray = new Chunk[(sx/16) * (sy/16)];
		int c=0;
		for(int cx=0;cx<sx/16;cx++)
			for(int cz=0;cz<sy/16;cz++){
			chunkarray[c]=new Chunk(cx,cz,16,16);c++;}
		entityarray  = new Entity[32]; //Entity Limit
	}

	

	public int addEntity(Entity e){
		for(int i=0;i<entityarray.length;i++){
			if(entityarray[i]==null){
				entityarray[i] = e;return i;}
		}return -1;
	}
	
	public void removeEntity(Entity e){
		for(int i=0;i<entityarray.length;i++){
			if(entityarray[i]==e){
				entityarray[i] = null;return;}
		}
	}
	public Entity[] getEntityArray(){
		return entityarray;
	} 


	public Player getPlayer(){
		return player;
	}
	
	public void setPlayer(Player p){
		player=p;
	}
	
	public int getBlockID(int x, int y) {
		int wx = x/16;
		int wy = y/16;
		if(wx*sizey/16+wy>=sizex/16*sizey/16)return 0;
		if(x<0 || y<0)return 0;
		return chunkarray[((wx*(sizey/16))+wy)].getBlockID(x-(16*wx),  y-(16*wy));
	}
	
	public int getBlockIDFore(int x, int y) {
		int wx = x/16;
		int wy = y/16;
		if(wx*sizey/16+wy>=sizex/16*sizey/16)return 0;
		if(x<0 || y<0)return 0;
		return chunkarray[((wx*(sizey/16))+wy)].getBlockIDFore(x-(16*wx),  y-(16*wy));
	}
	
	public int getBlockIDBack(int x, int y) {
		int wx = x/16;
		int wy = y/16;
		if(wx*sizey/16+wy>=sizex/16*sizey/16)return 0;
		if(x<0 || y<0)return 0;
		return chunkarray[((wx*(sizey/16))+wy)].getBlockIDBack(x-(16*wx),  y-(16*wy));
	}

	public void setBlockID(int x, int y,int id) {
		int wx = x/16;
		int wy = y/16;
		if(wx*sizey/16+wy >=sizex/16*sizey/16)return;
		chunkarray[((wx*(sizey/16))+wy)].setBlockID(x-(16*wx),  y-(16*wy),id);
	}
	public void setBlockIDBack(int x, int y,int id) {
		int wx = x/16;
		int wy = y/16;
		if(wx*sizey/16+wy>=sizex/16*sizey/16)return;
		chunkarray[((wx*(sizey/16))+wy)].setBlockIDBack(x-(16*wx),  y-(16*wy),id);
	}
	public void setBlockIDFore(int x, int y,int id) {
		int wx = x/16;
		int wy = y/16;
		if(wx*sizey/16+wy>=sizex/16*sizey/16)return;
		chunkarray[((wx*(sizey/16))+wy)].setBlockIDFore(x-(16*wx),  y-(16*wy),id);
	}
	
	public int getSizeX() {
		return sizex;
	}

	public int getSizeY() {
		return sizey;
	}
	
	public Chunk[] getChunkArray(){
		return chunkarray;
	}
	
}
