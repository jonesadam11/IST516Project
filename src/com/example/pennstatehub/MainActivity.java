package com.example.pennstatehub;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.view.View;
import android.net.Uri;

public class MainActivity extends Activity {

	final int RQS_GooglePlayServices = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int resultCode=GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (!(resultCode==ConnectionResult.SUCCESS))
        {
        	GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices);
        }
    }
    
    public void goToWebmail(View view)
    {
    	goToUrl("https://m.webmail.psu.edu/webmail/index.cgi");
    }
    
    public void goToPSU(View view)
    {
    	goToUrl("http://m.psu.edu");
    }
    
    public void goToCatalog(View view)
    {
    	goToUrl("http://m.psu.edu/soc/");
    }
    
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
