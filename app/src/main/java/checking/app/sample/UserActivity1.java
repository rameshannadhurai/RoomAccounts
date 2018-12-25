package checking.app.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import checking.app.sample.helper.CustomDialog;
import checking.app.sample.helper.CustomProgressDialog;
import checking.app.sample.helper.MyCommon;
import checking.app.sample.models.UserDetails;
import checking.app.sample.models.UserDetailsWithKey;

public class UserActivity1 extends CommonActivity {

    @BindView(R.id.userLists)
    RecyclerView userLists;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private CustomProgressDialog customProgressDialog;
    List<UserDetailsWithKey> userDetailsWithKeys;
    Usersadapters usersadapters;
    ArrayList<String> arrayList;
    int usersCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commonlist_activity);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseUtils.getFirebaseAuth();
        databaseReference = FirebaseUtils.getDatabaseReference();
        customProgressDialog = CustomDialog.getInstanse(UserActivity1.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        userLists.setLayoutManager(mLayoutManager);
        userLists.setItemAnimator(new DefaultItemAnimator());
        userDetailsWithKeys = new ArrayList<>();
        arrayList = new ArrayList<>();
        getUsersLists();

    }


    @OnClick({R.id.confirms})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirms:
              /*  for (UserDetailsWithKey userDetailsWithKey : userDetailsWithKeys) {
                    boolean checks = userDetailsWithKey.isIscheck();
                    if (checks) {
                        userlists.add(userDetailsWithKey.getKey());
                    }
                }*/

                Log.e("UserDetailsWithKey===>", "" + arrayList.size());
                if (arrayList.size() > 0) {
                    int calculate = 200 / arrayList.size();
                    Log.e("UserDetailsWithKey===>", "" + calculate);
                }

                break;


            default:
                break;
        }
    }


    private void getUsersLists() {
        customProgressDialog.show();
        databaseReference.child(MyCommon.USERDETAILS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                customProgressDialog.dismiss();
                userDetailsWithKeys.clear();
                for (DataSnapshot dttSnapshot2 : dataSnapshot.getChildren()) {
                    String keys = dttSnapshot2.getKey();
                    UserDetails details = dttSnapshot2.getValue(UserDetails.class);
                    userDetailsWithKeys.add(new UserDetailsWithKey(keys, details, false));
                }
                usersCount = userDetailsWithKeys.size();
                usersadapters = new Usersadapters(getApplicationContext(), userDetailsWithKeys);
                userLists.setAdapter(usersadapters);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (customProgressDialog.isShowing()) {
                    customProgressDialog.dismiss();
                }
            }
        });

    }


    public class Usersadapters extends RecyclerView.Adapter<Usersadapters.MyViewHolder> {

        Context mContext;
        List<UserDetailsWithKey> servicelists;


        Usersadapters(Context mContext, List<UserDetailsWithKey> types) {
            this.mContext = mContext;
            this.servicelists = types;
        }

        @NonNull
        @Override
        public Usersadapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_details, parent, false);
            return new MyViewHolder(itemview);
        }

        @Override
        public void onBindViewHolder(@NonNull final Usersadapters.MyViewHolder holder, final int position) {
            UserDetails details = servicelists.get(position).getUserDetails();
            holder.names_txt.setText(details.getUsername());
            boolean isckechs = servicelists.get(position).isIscheck();
            if (isckechs) {
                holder.check_item.setChecked(true);
                arrayList.add(servicelists.get(position).getKey());
            } else {
                holder.check_item.setChecked(false);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<String> arrayList = getNames();
                 /*   Log.e("SIZEEEE===>", "d==>" + arrayList.size());
                    if (arrayList.size() > 0) {

                    }*/
                    if (arrayList.size()<usersCount){
                        if (arrayList.size()==0){

                        }else {
                            int sizes = arrayList.size() + 1;
                            int calculate = 200 / sizes;
                            Log.e("UserDetailsWithKey===>", "calculate==>" + calculate);
                        }
                    }
                    boolean names = servicelists.get(position).isIscheck();
                    if (!names) {
                        holder.check_item.setChecked(true);
                        servicelists.get(position).setIscheck(true);
                    } else {
                        holder.check_item.setChecked(false);
                        servicelists.get(position).setIscheck(false);
                    }

                    notifyDataSetChanged();

                }
            });
        }

        public ArrayList<String> getNames() {
            ArrayList<String> userlists = new ArrayList<>();
            for (UserDetailsWithKey userDetailsWithKey : servicelists) {
                if (userDetailsWithKey.isIscheck())
                    userlists.add(userDetailsWithKey.getKey());
            }
            return userlists;
        }

        @Override
        public int getItemCount() {
            return servicelists.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.check_item)
            CheckBox check_item;
            @BindView(R.id.names_txt)
            TextView names_txt;
            boolean isckechs;

            MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

    }
}
