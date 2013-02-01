package logic;

import android.graphics.Rect;

public class Animate{
		private int _frameNum = 0;
		private int _frameWidth = 0;
		private int _frameHeight = 0;
		private int _noOfFrames = 1;
		private int _delay = 3;
		private int _counter = 0;
		private Rect _portion;
		
		public Animate(int frames, int width, int height){
			_noOfFrames = frames;
			_frameWidth = width/_noOfFrames ;
			_frameHeight = height;
			_portion = new Rect(0, 0, _frameWidth, _frameHeight);
		}
		
		public void animateFrame(){
			if(_counter++ >= _delay){
				_counter = 0;
				_frameNum++;
				updatePortion();
			}
		}
		
		public void updatePortion(){
			if(_frameNum >= (_noOfFrames)){
				_frameNum = 0;
			}
			getPortion().left =  _frameNum * _frameWidth;
			getPortion().right = getPortion().left + _frameWidth;
		}

		public Rect getPortion() {
			return _portion;
		}
		public void setDelay(int d){
			_delay = d;
		}
		public int getDelay(){
			return _delay;
		}
	}