/*
 * Author: Fraser Tomison
 * Last Updated: 5/2/13
 * Content:
 * The purpose of this widget is to display an arrow from point A to B
 * Arrow indicates direction of whirlpool, may have different size/colour for speeds
 */


package objects;

import logic.Constants;
import logic.Func;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Arrow {

	private float m_startX, m_startY, m_endX, m_endY;
	private float m_pointer1X, m_pointer2X, m_pointer1Y, m_pointer2Y;
	private boolean visible;
	
	public Arrow(float x1, float y1, float x2, float y2){
		init(x1,y1,x2,y2);
	}
	
	public void Reposition(float x1, float y1, float x2, float y2){
		init(x1,y1,x2,y2);
	}
	
	public void draw(Canvas c) {
		if (visible){
			Paint paint = new Paint();
			paint.setStyle(Style.FILL);
	        //background
	        paint.setColor(Color.RED);
	        paint.setStrokeWidth(4 / Constants.getRes().getDisplayMetrics().density);
			c.save();//save canvas state
			
			c.drawLine(m_startX, m_startY, m_endX, m_endY, paint);
			c.drawLine(m_endX, m_endY, m_pointer1X, m_pointer1Y, paint);
			c.drawLine(m_endX, m_endY, m_pointer2X, m_pointer2Y, paint);
	
			c.restore();//restore canvas state
		}
	}
	
	public void setVisible(boolean param){
		visible = param;
	}
	
	public boolean getVisible(){
		return visible;
	}
	
	private void init(float x1, float y1, float x2, float y2){
		m_startX 	= x1;
		m_startY	= y1;
		m_endX		= x2;
		m_endY		= y2;
		
		float dist = (float)Math.sqrt(Math.pow(m_startX-m_endX, 2)+(Math.pow(m_startY-m_endY, 2)));
		float angle = Func.calcAngle(m_startX, m_startY,m_endX, m_endY) - 90;
		
		m_pointer1X = m_endX + (float)Math.sin((angle+20)*Math.PI/180)*(dist/5);
		m_pointer1Y = m_endY - (float)Math.cos((angle+20)*Math.PI/180)*(dist/5);
		
		m_pointer2X = m_endX + (float)Math.sin((angle-20)*Math.PI/180)*(dist/5);
		m_pointer2Y = m_endY - (float)Math.cos((angle-20)*Math.PI/180)*(dist/5);
	}
}
