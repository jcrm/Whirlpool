package logic;

import android.graphics.Rect;

public class Animate{
		int frameNum = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int frameX = 0;
		int frameY = 0;
		int noOfFrames = 1;
		int delay = 10;
		int counter = 0;
		private Rect portion;
		
		public Animate(int frames, int width, int height){
			noOfFrames = frames;
			frameWidth = width;
			frameHeight = height;
			portion = new Rect(0, 0, width, height);
		}
		
		public void animateFrame(){
			if(counter++ >= delay){
				counter = 0;
				frameNum++;
				updatePortion();
			}
		}
		
		public void updatePortion(){
			if(frameNum >= (noOfFrames - 3)){		//TODO No clue why I have to -3 from this to make it not show blank frames D:
				frameNum = 0;
			}
			getPortion().left =  frameNum * frameWidth;
			getPortion().right = getPortion().left + frameWidth;
		}

		public Rect getPortion() {
			return portion;
		}
		
	}