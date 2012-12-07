package example.whirlpool;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {
	Panel _panel;
	static private Level _currentLevel;
	private Level level1;
	private Level menuScreen;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //_panel = new Panel(this);
        //setContentView(_panel);
        setContentView(R.layout.activity_main);
        _panel = (Panel) findViewById(R.id.mainview);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        level1 = new Level();
    	menuScreen = new Level();
    	_currentLevel = level1;
    	_panel.subConstructor();
    }

	public static Level getCurrentLevel() {
		return _currentLevel;
	}

	public static void setCurrentLevel(Level level) {
		_currentLevel = level;
	}
    
}