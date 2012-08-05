package org.example.TicTacToe;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Empty extends Cell {
	Paint p = new Paint();

	public Empty(int x, int y, Paint p) {
		super(x, y, p);
		this.p = p;
	}

	public void draw(Canvas g, Resources res, int x, int y, int w, int h) {
		g.drawRect(new Rect(x * w, y * h, (x * w) + w, (y * h) + h), p);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Empty) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return " ";
	}
}
