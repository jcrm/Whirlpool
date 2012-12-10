package states;

import logic.Constants;
import logic.Level;
import logic.MainThread;
import logic.Panel;
import example.whirlpool.R;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Game extends MainActivity {
	Panel _panel;
	private Level level1;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
        _panel = (Panel) findViewById(R.id.gameview);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        level1 = new Level();
        setCurrentLevel(level1);
    	Constants.setState(this);
    	
    	((Button) findViewById(R.id.test1)).setOnClickListener(
    		new Button.OnClickListener(){
		        public void onClick(View v) {
		        	MainThread tempthread = Constants.getThread();
	        		tempthread.onPause();
	        		startActivity(new Intent(Game.this, Menu.class));
		        	
		        }
    		}
    	);
    }
    
	@Override
	public void update() {
		getCurrentLevel().update();
		
	}

	@Override
	public void onDraw(Canvas canvas) {
		getCurrentLevel().onDraw(canvas);
		
	}

	@Override
	public boolean needListener() {
		return true;
	}
    
}