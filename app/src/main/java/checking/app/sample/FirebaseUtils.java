package checking.app.sample;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseUtils {

    public static FirebaseAuth firebaseAuth;
    public static FirebaseUser firebaseUser;
    public static DatabaseReference databaseReference;

    private Context context;


    public FirebaseUtils(Context mContext) {
        this.context = mContext;
    }

    public static FirebaseAuth getFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth;
    }

    public static FirebaseUser getFirebaseUser() {
        firebaseUser = firebaseAuth.getCurrentUser();
        return firebaseUser;
    }

    public static DatabaseReference getDatabaseReference() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference;
    }

}
