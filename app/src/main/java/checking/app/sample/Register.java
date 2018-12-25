package checking.app.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import checking.app.sample.helper.CustomDialog;
import checking.app.sample.helper.CustomProgressDialog;
import checking.app.sample.helper.MyCommon;
import checking.app.sample.models.UserDetails;

public class Register extends CommonActivity {

    @BindView(R.id.edit_user)
    EditText edit_user;
    @BindView(R.id.edit_email)
    EditText edit_email;
    @BindView(R.id.edit_password)
    EditText edit_password;
    String str_emil = "", str_password = "", str_user = "";

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseUtils.getFirebaseAuth();
        customProgressDialog = CustomDialog.getInstanse(this);
    }

    @OnClick({R.id.btn_register, R.id.btn_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String user = edit_user.getText().toString().trim();
                String email = edit_email.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                userRegistrationDetails(email, password, user);
                break;

            case R.id.btn_login:
                break;
            default:
                break;
        }
    }


    public void userRegistrationDetails(final String email, final String password, final String user) {
        customProgressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                customProgressDialog.dismiss();
                if (!task.isSuccessful()) {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "User with this email already exist.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    FirebaseUser firebaseUser = FirebaseUtils.getFirebaseUser();
                    String userUid = firebaseUser.getUid();
                    UserDetails userDetails = new UserDetails(user, email,
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "No");
                    databaseReference = FirebaseUtils.getDatabaseReference();
                    if (firebaseAuth.getCurrentUser() != null) {
                        databaseReference.child(MyCommon.USERDETAILS).child(FirebaseUtils.getFirebaseUser().getUid()).setValue(userDetails);
                    }
                }
            }
        });
    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

    }
}
