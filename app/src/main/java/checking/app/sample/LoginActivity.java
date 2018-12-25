package checking.app.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import checking.app.sample.helper.CustomDialog;
import checking.app.sample.helper.CustomProgressDialog;
import checking.app.sample.helper.MyCommon;
import checking.app.sample.helper.SharedHelper;
import checking.app.sample.pushnotification.Config;

public class LoginActivity extends CommonActivity {

    @BindView(R.id.edit_email)
    EditText edit_email;
    @BindView(R.id.edit_password)
    EditText edit_password;
    String str_emil = "", str_password = "", str_user = "";

    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_register)
    Button btn_register;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseUtils.getFirebaseAuth();
        databaseReference = FirebaseUtils.getDatabaseReference();
        customProgressDialog = CustomDialog.getInstanse(this);
    }

    @OnClick({R.id.btn_register, R.id.btn_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                startActivity(new Intent(getApplicationContext(), Register.class));
                break;

            case R.id.btn_login:
                String email = edit_email.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                userLoginFunction(email, password);
                break;
            default:
                break;
        }
    }

    public void userLoginFunction(final String email, final String password) {
        customProgressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                customProgressDialog.dismiss();
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Valid Email and Password", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
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
