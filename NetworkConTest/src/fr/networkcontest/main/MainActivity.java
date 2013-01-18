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

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import fr.networkcontest.http.MyHttpClient;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
	private TextView tw1 ;
	private enum Action {LOAD, DISPLAY} ;
	private Action m_action ;
	private String m_htmlContent ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		m_htmlContent = null ;
		
		bAction = (Button)findViewById(R.id.bAction);
		etUrl = (EditText)findViewById(R.id.etURL) ;
		twLoad = (TextView)findViewById(R.id.twLoad) ;
		tw1 = (TextView)findViewById(R.id.textView1) ;
		
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
					m_htmlContent = new DownloadWebpageText().execute(strUrl).get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				
				m_action = Action.DISPLAY;
				// On remet le bouton (en changeant de texte) et on cache le chargement 
				bAction.setVisibility(0) ;
				bAction.setText(R.string.display_str) ;
				tw1.setText("L'URL :") ;
				twLoad.setText("a été chargé avec succès.") ;
				//twLoad.setVisibility(TextView.GONE) ;
			}
			else if ( m_action == Action.DISPLAY )
			{
				Intent intent = new Intent(this, WebViewActivity.class);
				intent.putExtra("htmlContent", m_htmlContent) ;
				startActivity(intent);
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
		    InputStream content = null;
		    
		    try {
		    	DefaultHttpClient client = new MyHttpClient(getApplicationContext());
		    	HttpResponse response = client.execute(new HttpGet(myurl));
		        content = response.getEntity().getContent();
		        return StreamToString(content) ;
		    } catch (Exception e) {
		        return e.getMessage() ;
		    }
		}
		
		private String StreamToString(InputStream stream) throws IOException
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream)) ;
	        StringBuilder sb = new StringBuilder();
	        String line = null;

	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }

	        stream.close();
	        return sb.toString() ;
		}
		
	}

}
