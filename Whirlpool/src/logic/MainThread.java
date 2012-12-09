package logic;

import states.MainActivity;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

class MainThread extends Thread {
	private Object _pauseLock = new Object();  
	private boolean _paused;
    private SurfaceHolder _surfaceHolder;
    private Panel _panel;
    private boolean _run = false;

    public MainThread(Panel panel) {
        _surfaceHolder = panel.getHolder();
        _panel = panel;
        //_panel.init();
    }
    //run game loop not sure where to put this other than this thread
    //sometimes thread can cause program to end suddenly
    @Override
    public void run() {
        Canvas canvas;
        _panel.init();
        MainActivity.getCurrentLevel().init();
        while (_run) {
            canvas = null;
            try {
                canvas = _surfaceHolder.lockCanvas(null);
                synchronized (_surfaceHolder) {
                	if(!_paused){
                		_panel.update();
                		_panel.onDraw(canvas);
                	}
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (canvas != null) {
                    _surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            // This should be after your drawing/update code inside your thread's run() code.
            while (_paused) {
                try {
                synchronized (_pauseLock) {
                    _pauseLock.wait();
                }
                } catch (InterruptedException e) {
                }
            }
        }
    }
    public void setRunning(boolean run) {
        _run = run;
    }
    public SurfaceHolder getSurfaceHolder() {
        return _surfaceHolder;
    }
    // Two methods for your Runnable/Thread class to manage the thread properly.
    public void onPause() {
	synchronized (_pauseLock) {
		_paused = true;
    }
    }

    public void onResume() {
	synchronized (_pauseLock) {
	    _paused = false;
	    _pauseLock.notifyAll();
	}
    }
}
     



