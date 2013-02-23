package logic;

import android.graphics.Rect;

public class Animate{
		int mFrameNum = 0;
		int mFrameWidth = 0;
		int mFrameHeight = 0;
		int mFrameX = 0;
		int mFrameY = 0;
		int mNoOfFrames = 1;
		int mDelay = 3;
		int mCounter = 0;
		private Rect mPortion;
		private static Object mScreenLock;
		
		public Animate(int frames, int width, int height){
			mNoOfFrames = frames;
			mFrameWidth = width/mNoOfFrames ;
			mFrameHeight = height;
			mPortion = new Rect(0, 0, mFrameWidth, mFrameHeight);
			mScreenLock = Constants.getLock();
		}
		
		public void animateFrame(){
			if(mCounter++ >= mDelay){
				mCounter = 0;
				mFrameNum++;
				updatePortion();
			}
		}
		
		public void updatePortion(){
			if(mFrameNum >= mNoOfFrames){		//TODO No clue why I have to -3 from this to make it not show blank frames D:
				mFrameNum = 0;
			}
			//synchronized(screenLock){
				mPortion.left =  mFrameNum * mFrameWidth;
				mPortion.right = getPortion().left + mFrameWidth;
			//}
		}

		public Rect getPortion() {
			return mPortion;
		}
	}
