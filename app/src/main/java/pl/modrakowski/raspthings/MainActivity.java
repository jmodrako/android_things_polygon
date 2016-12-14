package pl.modrakowski.raspthings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pl.modrakowski.raspthings.polygon.BlinkLedCase;
import pl.modrakowski.raspthings.polygon.Case;

public class MainActivity extends AppCompatActivity {

	private Case caseObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		caseObject = new BlinkLedCase();
		caseObject.startCase();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		caseObject.stopCase();
	}
}
