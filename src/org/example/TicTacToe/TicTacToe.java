package org.example.TicTacToe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class TicTacToe extends Activity {
	private Game game1;
	private int[] score = new int[3];
	boolean mode = true;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mode = getIntent().getExtras().getBoolean("mode");
		game1 = new Game(this, score, mode);
		setContentView(game1);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.newGame) {
			// Intent intent = getIntent();
			// finish();
			// startActivity(intent);
			startActivity(new Intent(TicTacToe.this, StartUp.class));
			// game1 = new Game(this, score, mode);
			// setContentView(game1);
		} else if (item.getItemId() == R.id.options) {
			setContentView(R.layout.options);
			final Spinner s1 = (Spinner) findViewById(R.id.spinner1);
			final Spinner s2 = (Spinner) findViewById(R.id.spinner2);
			final Spinner s3 = (Spinner) findViewById(R.id.spinner3);
			final Spinner s4 = (Spinner) findViewById(R.id.spinner4);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter
					.createFromResource(this, R.array.colors,
							android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			s1.setAdapter(adapter);
			s2.setAdapter(adapter);
			s3.setAdapter(adapter);
			s4.setAdapter(adapter);
			Button cancel = (Button) findViewById(R.id.cancel);
			cancel.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					setContentView(game1);
				}
			});

			Button done = (Button) findViewById(R.id.done);
			done.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					setContentView(game1);
					int background = s1.getSelectedItemPosition();
					int line = s2.getSelectedItemPosition();
					int xmarker = s3.getSelectedItemPosition();
					int omarker = s4.getSelectedItemPosition();
					game1.change_colors(background, line, xmarker, omarker);
				}
			});

		} else if (item.getItemId() == R.id.help) {
			setContentView(R.layout.help);
			Button back = (Button) findViewById(R.id.goBack);
			back.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					setContentView(game1);
				}
			});
		} else if (item.getItemId() == R.id.clearScores) {
			game1.clear_scores();
			setContentView(game1);
		}
		// Toast.makeText(this, "Just a test", Toast.LENGTH_SHORT).show();
		return true;
	}
}