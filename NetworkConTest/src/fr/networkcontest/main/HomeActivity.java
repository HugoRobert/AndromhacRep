package fr.networkcontest.main;

import fr.networkcontest.main.R;
import fr.networkcontest.main.MainActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity implements android.view.View.OnClickListener
{
	
	private Button bTester ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		bTester = (Button)findViewById(R.id.bLauchTest);
		
		//set l'event onClick des boutons de la vue
		bTester.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// Vérification de la vue cliquée
		if(v == bTester) 
		{	
			Intent intent = new Intent(this, MainActivity.class);
		   	startActivity(intent);
		}
	}

}
