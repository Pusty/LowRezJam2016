package game.engine.world;

import game.engine.main.GameClass;





public class World {
	
	int sizex;
	int sizey;
	Chunk[] chunkarray;
//	Player player;
	GameClass mainclass;

	public World(GameClass m,int sx, int sy) {
		mainclass=m;
		sizex = sx;
		sizey = sy;
		chunkarray = new Chunk[(sx/10) * (sy/10)];
		int c=0;
		for(int cx=0;cx<sx/10;cx++)
			for(int cz=0;cz<sy/10;cz++){
			chunkarray[c]=new Chunk(cx,cz,10,10);c++;}
	}


//	public Player getPlayer(){
//		return player;
//	}
	
//	public void setPlayer(Player p){
//		player=p;
//	}
	
	public int getBlockID(int x, int y) {
		int wx = x/10;
		int wy = y/10;
		if(wx*sizey/10+wy>=sizex/10*sizey/10)return 0;
		if(x<0 || y<0)return 0;
		return chunkarray[((wx*(sizey/10))+wy)].getBlockID(x-(10*wx),  y-(10*wy));
	}

	public void setBlockID(int x, int y,int id) {
		int wx = x/10;
		int wy = y/10;
		if(wx*sizey/10+wy>=sizex/10*sizey/10)return;
		chunkarray[((wx*(sizey/10))+wy)].setBlockID(x-(10*wx),  y-(10*wy),id);
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
