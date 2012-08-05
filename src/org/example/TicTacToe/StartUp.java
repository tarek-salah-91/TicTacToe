package org.example.TicTacToe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartUp extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_up);
		Button two_player = (Button) findViewById(R.id.two);
		two_player.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent two = new Intent(StartUp.this, TicTacToe.class);
				two.putExtra("mode", true);
				startActivity(two);
			}
		});
		Button single_player = (Button) findViewById(R.id.single);
		single_player.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent single = new Intent(StartUp.this, TicTacToe.class);
				single.putExtra("mode", false);
				startActivity(single);
			}
		});
	}
}
