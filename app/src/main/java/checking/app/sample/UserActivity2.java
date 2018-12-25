package checking.app.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import checking.app.sample.helper.CustomDialog;
import checking.app.sample.helper.CustomProgressDialog;

public class UserActivity2 extends CommonActivity {
    @BindView(R.id.userLists)
    RecyclerView userLists;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commonlist_activity);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseUtils.getFirebaseAuth();
        databaseReference = FirebaseUtils.getDatabaseReference();
        customProgressDialog = CustomDialog.getInstanse(UserActivity2.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        userLists.setLayoutManager(mLayoutManager);
        userLists.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

    }
}
