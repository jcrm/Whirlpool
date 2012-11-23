/**																*
*																*
*	changed all non-local variables to the following format 	*
*	_[lower case][upper case words] for example _mainView		*
*	all functions excepts constructors have also been changed	*
*	so that they are camel case for example getSpeed()			*
*																*
*															  **/
package example.whirlpool;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {
	private Panel _mainView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        _mainView = new Panel(this);
        setContentView(_mainView);
    }   
}