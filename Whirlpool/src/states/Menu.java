package states;

import example.whirlpool.R;
import logic.Constants;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu extends MainActivity {
    //@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Constants.clearLevel();
        Constants.setState(this);
        ImageButton game = ((ImageButton) findViewById(R.id.game));
        
        System.gc();
        game.setOnClickListener(
    		new Button.OnClickListener(){
		        public void onClick(View view) {
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

    
}
