package game.engine.main;

import game.classes.GameRender;
import game.engine.world.World;
import game.engine.world.WorldLoader;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.RawAnimation;
import me.pusty.util.json.JsonHandler;

public class GameClass extends AbstractGameClass {


	

	public GameClass(){
		super();
		
		
	}
	
	
	public void loadDefault() {

	}
	
	@Override
	public void preInit() {
//		FileChecker.createStreams();
		
//		TickClassHandler.initTickHandler(this);
		
		this.setBatch(new SpriteBatch());
		this.setFont(new BitmapFont());
		
		OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        this.setCamera(camera);
        
		try{
			
			FileHandle fileHandle = Gdx.files.internal("resources/chars.png");
			{
				String[] letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
						"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
						"V", "W", "X", "Y", "Z", ":", "!", "?", ".", "[", "]", "0",
						"1", "2", "3", "4", "5", "6", "7", "8", "9", "(", ")", "+",
						"-", "/", " ", "_","," };
				
				

				Texture tex = new Texture(fileHandle);
				TextureRegion[][]  tmp = TextureRegion.split(tex, tex.getWidth()/8, tex.getHeight()/8);
		        int index = 0;
		        for (int i = 0; i < 8; i++) {
		            for (int j = 0; j < 8; j++) {
		            	getImageHandler().addImage("char_" + letter[index], tmp[i][j]);
		            	getImageHandler().addImage("small_" + letter[index], tmp[i][j]);
		                index++;
		                if(index >= letter.length)
		                	break;
		            }
	                if(index >= letter.length)
	                	break;
		        }
		        
			}
			
			
			String fileNames[] = {"resources/tile.png","resources/player.png","resources/empty.png"};
			for(String fileName:fileNames) {			
				fileHandle = Gdx.files.internal(fileName);
				String name = fileHandle.nameWithoutExtension();
				Texture texture = new Texture(fileHandle);
				if(texture.getWidth() <= Config.tileSize && texture.getHeight() <= Config.tileSize 
							|| name.contains("bg") || name.contains("player") || name.contains("empty"))
						getImageHandler().addImage(name, new TextureRegion(texture));
					else {
						int splitterX = texture.getWidth()/Config.tileSize;
						int splitterY = texture.getHeight()/Config.tileSize;
						TextureRegion[][]  tmp = TextureRegion.split(texture, texture.getWidth()/splitterX, texture.getHeight()/splitterY);
				        int index = 0;
				        for (int i = 0; i < tmp.length; i++) {
				            for (int j = 0; j < tmp[i].length; j++) {
				            	getImageHandler().addImage(name+"_"+index, tmp[i][j]);
				                index++;
				            }
				        }
					}
			}
			
						
				
			
		
			


			//Animation loader
			
			JsonHandler handler = new JsonHandler();
		
				try {
					JsonValue jsobj  = handler.getArrayFromFile(Gdx.files.getFileHandle("scripts/animations.json", FileType.Internal).read());;
					for(JsonValue jobj:jsobj){
						RawAnimation animation = new RawAnimation(jobj);
						this.getAnimationHandler().addAnimation(jobj.getString("name"), animation);
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			
		

		}catch(Exception e){e.printStackTrace();}
		

		
		
		String fileNames[] = {};
		for(String fileName:fileNames) {			
			FileHandle fileHandle = Gdx.files.internal(fileName);
			getSound().addSound(fileHandle.nameWithoutExtension(),fileHandle,false);
		}
//		getSound().addSound("select", StartClass.getURL("resources/select.wav"),false);
//		getSound().addSound("bg_1",  StartClass.getURL("resources/bg_1.wav"),true);
		
//		this.getSound().playClip("select");
	
		
	}


	public static String[] splitNonRegex(String input, String delim)
		{
    List<String> l = new ArrayList<String>();
    int offset = 0;

    while (true)
    	{
	        int index = input.indexOf(delim, offset);
	        if (index == -1)
	        {
	            l.add(input.substring(offset));
	            return l.toArray(new String[l.size()]);
	        } else
	        {
	            l.add(input.substring(offset, index));
	            offset = (index + delim.length());
	        }
    	}
	}

	
	public static String replaceAll(String in, String ths, String that) {
	    StringBuilder sb = new StringBuilder(in);
	    int idx = sb.indexOf(ths); 
	    
	    while (idx > -1) {
	        sb.replace(idx, idx + ths.length(), that);
	        idx = sb.indexOf(ths);

	    }
	    
	    return sb.toString();

	}

	@Override
	public void Init() {
		setWorld(WorldLoader.loadWorld(this,Gdx.files.internal("resources/map.csv")));
	}
	
	World currentWorld = null;
	public void setWorld(World w) {
		currentWorld = w;
	}
	public World getWorld() {
		return currentWorld;
	}

	@Override
	public void postInit() {

	}


	@Override
	public void initStartScreen() {
//		this.setScreen(TickClassHandler.handler.getTick(this, 0));
//	    Gdx.input.setInputProcessor(TickClassHandler.handler.getTick(this, 0));
		
		this.setScreen(new GameRender(this));
	}

}
