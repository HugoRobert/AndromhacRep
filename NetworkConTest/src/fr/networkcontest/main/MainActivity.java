package fr.networkcontest.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import fr.networkcontest.https.HttpsManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements android.view.View.OnClickListener
{
	
	private Button bAction ;
	private EditText etUrl ;
	private TextView twLoad ;
	private enum Action {LOAD, DISPLAY} ;
	Action m_action ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bAction = (Button)findViewById(R.id.bAction);
		etUrl = (EditText)findViewById(R.id.etURL) ;
		twLoad = (TextView)findViewById(R.id.twLoad) ;
		
		twLoad.setVisibility(TextView.GONE) ;
		
		//set l'event onClick des boutons de la vue
		bAction.setOnClickListener(this) ;
		
		m_action = Action.LOAD ;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// Vérification de la vue cliquée
		if(v == bAction) 
		{
			if ( m_action == Action.LOAD )
			{
				// On cache le bouton et on indique le chargement 
				bAction.setVisibility(Button.GONE) ;
				twLoad.setVisibility(0) ;
				
				// On récupère l'URL
				String strUrl = etUrl.getText().toString();
				
				// On essaye d'établir la connection Https
				try {
					String htmlContent = new DownloadWebpageText().execute(strUrl).get();
					String blop = "2" ;
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private class DownloadWebpageText extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... urls) {
			try
			{
				return downloadUrl(urls[0]) ;
			} 
			catch (IOException e) 
			{
				return "Error";
			} catch (NoSuchAlgorithmException e) {
				return "Error";
			} catch (KeyManagementException e) {
				return "Error";
			} catch (KeyStoreException e) {
				return "Error";
			}
		}
		
		
		private String downloadUrl(String myurl) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException 
		{
		    InputStream is = null;
		        
		    try {
		    	KeyStore keyStore = KeyStore.getInstance("PKCS12");//KeyStore.getDefaultType());
		    	TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
		    	tmf.init(keyStore) ;
		    	
		    	SSLContext context = SSLContext.getInstance("TLS");
		    	context.init(null, tmf.getTrustManagers(), null);
		    	
		        URL url = new URL(myurl);
		        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		        conn.setSSLSocketFactory(context.getSocketFactory());
		        is = conn.getInputStream();
		        
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is)) ;
		        StringBuilder sb = new StringBuilder();
		        String line = null;

		        while ((line = reader.readLine()) != null) {
		            sb.append(line);
		        }

		        is.close();
		        return sb.toString() ;
		        
		    // Makes sure that the InputStream is closed after the app is
		    // finished using it.
		    } finally {
		        if (is != null) {
		            is.close();
		        } 
		    }
		}
		
	}

}
