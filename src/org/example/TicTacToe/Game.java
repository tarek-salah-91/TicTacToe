/**
 * 
 */
package org.example.TicTacToe;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * @author DELL
 * 
 */
public class Game extends View {
	private Cell[][] singlesquare = null;
	int x = 3;
	int y = 3;
	private int l;
	private int a;
	int newHight;
	int[] score;
	private boolean turn = false;
	public boolean mode = true;
	private int playerwin = 3;
	private Paint linesPaint;
	private Paint backgroundPaint;
	private Paint xmarkPaint;
	private Paint omarkPint;
	private Paint scorePaint;
	Handler handler = new Handler() {
		// @Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				invalidate();
				break;
			case 1:
				Toast.makeText(getContext(), "O Win!", Toast.LENGTH_LONG)
						.show();
				break;
			case 2:
				Toast.makeText(getContext(), "X Win!", Toast.LENGTH_LONG)
						.show();
				break;
			case 3:
				Toast.makeText(getContext(), "Tie!", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	public int getGameSize() {
		return x;
	}

	public Game(Context context, int[] score, boolean mode) {
		super(context);
		this.mode = mode;
		l = this.getWidth();
		a = this.getHeight();
		linesPaint = new Paint();
		this.linesPaint.setARGB(255, 0, 0, 255);
		this.linesPaint.setAntiAlias(true);
		this.linesPaint.setStyle(Style.STROKE);
		this.linesPaint.setStrokeWidth(5);
		backgroundPaint = new Paint();
		this.backgroundPaint.setARGB(255, 255, 255, 255);
		this.backgroundPaint.setStyle(Style.FILL);
		xmarkPaint = new Paint();
		this.xmarkPaint.setARGB(255, 255, 0, 0);
		this.xmarkPaint.setStyle(Style.FILL);
		omarkPint = new Paint();
		this.omarkPint.setARGB(255, 0, 255, 0);
		this.omarkPint.setStyle(Style.FILL);
		scorePaint = new Paint();
		this.scorePaint.setARGB(255, 255, 255, 255);
		this.scorePaint.setStyle(Style.FILL);
		singlesquare = new Cell[x][y];
		this.score = score;
		int xss = l / x;
		int yss = a / y;
		for (int z = 0; z < y; z++) {
			for (int i = 0; i < x; i++) {
				singlesquare[z][i] = new Empty(xss * i, z * yss,
						backgroundPaint);
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		newHight = (this.getHeight() - (this.getHeight() / 8));
		for (int i = 0; i < singlesquare.length; i++) {
			for (int j = 0; j < singlesquare[0].length; j++) {
				singlesquare[i][j].draw(canvas, getResources(), j, i,
						(this.getWidth() + 3) / singlesquare.length, newHight
								/ singlesquare[0].length);
			}
		}
		int xs = this.getWidth() / x;
		int ys = (this.getHeight() - (this.getHeight() / 8)) / y;
		newHight = (this.getHeight() - (this.getHeight() / 8));
		Log.i("info", (xs) + "");
		this.xmarkPaint.setTextSize((float) (this.getHeight() / 3.5));
		this.omarkPint.setTextSize((float) (this.getHeight() / 3.5));
		for (int i = 0; i <= x; i++) {
			canvas.drawLine(xs * i, 0, xs * i, newHight, linesPaint);
		}
		for (int i = 0; i <= y; i++) {
			canvas.drawLine(0, ys * i, this.getWidth(), ys * i, linesPaint);
		}
		// ===============================================================
		scorePaint.setTextSize(getHeight() / 20);
		int midScore = newHight + (this.getHeight() / 16);
		canvas.drawLine(0, midScore, this.getWidth(), midScore, linesPaint);
		String scoreview = "X wins: " + score[1] + " | " + "O wins: "
				+ score[0] + " | " + "Ties: " + score[2];
		String playerTurn = "";
		if (turn) {
			playerTurn = "Players turn : " + "X";
		} else {
			playerTurn = "Players turn : " + "O";
		}
		canvas.drawText(playerTurn, 0,
				this.getHeight() - this.getHeight() / 14, scorePaint);
		canvas.drawText(scoreview, 0, this.getHeight(), scorePaint);
		// =============================================================
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		newHight = (this.getHeight() - (this.getHeight() / 8));
		if (event.getY() >= 0 && event.getY() <= newHight - 3) {
			int x_aux = (int) (event.getX() / (this.getWidth() / x));
			int y_aux = (int) (event.getY() / (newHight / y));
			try {
				draw_mark(x_aux, y_aux);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.onTouchEvent(event);
	}

	public String getPiece(int player) {
		switch (player) {
		case 1:
			return "x";
		case -1:
			return "o";
		}
		return null;
	}

	public void draw_mark(int x_aux, int y_aux) throws InterruptedException {
		if (!mode) {
			if (singlesquare[y_aux][x_aux] instanceof Empty) {
				Cell cel = null;
				if (!turn) {
					cel = new O_Mark(singlesquare[x_aux][y_aux].x,
							singlesquare[x_aux][y_aux].y, omarkPint);
					turn = true;
				} else {
					cel = new X_Mark(singlesquare[x_aux][y_aux].x,
							singlesquare[x_aux][y_aux].y, xmarkPaint);
					turn = false;
				}
				singlesquare[y_aux][x_aux] = cel;
				handler.sendMessage(Message.obtain(handler, 0));
				if (validate_game()) {
					if (turn) {
						score[0]++;
						Log.i("score", score[0] + "");
						handler.sendMessage(Message.obtain(handler, 1));
					} else {
						score[1]++;
						Log.i("score", score[1] + "");
						handler.sendMessage(Message.obtain(handler, 2));
					}
					
					resizegame(x);

				} else if (isFull()) {
					score[2]++;
					Log.i("score", score[2] + "");
					handler.sendMessage(Message.obtain(handler, 3));
					
					resizegame(x);
				}
				if (turn) {
					int[] a = getComputerCell();
					Log.i("computer player", a[0] + " " + a[1]);
					draw_mark(a[0], a[1]);
				}
			}
		} else {
			if (singlesquare[y_aux][x_aux] instanceof Empty) {
				Cell cel = null;
				if (turn) {
					cel = new X_Mark(singlesquare[x_aux][y_aux].x,
							singlesquare[x_aux][y_aux].y, xmarkPaint);
					turn = false;
				} else {
					cel = new O_Mark(singlesquare[x_aux][y_aux].x,
							singlesquare[x_aux][y_aux].y, omarkPint);
					turn = true;
				}
				singlesquare[y_aux][x_aux] = cel;
				handler.sendMessage(Message.obtain(handler, 0));
				if (validate_game()) {
					if (turn) {
						score[0]++;
						Log.i("score", score[0] + "");
						handler.sendMessage(Message.obtain(handler, 1));
					} else {
						score[1]++;
						Log.i("score", score[1] + "");
						handler.sendMessage(Message.obtain(handler, 2));
					}
					resizegame(x);

				} else if (isFull()) {
					score[2]++;
					Log.i("score", score[2] + "");
					handler.sendMessage(Message.obtain(handler, 3));
					resizegame(x);
				}
			}
		}
	}

	private boolean validate_game() {
		int contador = 0;
		Cell anterior = null;
		for (int i = 0; i < singlesquare.length; i++) {
			for (int j = 0; j < singlesquare[0].length; j++) {
				// System.out.print(singlesquare[i][j]);
				if (!singlesquare[i][j].equals(anterior)
						|| singlesquare[i][j] instanceof Empty) {
					anterior = singlesquare[i][j];
					contador = 0;
				} else {
					contador++;
				}
				if (contador >= getPlayerwin() - 1) {
					return true;
				}
			}
			// System.out.println("");
			anterior = null;
			contador = 0;
		}
		anterior = null;
		for (int j = 0; j < singlesquare[0].length; j++) {
			for (int i = 0; i < singlesquare.length; i++) {
				// System.out.print(singlesquare[i][j]);
				if (!singlesquare[i][j].equals(anterior)
						|| singlesquare[i][j] instanceof Empty) {
					anterior = singlesquare[i][j];
					contador = 0;
				} else {
					contador++;
				}
				if (contador >= getPlayerwin() - 1) {
					return true;
				}
			}
			// System.out.println("");
			anterior = null;
			contador = 0;
		}
		anterior = null;
		for (int j = singlesquare[0].length - 1; j >= 0; j--) {
			int yau = 0;
			for (int z = j; z < singlesquare[0].length; z++) {
				if (!singlesquare[yau][z].equals(anterior)
						|| singlesquare[yau][z] instanceof Empty) {
					anterior = singlesquare[yau][z];
					contador = 0;
				} else {
					contador++;
				}
				if (contador >= getPlayerwin() - 1) {
					return true;
				}
				yau++;
			}
			contador = 0;
			anterior = null;
		}
		anterior = null;
		for (int j = 0; j < singlesquare[0].length; j++) {
			int yau = 0;
			for (int z = j; z >= 0; z--) {
				if (!singlesquare[yau][z].equals(anterior)
						|| singlesquare[yau][z] instanceof Empty) {
					anterior = singlesquare[yau][z];
					contador = 0;
				} else {
					contador++;
				}
				if (contador >= getPlayerwin() - 1) {
					return true;
				}
				yau++;
			}
			contador = 0;
			anterior = null;
		}
		return false;
	}

	public boolean isFull() {
		for (int i = 0; i < singlesquare.length; i++) {
			for (int j = 0; j < singlesquare[0].length; j++) {
				if (singlesquare[i][j] instanceof Empty) {
					return false;
				}
			}
		}
		return true;
	}

	public void resizegame(int s) {
		x = s;
		y = s;
		singlesquare = new Cell[x][y];
		newHight = (this.getHeight() - (this.getHeight() / 8));
		int xss = l / x;
		int yss = newHight / y;
		for (int z = 0; z < y; z++) {
			for (int i = 0; i < x; i++) {
				singlesquare[z][i] = new Empty(xss * i, z * yss,
						backgroundPaint);
			}
		}
		handler.sendMessage(Message.obtain(handler, 0));
	}

	public int getPlayerwin() {
		return playerwin;

	}

	public int determine_color(int color) {
		if (color == 0) {
			return Color.RED;
		} else if (color == 1) {
			return Color.GREEN;
		} else if (color == 2) {
			return Color.BLUE;
		} else if (color == 3) {
			return Color.WHITE;
		} else if (color == 4) {
			return Color.GRAY;
		} else if (color == 5) {
			return Color.YELLOW;
		} else if (color == 6) {
			return Color.MAGENTA;
		} else if (color == 7) {
			return Color.CYAN;
		}
		return 0;
	}

	public void change_colors(int background, int line, int xmarker, int omarker) {
		backgroundPaint.setColor(determine_color(background));
		linesPaint.setColor(determine_color(line));
		xmarkPaint.setColor(determine_color(xmarker));
		omarkPint.setColor(determine_color(omarker));
	}

	public void clear_scores() {
		score = new int[3];
	}

	public int[] getComputerCell() {
		// CASE # 1
		int contador = 0;
		int index = -1;
		for (int j = 0; j < singlesquare[0].length; j++) {
			if (singlesquare[0][j] instanceof O_Mark) {
				contador++;
			}
			if (singlesquare[0][j] instanceof Empty) {
				index = j;
			}
		}
		if (contador == 2 && index > -1) {
			return new int[] { index, 0 };
		}
		// =======================================================
		// CASE # 2
		contador = 0;
		index = -1;
		for (int j = 0; j < singlesquare[0].length; j++) {
			if (singlesquare[1][j] instanceof O_Mark) {
				contador++;
			}
			if (singlesquare[1][j] instanceof Empty) {
				index = j;
			}
		}
		if (contador == 2 && index > -1) {
			return new int[] { index, 1 };
		}
		// ==========================================================
		// CASE # 3
		contador = 0;
		index = -1;
		for (int j = 0; j < singlesquare[0].length; j++) {
			if (singlesquare[2][j] instanceof O_Mark) {
				contador++;
			}
			if (singlesquare[2][j] instanceof Empty) {
				index = j;
			}
		}
		if (contador == 2 && index > -1) {
			return new int[] { index, 2 };
		}
		// =============================================================
		// CASE # 4
		contador = 0;
		index = -1;
		for (int j = 0; j < singlesquare.length; j++) {
			if (singlesquare[j][0] instanceof O_Mark) {
				contador++;
			}
			if (singlesquare[j][0] instanceof Empty) {
				index = j;
			}
		}
		if (contador == 2 && index > -1) {
			return new int[] { 0, index };
		}
		// =============================================================
		// CASE # 5
		contador = 0;
		index = -1;
		for (int j = 0; j < singlesquare.length; j++) {
			if (singlesquare[j][1] instanceof O_Mark) {
				contador++;
			}
			if (singlesquare[j][1] instanceof Empty) {
				index = j;
			}
		}
		if (contador == 2 && index > -1) {
			return new int[] { 1, index };
		}
		// ================================================================
		// CASE # 6
		contador = 0;
		index = -1;
		for (int j = 0; j < singlesquare.length; j++) {
			if (singlesquare[j][2] instanceof O_Mark) {
				contador++;
			}
			if (singlesquare[j][2] instanceof Empty) {
				index = j;
			}
		}
		if (contador == 2 && index > -1) {
			return new int[] { 2, index };
		}
		// ===============================================================
		// CASE # 7
		contador = 0;
		index = -1;
		for (int j = 0; j < singlesquare.length; j++) {
			if (singlesquare[j][j] instanceof O_Mark) {
				contador++;
			}
			if (singlesquare[j][j] instanceof Empty) {
				index = j;
			}
		}
		if (contador == 2 && index > -1) {
			return new int[] { index, index };
		}
		// ================================================================
		// CASE # 8
		contador = 0;
		index = -1;
		for (int j = 0; j < singlesquare.length; j++) {
			if (singlesquare[j][2 - j] instanceof O_Mark) {
				contador++;
			}
			if (singlesquare[j][2 - j] instanceof Empty) {
				index = j;
			}
		}
		if (contador == 2 && index > -1) {
			return new int[] { 2 - index, index };
		}
		// ==================================================================
		// CASE # 9
		Random r = new Random();
		int i = 1;
		int j = 1;
		while (true) {
			if (singlesquare[i][j] instanceof Empty) {
				return new int[] { j, i };
			}
			i = r.nextInt(3);
			j = r.nextInt(3);
		}
	}
}
