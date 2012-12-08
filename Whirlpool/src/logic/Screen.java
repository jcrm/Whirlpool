package logic;

public class Screen {
	public class Point{
		public int _x;
		public int _y;
		public void set(int x1, int y1){
			_x = x1;
			_y = y1;
		}
		public int getX(){
			return _x;
		}
		public int getY(){
			return _y;
		}
	}
	//variables
	private int _width;
	private int _height;
	private Point _centre = new Point();
	
	Screen(){
	}
	//getters and setter for width and height
	public int getWidth(){
		return _width;
	}
	public int getHeight(){
		return _height;
	}
	public void set(int wid, int hgt){
		_width = wid;
		_height = hgt;
		_centre.set(wid/2, hgt/2);
	}
	//getters for x and y components
	public int getCentreX(){
		return _centre.getX();
	}
	public int getCentreY(){
		return _centre.getY();
	}
}
