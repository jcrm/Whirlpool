package example.whirlpool;

public class Screen {
	
	public class Point{
		public int x;
		public int y;
		public void set(int x1, int y1){
			x = x1;
			y = y1;
		}
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
		}
	}
	
	private int width;
	private int height;
	private Point centre = new Point();
	
	Screen(){
		//width = 500;
		//height = 500;
		//centre.x = 250;
		//centre.y = 250;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void set(int wid, int hgt){
		width = wid;
		height = hgt;
		centre.set(wid/2, hgt/2);
	}
	
	public int getCentreX(){
		return centre.getX();
	}
	
	public int getCentreY(){
		return centre.getY();
	}
	
}
