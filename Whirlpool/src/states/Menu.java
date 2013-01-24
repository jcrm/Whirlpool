package states;

import logic.Constants;
import logic.Level;
import logic.MainThread;
import logic.Panel;
import example.whirlpool.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu extends MainActivity {
	ImageButton game;
	
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Constants.clearLevel();
        Constants.setState(this);
        
        game = ((ImageButton) findViewById(R.id.game));
        
        game.setOnClickListener(
    		new Button.OnClickListener(){
		        public void onClick(View v) {
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