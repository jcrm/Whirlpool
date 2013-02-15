package logic;

import android.graphics.Rect;

public class Animate{
		int _frameNum = 0;
		int _frameWidth = 0;
		int _frameHeight = 0;
		int _frameX = 0;
		int _frameY = 0;
		int _noOfFrames = 1;
		int _delay = 3;
		int _counter = 0;
		private Rect portion;
		private static Object screenLock;
		
		public Animate(int frames, int width, int height){
			_noOfFrames = frames;
			_frameWidth = width/_noOfFrames ;
			_frameHeight = height;
			portion = new Rect(0, 0, _frameWidth, _frameHeight);
			screenLock = Constants.getLock();
		}
		
		public void animateFrame(){
			if(_counter++ >= _delay){
				_counter = 0;
				_frameNum++;
				updatePortion();
			}
		}
		
		public void updatePortion(){
			if(_frameNum >= _noOfFrames){		//TODO No clue why I have to -3 from this to make it not show blank frames D:
				_frameNum = 0;
			}
			//synchronized(screenLock){
				getPortion().left =  _frameNum * _frameWidth;
				getPortion().right = getPortion().left + _frameWidth;
			//}
		}

		public Rect getPortion() {
			return portion;
		}
	}
