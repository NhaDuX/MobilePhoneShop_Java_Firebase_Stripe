package edu.huflit.shopDT.MenuFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.huflit.shopDT.Adapter.FAVAdapter;
import edu.huflit.shopDT.Phones;
import edu.huflit.shopDT.R;

public class FavoriteFragment extends Fragment {
    BottomNavigationView bnv;
    FAVAdapter favAdapter;
    RecyclerView recyclerView;
    ArrayList<Phones> phoneList;

    FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorite_ac, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadFavPhone();
        recyclerView = view.findViewById(R.id.rvFavorite);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);
    }

    private void loadFavPhone() {
        phoneList = new ArrayList<>();
        Phones phones = new Phones();

        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(firebaseAuth.getUid()).child("Favorites")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        phoneList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            //lay phoneID o day, may cai con lai lay o fav adapter
                            String phoneID = "" + ds.child("phoneID").getValue();

                            phones.setId(phoneID);

                            phoneList.add(phones);
                        }
                        favAdapter = new FAVAdapter(phoneList, getContext());
                        recyclerView.setAdapter(favAdapter);
                        // favAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}