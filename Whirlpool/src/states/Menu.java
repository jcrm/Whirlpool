package states;

import logic.Constants;
import logic.Level;
import logic.MainThread;
import logic.Panel;
import example.whirlpool.R;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Menu extends MainActivity {
	Panel _panel;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        _panel = (Panel) findViewById(R.id.menuview);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Constants.clearLevel();
        Constants.setState(this);
        
        ((Button) findViewById(R.id.test2)).setOnClickListener(
    		new Button.OnClickListener(){
		        public void onClick(View v) {
		        	MainThread tempthread = Constants.getThread();
		        	tempthread.onPause();
	        		startActivity(new Intent(Menu.this, Game.class));
		        	
		        }
    		}
    	);
    }
//	Intent OptionsBackIntent = new Intent(OptionsMenu.this, Menu.class);
//	startActivity(OptionsBackIntent);

	@Override
	public void update() {
		//nothing needed in Menu State
	}

	@Override
	public void onDraw(Canvas canvas) {
		//nothing needed in Menu State
		
	}

	@Override
	public boolean needListener() {
		return false;
	}
    
}