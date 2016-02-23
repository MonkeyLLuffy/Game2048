package com.monkeyllufy.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @author liuqun
 * ø®∆¨¿‡
 *
 */
public class Card extends FrameLayout {

	int num = 0;
	private TextView lable;
	public Card(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		lable = new TextView(getContext());
		lable.setTextSize(32);
		lable.setBackgroundColor(0x33ffffff);
		lable.setGravity(Gravity.CENTER);
		LayoutParams lp = new LayoutParams(-1,-1);//ÃÓ≥‰ùM
		lp.setMargins(10,10,0,0);
		addView(lable,lp);
		setNum(0);
	}
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		if (num == 0) {
			lable.setText("");
		}else {
			lable.setText(num + "");
		}
	}
	
	public boolean equals(Card o) {
		// TODO Auto-generated method stub
		return getNum() == o.getNum();
	}
	
	
	

}
