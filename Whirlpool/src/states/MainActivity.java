package states;

import logic.Level;
import android.app.Activity;
import android.os.Bundle;

public abstract class MainActivity extends Activity {
	static protected Level mCurrentLevel;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    
    public MainActivity getState(){
    	return this;
    }

    public abstract void update();
    
    
	
    
}
