package game.engine.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

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

public class GameClass extends AbstractGameClass {


	

	public GameClass(){
		super();
		
		
	}
	
	
	public void loadDefault() {

	}
	
	@Override
	public void preInit() {
		FileChecker.createStreams();
		
		TickClassHandler.initTickHandler(this);
		
		this.setBatch(new SpriteBatch());
		this.setFont(new BitmapFont());
		
		OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.setCamera(camera);
        
		try{
			
			for(Entry<String,FileHandle> entry:FileChecker.checkFolderToHashMap("", "png").entrySet()) {
				String name = FileChecker.splitNonRegex(entry.getKey(),".")[0];
				FileHandle file = entry.getValue();
				if(name.equalsIgnoreCase("letters")) {
					String[] letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
							"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
							"V", "W", "X", "Y", "Z", ":", "!", "?", ".", "[", "]", "0",
							"1", "2", "3", "4", "5", "6", "7", "8", "9", "(", ")", "+",
							"-", "/", " ", "_","," };
					
					

					Texture tex = new Texture(file);
					TextureRegion[][]  tmp = TextureRegion.split(tex, tex.getWidth()/8, tex.getHeight()/8);
			        int index = 0;
			        for (int i = 0; i < 8; i++) {
			            for (int j = 0; j < 8; j++) {
			            	getImageHandler().addImage("char_" + letter[index], tmp[i][j]);
			                index++;
			                if(index >= letter.length)
			                	break;
			            }
		                if(index >= letter.length)
		                	break;
			        }
			        
			     
				}else if(name.equalsIgnoreCase("chars")) {
					
					char[] smallletters = { ' ', 'A','B','C','D','E','F','G','H','I',
											'J', 'K','L','M','N','O','P','Q','R','S',
											'T', 'U','V','W','X','Y','Z','a','b','c',
											'd', 'e','f','g','h','i','j','k','l','m',
											'n', 'o','p','q','r','s','t','u','v','w',
											'x', 'y','z','0','1','2','3','4','5','6',
											'7', '8','9','!','"','%','&','/','(',')',
											'=', '?','[',']','{','}','\\','|','<','>',
											'*', '+','~',"'".toCharArray()[0],'#','-','_','.',':',',',
											';'};
					
					
					
					Texture tex = new Texture(file);
					TextureRegion[][]  tmp = TextureRegion.split(tex, tex.getWidth()/10, tex.getHeight()/10);
			        int index = 0;
			        for (int i = 0; i < tmp.length; i++) {
			            for (int j = 0; j < tmp[i].length; j++) {
			            	getImageHandler().addImage("small_" + smallletters[index], tmp[i][j]);
			                index++;
			                if(index >= smallletters.length)
			                	break;
			            }
		                if(index >= smallletters.length)
		                	break;
			        }
				}else {
					Texture texture = new Texture(file);
					if(texture.getWidth() <= Options.tileSize && texture.getHeight() <= Options.tileSize 
							|| name.contains("bg"))
						getImageHandler().addImage(name, new TextureRegion(texture));
					else {
						int splitterX = texture.getWidth()/Options.tileSize;
						int splitterY = texture.getHeight()/Options.tileSize;
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
		
		/*
		this.setRender(new Render());
		this.setTick(new TickClass());
		this.addMouse();
		this.addKeyboard();
		engine=this;
		
		Commands.init(this);
//		LevelLoader levelLoader = new LevelLoader();
		DialogFile.loadDialogFile();
		loadWorldsIntoMemory();
//		LevelObject obj = levelLoader.loadFromFile(file,levelID);
		
		getSave().loadFromFile("file.save");
		
		*/
		for(Entry<String,FileHandle> entry:FileChecker.checkFolderToHashMap("", "wav").entrySet()) {
			getSound().addSound(FileChecker.splitNonRegex(entry.getKey(),".")[0],entry.getValue(),false);
		}
//		getSound().addSound("select", StartClass.getURL("resources/select.wav"),false);
//		getSound().addSound("bg_1",  StartClass.getURL("resources/bg_1.wav"),true);
		
//		this.getSound().playClip("select");
	
		
	}
	

	@Override
	public void Init() {
		Zone world = new Zone(this,10,10,3);
		Random random = new Random();
		for(int x=0;x<world.getSizeX();x++)
			for(int z=0;z<world.getSizeZ();z++) {
				if(x > 7)
					world.setBlockID(x, z, 8);
				else if(x == 7)
					world.setBlockID(x, z, 4);
				else if(x == 3)
					world.setBlockID(x, z, 3);
				else
					world.setBlockID(x, z, random.nextInt(10)==0?2:1);
			}
		
		world.setBlockID(4, 5, 16);
		world.setBlockID(5, 5, 17);
		world.setBlockID(4, 4, 18);
		world.setBlockID(5, 4, 19);
//		world.setBlockID(5, 4, 20);
//		world.setBlockID(6, 4, 21);
		world.setPlayer(new Player(0,0));
		world.addEntity(new EntityLeek(2,2));
		this.setWorld(world);
//		load(this.getStartWorld());
	}

	@Override
	public void postInit() {

	}


	@Override
	public void initStartScreen() {
		this.setScreen(TickClassHandler.handler.getTick(this, 0));
	    Gdx.input.setInputProcessor(TickClassHandler.handler.getTick(this, 0));
	}

}
