package logic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;

public class Panel extends View{
	private static boolean sGameIsRunning;
    static public Screen sScreen = new Screen();
    static public Resources sRes;
    static MediaPlayer sBackgroundMusic;
    private static Object sScreenLock;
    
    public Panel(Context context) {
        super(context);
        constructor();
    }
    public Panel(Context context, AttributeSet attributes) {
        super(context, attributes);
        constructor();
    }
    private void constructor(){    	
        setFocusable(true);
        Constants.setScreen(sScreen);
        sScreenLock = Constants.getLock();
    }

    @Override
    public void onDraw(Canvas canvas) {
    	synchronized(sScreenLock){
    		if(Constants.getLock()!=null)
    		Constants.getLevel().onDraw(canvas);
    	}
    }

	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
    	int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
    	int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
    	this.setMeasuredDimension(parentWidth, parentHeight);
    	sScreen.set(parentWidth, parentHeight);
    }
    public void init(){
    	//int x = findViewById(R.id.mainview).getWidth();
    	//int y = findViewById(R.id.mainview).getHeight();
    	sRes = getResources();
    	Constants.setRes(sRes);
    	Imports.setImages();
    }
    
    public static void stopMusic(){
    	if(sBackgroundMusic != null){
    		sBackgroundMusic.stop();
    	}
    }
    
    

}
