package game.engine.world;

import game.engine.main.GameClass;

import com.badlogic.gdx.files.FileHandle;

public class WorldLoader {
	public static World loadWorld(GameClass g, FileHandle handle) {
		try {
		String text = handle.readString();
		String lines[] = text.split("\n");
		
		int count = 0;
		for(char c:lines[0].toCharArray())
			if(c==',')
				count++;
		
		int sizeX = count;
		int sizeY = lines.length;
		
		
		World world = new World(g,sizeX,sizeY);
		for(int y=0;y<sizeY;y++) {
			String[] numbers = GameClass.splitNonRegex(lines[y].trim() , ",");
			for(int x=0;x<sizeX;x++) {
				int number = Integer.parseInt(numbers[x].trim());
				world.setBlockID(x,(sizeY-1)-y, number);
			}
		}
		return world;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
