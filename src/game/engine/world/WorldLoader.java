package game.engine.world;

import game.engine.main.GameClass;

import com.badlogic.gdx.files.FileHandle;

public class WorldLoader {
	public static World loadWorld(GameClass g, FileHandle handle) {
		try {
		String text = handle.readString();
		String lines[] = text.split("\n");
		World world = new World(g,lines[0].length(),lines.length);
		for(int y=0;y<lines.length;y++) {
			int count = 0;
			for(char c:lines[y].toCharArray())
				if(c==',')
					count++;
			for(int x=0;x<count;x++)
				world.setBlockID(x,y, Integer.parseInt(GameClass.splitNonRegex(lines[y]
								, ",")[x].trim()));
		}
		return world;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
