package com.example.triviamania;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    Button btnLogOut;
    FirebaseAuth mAuth;
    String userEmail;
    TextView usrNameEt, usrPhoneEt, usrEmailEt, usrScoreEt;
    ImageView imageView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void getUser() {

        if (mAuth.getCurrentUser() != null){
            userEmail = mAuth.getCurrentUser().getEmail();
        }

        DatabaseReference ref = FirebaseDatabase.getInstance("https://traviamania-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");


        Query checkUser = ref.orderByChild("email").equalTo(userEmail);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String userEmail = mAuth.getCurrentUser().getEmail();;
                    UserScoreClass user = snapshot.child(userEmail.split("@")[0].replace('.','_')).getValue(UserScoreClass.class);



                    usrEmailEt.setText(user.getEmail());
                    usrNameEt.setText(user.getName());
                    String score = Float.toString(100*Integer.parseInt(user.getScore())/(float)Integer.parseInt(user.getNoOfQues()));

                    usrScoreEt.setText(score);
                    usrPhoneEt.setText(user.getPhone());

//                    Toast.makeText(getContext(), user.getPhone(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FAILED", "WHAT THE HELL");
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        btnLogOut = v.findViewById(R.id.btnLogOut);
        usrEmailEt = v.findViewById(R.id.userEmail);
        usrNameEt = v.findViewById(R.id.userName);
        usrPhoneEt = v.findViewById(R.id.userPhone);
        usrScoreEt = v.findViewById(R.id.userScore);
        imageView = v.findViewById(R.id.circle_img);

        mAuth = FirebaseAuth.getInstance();

        // Add a user image
//        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);


        if (mAuth.getCurrentUser() != null){
            Log.i("The current user is ready", "onCreateView: ready");
            String EMAIL= mAuth.getCurrentUser().getEmail();
//            Toast.makeText(getContext(), EMAIL, Toast.LENGTH_SHORT).show();
            getUser();
        }

        btnLogOut.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(getActivity(), activity_login.class));
        });
        return v;
    }
}