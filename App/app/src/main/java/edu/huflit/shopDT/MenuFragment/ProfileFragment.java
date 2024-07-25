package edu.huflit.shopDT.MenuFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.huflit.shopDT.Activity.LoginActivity;
import edu.huflit.shopDT.Activity.UpdateProfileActivity;
import edu.huflit.shopDT.R;

public class ProfileFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference profileRef;
    FirebaseAuth firebaseAuth;
    TextView txtName, txtFullName, txtAddress, txtEmail, txtPhoneNumber, btnChange;
    LinearLayout btnLogout;
    String userID;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        profileRef = firebaseDatabase.getReference("User");

        userID = firebaseAuth.getUid();

        txtEmail = view.findViewById(R.id.txtEmail);
        txtFullName = view.findViewById(R.id.txtFullName);
        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        txtAddress = view.findViewById(R.id.txtAddress);

        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
            }
        });

        btnChange = view.findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        profileRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = snapshot.child("email").getValue().toString();
                String fullName = snapshot.child("ten").getValue().toString();
                String gender = snapshot.child("diachi").getValue().toString();
                String phonenumber = snapshot.child("sdt").getValue().toString();

                txtEmail.setText(email);
                txtFullName.setText(fullName);
                txtAddress.setText(gender);
                txtPhoneNumber.setText(phonenumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}