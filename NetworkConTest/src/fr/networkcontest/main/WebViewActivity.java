package fr.networkcontest.main;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		
		WebView webview = new WebView(this);
		setContentView(webview);

		String htmlContent = getIntent().getExtras().getString("htmlContent") ;
		webview.loadData(htmlContent, "text/html", "utf-8") ;
		//m_webView.loadDataWithBaseURL("notreal/", htmlContent, "text/htm", "utf-8",null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_web_view, menu);
		return true;
	}

}
