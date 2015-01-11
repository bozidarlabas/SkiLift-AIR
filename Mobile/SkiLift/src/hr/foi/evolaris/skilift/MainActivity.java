package hr.foi.evolaris.skilift;

import hr.foi.evolaris.skilift.expandListView.test;
import hr.foi.evolaris.skilift.utils.Constants;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sonymobile.smartextension.hellonotification.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * This is MainActivity. From this activity we can select smartwatchUi and
 * information
 */

public class MainActivity extends Activity {

	private Button btnSmartwatchUI, btnFilterData, btnOpenInformation;
	private GoogleCloudMessaging gcm;
	private String regid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlayout);
		btnSmartwatchUI = (Button) findViewById(R.id.btnUI);
		btnFilterData = (Button) findViewById(R.id.btnFilter);
		btnOpenInformation = (Button) findViewById(R.id.btnInf);
	}

	// OnClick Events
	public void changeSmartwatchUI(View v) {
		Intent intent = new Intent(this, SmartwatchUserInterface.class);
		startActivity(intent);
	}

	public void filterData(View v) {
		Intent intent = new Intent(this, test.class);
		startActivity(intent);
	}

	public void openInformation(View v) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Ski lift waiting time display on smartwatch");
		alertDialogBuilder.setMessage("The app show an indicator icon for a list of all the roughly ten lifts in the Schladming-Planai ski resort.");
		alertDialogBuilder.setPositiveButton("Back",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		register();
	}

	// Goolge Cloud Messaging Registration
	private void register() {
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
			regid = getRegistrationId(getApplicationContext());

			if (regid.isEmpty()) {
				// button.setEnabled(false);
				new RegisterApp(getApplicationContext(), gcm,
						getAppVersion(getApplicationContext())).execute();
			} else {
				Toast.makeText(getApplicationContext(),
						"Device already Registered", Toast.LENGTH_SHORT).show();
			}
		} else {
			Log.i(Constants.TAG, "No valid Google Play Services APK found.");
		}
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						Constants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(Constants.TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(Constants.PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(Constants.TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(Constants.PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(getApplicationContext());
		if (registeredVersion != currentVersion) {
			Log.i(Constants.TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

}
