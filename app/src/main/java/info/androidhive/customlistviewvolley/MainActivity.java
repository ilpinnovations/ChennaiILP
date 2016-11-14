package info.androidhive.customlistviewvolley;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.SEND_SMS;

@SuppressLint("DefaultLocale") 
@SuppressWarnings("unused")

public class MainActivity extends Activity implements OnItemSelectedListener {
	
	public static int employeeid;
	public static String name;
	public static String location;
	public static String lg;
	public static String email;	
	public static TextView eventappointmentbook = null;
	public static TextView imeicheck = null;
	ProgressDialog pd;

	// Log tag
	private static final String TAG = MainActivity.class.getSimpleName();


	private static final int PERMISSION_REQUEST_CODE = 200;

    private EditText emailEditText;
    private Spinner spinner2;
    private EditText e1;
    private EditText e2;
    private Spinner  sp;
    private EditText e4;
    private EditText e5;


	private boolean checkPermission() {
		int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
		int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
		int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
		int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
		int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);
		int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);

		return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED;
	}

	private void requestPermission() {

		ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, CAMERA, READ_SMS, CALL_PHONE, READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case PERMISSION_REQUEST_CODE:
				if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access location data and camera.", Toast.LENGTH_SHORT).show();
                        onPermissionFetched();
                    }
					else {

						Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access location data and camera.", Toast.LENGTH_SHORT).show();

						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
							if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
								showMessageOKCancel("You need to allow access to both the permissions",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
													requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, CAMERA, READ_SMS, CALL_PHONE, READ_PHONE_STATE},
															PERMISSION_REQUEST_CODE);
												}
											}
										});
								return;
							}
						}

					}
				}

				break;
		}
	}

	private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
		new AlertDialog.Builder(getApplicationContext())
				.setMessage(message)
				.setPositiveButton("OK", okListener)
				.setNegativeButton("Cancel", null)
				.create()
				.show();
	}

	// Movies json url
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		String defaultValue = getResources().getString(R.string.permission_default_value);

		String checkPermission = sharedPref.getString(getResources().getString(R.string.check_permission_key), defaultValue);

        emailEditText = (EditText)findViewById(R.id.editText5);
        spinner2 = (Spinner) findViewById(R.id.spinner1);
        e1 = (EditText)findViewById(R.id.editText1);
        e2 = (EditText)findViewById(R.id.editText2);
        sp = (Spinner)findViewById(R.id.spinner1);
        e4 = (EditText)findViewById(R.id.editText4);
        e5 = (EditText)findViewById(R.id.editText5);

		if (checkPermission.equalsIgnoreCase("false")){
			requestPermission();
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(getResources().getString(R.string.check_permission_key), "true");
			editor.commit();
		}else {
            onPermissionFetched();
        }

	}

    public void onPermissionFetched(){
        Log.i(TAG, "OnPermissionFetched");
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(this.TELEPHONY_SERVICE);
        String t = telephonyManager.getDeviceId();

        new checkalreadyregistered(this).execute(t);

        emailEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    emailEditText.setHint("@tcs.com");
                }
            }
        });

        // Spinner click listener
        spinner2.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories1 = new ArrayList<String>();
        categories1.add("Chennai");
        categories1.add("Trivandrum");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(R.layout.spinner_rowloki);
        // Drop down layout style - list view with radio button

        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter1);

        listner();
    }

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@SuppressWarnings("static-access")
	public void submit(View v) {
		String text1 = "";
		
		if(e1.getText().toString().length() == 0)
			text1 = "Enter Employee Id";
		else if(e2.getText().toString().length() == 0)
			text1 = "Enter Your Name";
		else if(e4.getText().toString().length() == 0)
			text1 = "Enter LG Name";
	
            if(validEmail(e5.getText().toString().trim())) {
            	String[] temp = e5.getText().toString().trim().split("@");
            	if(!temp[1].trim().toLowerCase().equals("tcs.com"))
            		text1 = "Enter Official Email ID";
            }
            else
            	text1 = "Enter Official Email ID";
            	

		if(text1.length() != 0) {
			final Dialog dialog = new Dialog(MainActivity.this);
			dialog.setContentView(R.layout.customerror);
			dialog.setTitle("");

			 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
			// dialog.getWindow().setTitleColor(R.color.titlecolor);
		       

			// set the custom dialog components - text, image and button
			TextView text = (TextView) dialog.findViewById(R.id.text);
			text.setText(text1);


			Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
		}
		else {
			TelephonyManager telephonyManager = (TelephonyManager)getSystemService(this.TELEPHONY_SERVICE);
			String t = telephonyManager.getDeviceId();

			new serversignin(this).execute(
					e1.getText().toString(),
					e2.getText().toString(),
					sp.getSelectedItem().toString(),
					e4.getText().toString(),
					e5.getText().toString(),
					t);
		}
	}

	 private boolean validEmail(String email) {
		    Pattern pattern = Patterns.EMAIL_ADDRESS;
		    return pattern.matcher(email).matches();
		}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {	
	}
	
	void listner() {
		 eventappointmentbook = new TextView(this);
		 
		 eventappointmentbook.addTextChangedListener(new TextWatcher() {

			   @Override
			   public void afterTextChanged(Editable s) {

				   if(eventappointmentbook.getText().toString().trim().contains("Exception")) {
					 final Dialog dialog = new Dialog(MainActivity.this);
					 dialog.setContentView(R.layout.customerror);
					 dialog.setTitle("");

					 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));

					 // set the custom dialog components - text, image and button
					 TextView text = (TextView) dialog.findViewById(R.id.text);
					 text.setText("No Internet Connection\nPress OK to exit!");

					 Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
					 // if button is clicked, close the custom dialog
						
					 dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
                                Log.i(TAG, "onClick: finish()");
                                finish();
								dialog.dismiss();
							}
						});
						dialog.show();
				 }
					EditText e1 = (EditText)findViewById(R.id.editText1);
					EditText e2 = (EditText)findViewById(R.id.editText2);
					Spinner  sp = (Spinner)findViewById(R.id.spinner1);
					EditText e4 = (EditText)findViewById(R.id.editText4);
					EditText e5 = (EditText)findViewById(R.id.editText5);
					
					employeeid = Integer.parseInt(e1.getText().toString().trim());
					name = e2.getText().toString().trim();
					location = sp.getSelectedItem().toString();
					lg = e4.getText().toString().trim();
					email = e5.getText().toString().trim();

					Intent i = new Intent(getApplicationContext(), list.class);		 		
			 		startActivity(i);
			   }

			   @Override    
			   public void beforeTextChanged(CharSequence s, int start,
			     int count, int after) {
			   }

			   @Override    
			   public void onTextChanged(CharSequence s, int start,
			     int before, int count) {
			     
			   }
			  });
		 	
		 		imeicheck = new TextView(this);

				imeicheck.addTextChangedListener(new TextWatcher() {

			   @Override
			   public void afterTextChanged(Editable s) {

				  if(imeicheck.getText().toString().trim().contains("Exception")){
					  final Dialog dialog = new Dialog(MainActivity.this);
					  dialog.setContentView(R.layout.customerror);
					  dialog.setTitle("");

					  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
					  // dialog.getWindow().setTitleColor(R.color.titlecolor);
					       

					  // set the custom dialog components - text, image and button
					  TextView text = (TextView) dialog.findViewById(R.id.text);
					  text.setText("No Internet Connection\nPress OK to exit!");


					  Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
					  // if button is clicked, close the custom dialog
					
					  dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
                                finish();
							}
						});
						dialog.show();
						return;
				  }

					String data1 = imeicheck.getText().toString().trim();
					String[] data = data1.split("xyzzyspoonshift");
				
					if(data1.length()!=127)
					
					if(!data[0].equals("")) {
					   employeeid = Integer.parseInt(data[1].trim());
					   name = data[2].trim();
					   location = data[3].trim();
					   lg = data[4].trim();
					   email = data[5].trim();
					   
					   Intent i = new Intent(getApplicationContext(), list.class);		 		
					   startActivity(i);
					}			   
			   }

			   @Override    
			   public void beforeTextChanged(CharSequence s, int start,
			     int count, int after) {
			   }

			   @Override    
			   public void onTextChanged(CharSequence s, int start,
			     int before, int count) {
			     
			   }
			  });
	 }
}