package game.engine.main;

import game.classes.GameTick;
import game.classes.SpaceTick;
import game.engine.world.World;
import game.worlds.WorldTemplate;

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
import me.pusty.util.PixelLocation;
import me.pusty.util.RawAnimation;
import me.pusty.util.json.JsonHandler;

public class GameClass extends AbstractGameClass {


	

	public GameClass(){
		super();
		
		
	}
	
	
	public int cameraPoint = -1;
	public PixelLocation cameraPointLast = new PixelLocation(0,0);
	
	public int getCameraPoint() {
		return cameraPoint;
	}
	public void setCameraPoint(int i) {
		cameraPoint = i;
	}
	
	public PixelLocation getLastCameraPoint() {
		return cameraPointLast;
	}
	public void setLastCameraPoint(PixelLocation l) {
		cameraPointLast = l;
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
        camera.setToOrtho(false, 64, 64);
        this.setCamera(camera);
        
		try{
			
			FileHandle fileHandle = Gdx.files.internal("resources/chars.png");
			{
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



						Texture tex = new Texture(fileHandle);
						TextureRegion[][]  tmp = TextureRegion.split(tex, tex.getWidth()/10, tex.getHeight()/10);
						int index = 0;
						for (int i = 0; i < tmp.length; i++) {
						    for (int j = 0; j < tmp[i].length; j++) {
						    	getImageHandler().addImage("small_" + smallletters[index], tmp[i][j]);
						    	getImageHandler().addImage("char_" + smallletters[index], tmp[i][j]);
						        index++;
						        if(index >= smallletters.length)
						        	break;
						    }
						    if(index >= smallletters.length)
						    	break;
						}
		        
			}
			
			
			String fileNames[] = {"resources/tileset.png","resources/entities/reaper.png","resources/empty.png","resources/player_bubble.png"};
			for(String fileName:fileNames) {			
				fileHandle = Gdx.files.internal(fileName);
				String name = fileHandle.nameWithoutExtension();
				Texture texture = new Texture(fileHandle);
				if(name.contains("tile")) {
					int splitterX = texture.getWidth()/Config.tileSize;
					int splitterY = texture.getHeight()/Config.tileSize;
					TextureRegion[][]  tmp = TextureRegion.split(texture, texture.getWidth()/splitterX, texture.getHeight()/splitterY);
			        int index = 0;
			        for (int i = 0; i < tmp.length; i++) {
			            for (int j = 0; j < tmp[i].length; j++) {
			            	getImageHandler().addImage("tile_"+index, tmp[i][j]);
			                index++;
			            }
			        }
				}
				else
						getImageHandler().addImage(name, new TextureRegion(texture));
			}
			
			
			String[] fileNames2 = {"resources/entities/bridge.png","resources/entities/door.png"
					,"resources/entities/exclamation.png","resources/entities/goarrow.png","resources/entities/key.png"
					,"resources/entities/lever.png","resources/entities/projectile.png","resources/entities/slime.png"};
			for(String fileName:fileNames2) {	
				fileHandle = Gdx.files.internal(fileName);
				String name = fileHandle.nameWithoutExtension();
				Texture texture = new Texture(fileHandle);
					int splitterX = texture.getWidth()/8;
					TextureRegion[][]  tmp = TextureRegion.split(texture, texture.getWidth()/splitterX, texture.getHeight());
			        int index = 0;
			        for (int i = 0; i < tmp.length; i++) {
			            for (int j = 0; j < tmp[i].length; j++) {
			            	getImageHandler().addImage(name+"_"+index, tmp[i][j]);
			                index++;
			            }
			        }
			       
			}
			
			{
				fileHandle = Gdx.files.internal("resources/entities/player.png");
				String name = fileHandle.nameWithoutExtension();
				Texture texture = new Texture(fileHandle);
					int splitterX = texture.getWidth()/16;
					TextureRegion[][]  tmp = TextureRegion.split(texture, texture.getWidth()/splitterX, texture.getHeight());
			        int index = 0;
			        for (int i = 0; i < tmp.length; i++) {
			            for (int j = 0; j < tmp[i].length; j++) {
			            	getImageHandler().addImage(name+"_"+index, tmp[i][j]);
			                index++;
			            }
			        }
			       
			}
			
	
			{
				fileHandle = Gdx.files.internal("resources/entities/bubbleshooter.png");
				String name = fileHandle.nameWithoutExtension();
				Texture texture = new Texture(fileHandle);
					int splitterX = texture.getWidth()/16;
					TextureRegion[][]  tmp = TextureRegion.split(texture, texture.getWidth()/splitterX, texture.getHeight());
			        int index = 0;
			        for (int i = 0; i < tmp.length; i++) {
			            for (int j = 0; j < tmp[i].length; j++) {
			            	getImageHandler().addImage(name+"_"+index, tmp[i][j]);
			                index++;
			            }
			        }
			       
			}
			
			
			{
				fileHandle = Gdx.files.internal("resources/title.png");
				String name = fileHandle.nameWithoutExtension();
				Texture texture = new Texture(fileHandle);
					int splitterX = texture.getWidth()/64;
					TextureRegion[][]  tmp = TextureRegion.split(texture, texture.getWidth()/splitterX, texture.getHeight());
			        int index = 0;
			        for (int i = 0; i < tmp.length; i++) {
			            for (int j = 0; j < tmp[i].length; j++) {
			            	getImageHandler().addImage(name+"_"+index, tmp[i][j]);
			                index++;
			            }
			        }
			       
			}
			
			
			
			{
				fileHandle = Gdx.files.internal("resources/hud.png");
				String name = fileHandle.nameWithoutExtension();
				Texture texture = new Texture(fileHandle);
					int splitterX = texture.getWidth()/8;
					TextureRegion[][]  tmp = TextureRegion.split(texture, texture.getWidth()/splitterX, texture.getHeight());
			        int index = 0;
			        for (int i = 0; i < tmp.length; i++) {
			            for (int j = 0; j < tmp[i].length; j++) {
			            	getImageHandler().addImage(name+"_"+index, tmp[i][j]);
			                index++;
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
		

		
		
		String fileNames[] = {"resources/sounds/start.wav","resources/sounds/jump.wav","resources/sounds/powerup.wav","resources/sounds/hit.wav",
				"resources/sounds/charge.wav","resources/sounds/down.wav","resources/sounds/ghost.wav","resources/sounds/jump_player.wav","resources/sounds/shot.wav"};
		for(String fileName:fileNames) {			
			FileHandle fileHandle = Gdx.files.internal(fileName);
			getSound().addSound(fileHandle.nameWithoutExtension(),fileHandle,false);
		}
//		getSound().addSound("select", StartClass.getURL("resources/select.wav"),false);
//		getSound().addSound("bg_1",  StartClass.getURL("resources/bg_1.wav"),true);

	
		
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

	WorldTemplate template;
	public WorldTemplate getTemplate() {
		return template;
	}
	public void setTemplate(WorldTemplate temp) {
		template = temp;
	}
	
	@Override
	public void Init() {
		template = WorldTemplate.WORLD1;
		
		template.loadWorld(this);
	}
	
	
	public int cameraTick = 0;
	
	public PixelLocation getCamLocation() {
		PixelLocation goal = getCamPointLocation(getCameraPoint());
		PixelLocation start = getLastCameraPoint();
		int cTick = cameraTick>=0?cameraTick:0;
		int xPos = Math.round((start.getX() + (goal.getX()-start.getX()) * ((50f-cTick)/50)));
		int yPos = Math.round((start.getY() + (goal.getY()-start.getY()) * ((50f-cTick)/50)));
		PixelLocation current = new PixelLocation(xPos,yPos);
		return current;
	}
	public PixelLocation getCamPointLocation(int point) {
		PixelLocation goal = new PixelLocation(0,0);
		if(point>0) {
			goal = template.getCamPointLocation(this, point);
			
		}else if(point==0 || point==-1) {
			PixelLocation location = this.getWorld().getPlayer().getLocation().clone();

			if(location.x <= 32)
				location.setX(32);
			if(location.y <= 32)
				location.setY(32);
			if(location.x >= getWorld().getSizeX()*Config.tileSize-32-8)
				location.setX(getWorld().getSizeX()*Config.tileSize-32-8);
			if(location.y >= getWorld().getSizeY()*Config.tileSize-32-8)
				location.setY(getWorld().getSizeY()*Config.tileSize-32-8);
			
			goal = location.add(new PixelLocation(-26,-26));
		}
		
		return goal;
	}
	int timeOut = -1;
	public void setTimeout(int t) {
		timeOut = t;
	}
	public int getTimeout() {
		return timeOut;
	}
	public void cameraTick() {
		
		if(timeOut > 0)
			timeOut--;
		if(timeOut==0) {
			this.setTimeRunning(true);
			timeOut=-2;
		}else if(timeOut == -2)
			timeOut = -1;
		
		if(getCameraPoint()==-1)return;
		if(getCameraPoint()==0 && (cameraTick <= 0 && cameraTick > -50))
			setTimeRunning(true);
		else
			setTimeRunning(false);
		
		if(cameraTick==0)
			getSound().playClip("powerup",null,null);
		
		if(cameraTick == 0) {
			//Event Here
			cameraTick--;
		}
		
		if(cameraTick > 0)
			cameraTick--;		
		else if(cameraTick > -50) 
			cameraTick--;
		else {
			if(getCameraPoint()>0) {
				cameraTick = 50;
				this.setLastCameraPoint( getCamPointLocation(getCameraPoint()));
				setCameraPoint(0);
			}else {
				cameraTick=0;
				setCameraPoint(-1);
				setTimeRunning(true);
			}
		}
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
		SpaceTick screenTick = new SpaceTick(this);
		this.setScreen(screenTick);
	    Gdx.input.setInputProcessor(screenTick);
	}
	
	public void startGame() {
		GameTick gameTick = new GameTick(this);
		this.setScreen(gameTick);
	    Gdx.input.setInputProcessor(gameTick);
	}

}
