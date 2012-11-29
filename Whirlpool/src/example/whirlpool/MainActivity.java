package example.whirlpool;

import java.util.ArrayList;
import java.util.Random;

import example.whirlpool.GraphicObject.objtype;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
        _panel = (Panel) findViewById(R.id.mainview);
    }

    
}