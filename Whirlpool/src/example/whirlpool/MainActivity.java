package example.whirlpool;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
/*
 * Here is code  so far if need any explaining just let me know.
 * I've left in whirlpool code even though it cause the app to crash.
 * I have commented it out. 
 * You can delete it and the Whirlpool.java file until i've been able to fix it
 * 
 */
public class MainActivity extends Activity {
	Panel _panel;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new Panel(this));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        _panel = (Panel) findViewById(R.id.mainview);
    }

    
}