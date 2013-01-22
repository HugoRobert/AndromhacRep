package fr.networkcontest.http;

import java.io.InputStream;
import java.security.KeyStore;

//import javax.net.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import fr.networkcontest.main.R;

import android.content.Context;

public class MyHttpClient extends DefaultHttpClient
{
	final Context context;
	
	public MyHttpClient(Context context) 
	{
		this.context = context;
	}

	 @Override
	 protected ClientConnectionManager createClientConnectionManager() 
	 {
		 SchemeRegistry registry = new SchemeRegistry();
		 //Pour les requ�tes HTTP, on laisse la classe de base s'en occuper.
		 registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		 // Les requ�tes HTTPS se font sur le port 443. A chaque connexion HTTPS, c�est notre keystore qui sera utilis�.
		 registry.register(new Scheme("https", newSslSocketFactory(), 443));
		 return new SingleClientConnManager(getParams(), registry);
	 }

	 private SSLSocketFactory newSslSocketFactory() 
	 {
		 try {
			 // On obtient une instance de notre KeyStore
			 KeyStore trusted = null;/*KeyStore.getInstance("BKS");
			 InputStream in = context.getResources().openRawResource(R.raw.mykestore);
			 try {
				 // Initialisation de notre keystore. On entre le mot de passe (storepass)
				 trusted.load(in, "HugoRobert".toCharArray());
			 } finally {
				 in.close();
			 }*/

			 // Passons le keystore au SSLSocketFactory qui est responsable de la verification du certificat
			 SSLSocketFactory sf = new SSLSocketFactory(trusted);
			 sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);//STRICT_HOSTNAME_VERIFIER);
			 return sf;
		 } catch (Exception e) {
			 throw new AssertionError(e);
		 }
	 }
}
