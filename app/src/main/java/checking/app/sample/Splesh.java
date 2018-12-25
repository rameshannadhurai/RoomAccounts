package checking.app.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import checking.app.sample.helper.CustomDialog;
import checking.app.sample.helper.CustomProgressDialog;
import checking.app.sample.helper.MyCommon;
import checking.app.sample.helper.SharedHelper;
import checking.app.sample.helper.Utility;
import checking.app.sample.pushnotification.Config;


public class Splesh extends CommonActivity {

    Thread splashTread;

    String deviceIDs = "";
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private CustomProgressDialog customProgressDialog;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseUtils.getFirebaseAuth();
        databaseReference = FirebaseUtils.getDatabaseReference();
        customProgressDialog = CustomDialog.getInstanse(this);
    }


    public void CheckNet() {
        if (isOnline()) {
            Log.d("android_ossssssss==>", "Works Fine");
            StartAnimations();
        } else {
            Log.d("android_ossssssss", "Alerts Work");
            ShowCheckAlert();
        }
    }


    private void StartAnimations() {
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 2000) {
                        sleep(100);
                        waited += 100;
                    }
                    if (firebaseAuth.getCurrentUser() != null) {
                        String email_str = FirebaseUtils.getFirebaseUser().getEmail();
                        Intent intent = new Intent(Splesh.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(Splesh.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }


                } catch (InterruptedException ignored) {
                } finally {
                    Splesh.this.finish();
                }
            }
        };
        splashTread.start();
    }

    private String getAndroidVersion(int sdk) {
        switch (sdk) {
            case 1:
                return "Android 1.0";
            case 2:
                return "Petit Four (Android 1.1)";
            case 3:
                return "Cupcake (Android 1.5)";
            case 4:
                return "Donut (Android 1.6)";
            case 5:
                return "Eclair (Android 2.0)";
            case 6:
                return "Eclair (Android 2.0.1)";
            case 7:
                return "Eclair (Android 2.1)";
            case 8:
                return "Froyo (Android 2.2)";
            case 9:
                return "Gingerbread (Android 2.3)";
            case 10:
                return "Gingerbread (Android 2.3.3)";
            case 11:
                return "Honeycomb (Android 3.0)";
            case 12:
                return "Honeycomb (Android 3.1)";
            case 13:
                return "Honeycomb (Android 3.2)";
            case 14:
                return "Ice Cream Sandwich (Android 4.0)";
            case 15:
                return "Ice Cream Sandwich (Android 4.0.3)";
            case 16:
                return "Jelly Bean (Android 4.1)";
            case 17:
                return "Jelly Bean (Android 4.2)";
            case 18:
                return "Jelly Bean (Android 4.3)";
            case 19:
                return "KitKat (Android 4.4)";
            case 20:
                return "KitKat Watch (Android 4.4)";
            case 21:
                return "Lollipop (Android 5.0)";
            case 22:
                return "Lollipop (Android 5.1)";
            case 23:
                return "Marshmallow (Android 6.0)";
            case 24:
                return "Nougat (Android 7.0)";
            case 25:
                return "Nougat (Android 7.1)";
            case 26:
                return "Oreo (Android 8)";
            case 27:
                return "Oreo (Android 8.1)";
            default:
                return "";
        }
    }

    private void ShowCheckAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Splesh.this);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setMessage("No NetWork Connection Please Connect Your Internet");
        builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                CheckNet();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        MyCommon.DEVICETOCKEN = pref.getString("regId", null);
        SharedHelper.putKey(getApplicationContext(), "DEVICEKY", MyCommon.DEVICETOCKEN);
        Log.e("KEYYYYYYYYYYYYYYYY==>", "Firebase reg id: " + MyCommon.DEVICETOCKEN);
    }

    @Override
    public void networkAvailable() {
        displayFirebaseRegId();
        CheckNet();
    }

    @Override
    public void networkUnavailable() {
        Utility.tostshow(getApplicationContext(), "No NetWork Connection Please Connect Your Internet");
    }
}
