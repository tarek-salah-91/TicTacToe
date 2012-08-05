package org.example.TicTacToe;

import android.graphics.Paint;
import android.graphics.Point;
import android.content.res.Resources;
import android.graphics.Canvas;

public abstract class Cell extends Point {
	
	   public Cell(int x, int y,Paint p) {
	        super(x, y);
	    }
	    abstract public void draw(Canvas g,Resources res, int x, int y, int w, int h);
}
