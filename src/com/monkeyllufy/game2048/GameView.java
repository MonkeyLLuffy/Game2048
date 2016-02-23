package com.monkeyllufy.game2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {

	private static String TAG = "GameView";
	private Card[][] cardsMap = new Card[4][4];
	private List<Point> emptyPoint = new ArrayList<Point>();
	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initGameView();
	}

	private void initGameView() {
		setColumnCount(4);
		setBackgroundColor(0xffbbada0);
		setOnTouchListener(new OnTouchListener() {
			private float startX, startY, offsetX, offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX()-startX;
					offsetY = event.getY()-startY;
					if (Math.abs(offsetX) > Math.abs(offsetY)) {//说明是水平方向的意图,X方向上距离更大
						if (offsetX <-5) {
							Log.i(TAG, "left");
							swipeleft();
						}else if (offsetX > 5) {
							Log.i(TAG, "right");
							swiperight();
						}
					}else {
						if (offsetY<-5) {
							Log.i(TAG, "up");
							swipeup();
						}else if (offsetY>5) {
							Log.i(TAG, "down");
							swipedown();
						}
					}
					
					break;

				}

				return true;
			}
		});

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		int cardWidth = (Math.min(w, h)-10)/4;
		addCards(cardWidth,cardWidth);
		startGame();
		
	}
	private void addCards(int cardWidth, int cardHeight) {
		// TODO Auto-generated method stub
		Card card;
		for (int y = 0; y < 4; y++) {
			for(int x = 0;x < 4;x++){
				card = new Card(getContext());
				card.setNum(0);
				addView(card,cardWidth,cardHeight);
				cardsMap[x][y] = card;
			}
		}
		
	}
	
	private void startGame() {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardsMap[x][y].setNum(0);

			}
		}
		addRandomNum();
		addRandomNum();

	}
	
	/**
	 * 添加随机数
	 */
	private void addRandomNum() {
		emptyPoint.clear();
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardsMap[x][y].getNum() <= 0) {
					emptyPoint.add(new Point(x, y));
				}
			}
		}
		
		Point point = emptyPoint.remove((int)(Math.random()*emptyPoint.size()));//在没有数字的位置随机的选择位置
		cardsMap[point.x][point.y].setNum(Math.random()>0.1?2:4);//2:4-->9:1，随机的选择数字
	}

	private void swipeleft() {
		boolean merge = false;
		//一行一行的遍历
		for (int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
				//从当前位置往右进行遍历
				for (int x1 = x+1; x1 < 4; x1++) {
					if (cardsMap[x1][y].getNum()>0) {//交换
						
						if (cardsMap[x][y].getNum()<=0) {
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);
							
							x--;
							merge = true;
						}else if(cardsMap[x][y].equals(cardsMap[x1][y])){//合并
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
							cardsMap[x1][y].setNum(0);
							
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
							
						}
						break;
						
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkCompelete();
		}
	}

	private void swiperight() {
		boolean merge = false;
		//一行一行的遍历
				for (int y = 0; y < 4; y++) {
					for(int x = 3; x >= 0; x--) {
						//从当前位置往右进行遍历
						for (int x1 = x-1; x1 >=0 ; x1--) {
							if (cardsMap[x1][y].getNum()>0) {//交换
								
								if (cardsMap[x][y].getNum()<=0) {
									cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
									cardsMap[x1][y].setNum(0);
									
									x++;
									merge = true;
								}else if(cardsMap[x][y].equals(cardsMap[x1][y])){//合并
									cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
									cardsMap[x1][y].setNum(0);

									MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
									merge = true;
								}

								break;
							}
						}
					}
				}
				
				if (merge) {
					addRandomNum();
					checkCompelete();
				}
	}

	private void swipeup() {
		boolean merge = false;
		//一行一行的遍历
		for(int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
						//从当前位置往右进行遍历
						for (int y1 = y+1; y1 < 4 ; y1++) {
							if (cardsMap[x][y1].getNum()>0) {//交换
								
								if (cardsMap[x][y].getNum()<=0) {
									cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
									cardsMap[x][y1].setNum(0);
									
									y--;
									merge = true;
								}else if(cardsMap[x][y].equals(cardsMap[x][y1])){//合并
									cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
									cardsMap[x][y1].setNum(0);

									MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
									merge = true;
								}

								break;
							}
						}
					}
				}
		if (merge) {
			addRandomNum();
			checkCompelete();
		}
	}

	private void swipedown() {
		boolean merge = false;
		for(int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {
					//从当前位置往右进行遍历
					for (int y1 = y-1; y1 >= 0; y1--) {
						if (cardsMap[x][y1].getNum()>0) {//交换
							
							if (cardsMap[x][y].getNum()<=0) {
								cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
								cardsMap[x][y1].setNum(0);
								
								y++;
								merge = true;
							}else if(cardsMap[x][y].equals(cardsMap[x][y1])){//合并
								cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
								cardsMap[x][y1].setNum(0);

								MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
								merge = true;
							}

							break;
						}
					}
				}
			}
		if (merge) {
			addRandomNum();
			checkCompelete();
		}
	}
	
	public void checkCompelete() {
		boolean compelete = true;
		ALL:
		for(int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				//检测到有空位或者邻位可以合并
				if (cardsMap[x][y].getNum() == 0 //不是空的
						|| (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))//左边
						||(x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))//右边
						||(y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))//上边
						||(y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))) {//下边
					compelete = false;
					break ALL;
					
				}
			}
			
		}
		if (compelete) {
			new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					startGame();
				}

				
			}).show();
		}
	}

}
