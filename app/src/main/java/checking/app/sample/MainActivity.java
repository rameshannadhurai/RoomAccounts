package checking.app.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import checking.app.sample.helper.CustomDialog;
import checking.app.sample.helper.CustomProgressDialog;
import checking.app.sample.helper.MyCommon;
import checking.app.sample.helper.SharedHelper;
import checking.app.sample.pushnotification.Config;


public class MainActivity extends CommonActivity {
    @BindView(R.id.buttons)
    LinearLayout buttons;
    @BindView(R.id.visible_txt)
    TextView visible_txt;
    String currentMail = "", currentUID = "";
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseUtils.getFirebaseAuth();
        databaseReference = FirebaseUtils.getDatabaseReference();
        customProgressDialog = CustomDialog.getInstanse(MainActivity.this);
        currentMail = FirebaseUtils.getFirebaseUser().getEmail();
        currentUID = firebaseAuth.getUid();

        getVisibleandInvisible();
    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startActivity(new Intent(getApplicationContext(), UserActivity1.class));
                break;

            case R.id.btn2:
                startActivity(new Intent(getApplicationContext(), UserActivity2.class));
                break;

            case R.id.btn3:
                startActivity(new Intent(getApplicationContext(), UserActivity3.class));
                break;

            case R.id.btn4:
                startActivity(new Intent(getApplicationContext(), UserActivity1.class));
                break;

            case R.id.btn5:
                startActivity(new Intent(getApplicationContext(), UserActivity1.class));
                break;

            default:
                break;
        }
    }

    private void getVisibleandInvisible() {
       // customProgressDialog.show();
        Log.e("email_str", "" + currentMail + currentUID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              //  customProgressDialog.dismiss();
                String checkAdminresponse = dataSnapshot
                        .child(MyCommon.USERDETAILS)
                        .child(currentUID)
                        .child("adminPermission").getValue(String.class);

                if (checkAdminresponse.equalsIgnoreCase("Yes")) {
                    buttons.setVisibility(View.VISIBLE);
                    visible_txt.setVisibility(View.GONE);
                } else {
                    visible_txt.setVisibility(View.VISIBLE);
                    buttons.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
    }

    @Override
    public void networkUnavailable() {

    }
}
