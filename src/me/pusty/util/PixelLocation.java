package me.pusty.util;

public class PixelLocation {
	public int x;
	public int y;
	public PixelLocation(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public PixelLocation sub(PixelLocation l){
		int cx=getX()-l.getX();
		int cy=getY()-l.getY();
		return new PixelLocation(cx,cy);
	}
	
	public static PixelLocation getNorm(PixelLocation v) {
		double distance = getDistance(v,new PixelLocation(0,0));
		int cx=(int) (v.x/distance);
		int cy=(int) (v.y/distance);
		return new PixelLocation(cx,cy);
	}
	
	
	public PixelLocation subToDirection(PixelLocation l){
		int cx=l.getX()-getX();
		int cy=l.getY()-getY();
		return new PixelLocation((int)Math.sin(cx)/2,(int)Math.sin(cy)/2);
	}
	
	public int getX(){return x;}
	public int getY(){return y;}
	public void setX(int x){this.x=x;}
	public void setY(int y){this.y=y;}
	public PixelLocation clone(){return new PixelLocation(x,y);}
    public static double getDistance(PixelLocation l,PixelLocation l2){
    	return Math.sqrt(((l2.getX()-l.getX())*(l2.getX()-l.getX()))+((l2.getY()-l.getY())*(l2.getY()-l.getY())));
    }
    

	public PixelLocation add(PixelLocation a) {
		int cx = x + a.x;
		int cy = y + a.y;
 
		return new PixelLocation(cx,cy);
	}
 

 


	
	public boolean sameAs(PixelLocation loc){
		if(this.x==loc.x && this.y==loc.y)return true;
		return false;
	}
	
	
	
	public String toString(){
		return x+"|"+y;
	}

	public PixelLocation multiply(PixelLocation BlockLocation) {
		int cx = x*BlockLocation.x;
		int cy = y*BlockLocation.y;
		return new PixelLocation(cx,cy);
	}

	public void set(PixelLocation l) {
		this.setX(l.getX());
		this.setY(l.getY());
	}

	
	public PixelLocation redirect() {
		return new PixelLocation(-x,-y);
	}


}
