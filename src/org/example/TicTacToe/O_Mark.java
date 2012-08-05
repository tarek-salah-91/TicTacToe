package org.example.TicTacToe;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

public class O_Mark extends Cell {
	Paint p;

	public O_Mark(int x, int y, Paint p) {
		super(x, y, p);
		this.p = p;
	}

	public void draw(Canvas g, Resources res, int x, int y, int w, int h) {
		g.drawText("O", (x * w) + (w / 40), (y * h) + h - (h / 6), p);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof O_Mark) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "O";
	}

}
