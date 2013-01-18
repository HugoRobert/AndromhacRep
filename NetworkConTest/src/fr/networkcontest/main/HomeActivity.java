package fr.networkcontest.main;

import fr.networkcontest.https.HttpsManager;
import fr.networkcontest.main.R;
import fr.networkcontest.main.MainActivity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity implements android.view.View.OnClickListener
{
	
	private Button bTester ;
	//HttpsManager m_httpsMgr ;

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
			// Si la connexion est possible, on passe à la saisie de l'URL
			ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE) ;
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo() ;
			//if ( m_httpsMgr.isConnected() )
			if ( networkInfo != null && networkInfo.isConnected() )
			{
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			}
			// Sinon, message d'erreurs
			else
			{
				// création du message d'erreur
				AlertDialog.Builder builder = new AlertDialog.Builder(this) ; 
				builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   // On revient à l'activity
			               dialog.cancel() ;
			           }
			       })
			       .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int id) {
			            	// On lance l'application paramètre pour activer la connectivité
			            	Intent intent = new Intent(android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS) ;
			            	startActivity(intent);
			            }
			       });
				
				builder.setMessage("Non connecté.\nVoulez-vous modifier vos configurations ?") ;
				AlertDialog dialog = builder.create() ;
				dialog.show() ;
			}
		}
	}

}
