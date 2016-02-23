package com.monkeyllufy.game2048;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv_score;
	private int score = 0;
	public MainActivity() {
		// TODO Auto-generated constructor stub
		mainActivity = this;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv_score = (TextView) findViewById(R.id.tv_score);
		
		
	}
	private static MainActivity mainActivity = null;
	
	public static MainActivity getMainActivity() {
		return mainActivity;
	}
	public void clearScore() {
		score = 0;
	}
	public void showScore() {
		tv_score.setText(score+"");
	}
	public void  addScore( int s) {
		score += s;
		showScore();
	}

}
