package fr.networkcontest.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		
		WebView webview = new WebView(this);
		setContentView(webview);

		//String htmlContent = getIntent().getExtras().getString("htmlContent") ;
		String htmlContent = ReadFile("content.html") ;
		webview.loadData(htmlContent, "text/html", "utf-8") ;
		//m_webView.loadDataWithBaseURL("notreal/", htmlContent, "text/htm", "utf-8",null);
	}
	
	public String ReadFile(String filename)
	{
		FileInputStream fIn = null; 
        InputStreamReader isr = null; 
 
        char[] inputBuffer = null;//new char[255]; 
        String data = null; 
 
        try{ 
        	fIn = openFileInput(filename);       
        	isr = new InputStreamReader(fIn);
        	inputBuffer = new char[fIn.available()];
        	isr.read(inputBuffer); 
        	data = new String(inputBuffer); 
        	//affiche le contenu de mon fichier dans un popup surgissant
        	Toast.makeText(getApplicationContext(), "Données Html correctement chargées", Toast.LENGTH_SHORT).show() ;
        } 
        catch (Exception e) {       
        	Toast.makeText(getApplicationContext(), "Données Html non chargées", Toast.LENGTH_SHORT).show() ;
        } 
        finally { 
           try { 
                  isr.close(); 
                  fIn.close(); 
                  } catch (IOException e) { 
                    Toast.makeText(getApplicationContext(), "Données Html non chargées",Toast.LENGTH_SHORT).show(); 
                  } 
        }
            return data; 
       }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_web_view, menu);
		return true;
	}

}
