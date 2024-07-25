//package edu.huflit.shopDT.MenuFragment;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Paint;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatDelegate;
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.getbase.floatingactionbutton.FloatingActionButton;
//
//import edu.huflit.shopDT.PhoneListActivity;
//import edu.huflit.shopDT.R;
//
//public class HomeFragment extends Fragment {
//    SharedPreferences sharedPreferences;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.home_ac, container, false);
//        //floating button darkmode
//        FloatingActionButton darkmode = view.findViewById(R.id.darkmode);
//        sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = sharedPreferences.edit();
//        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
//        // When user reopens the app
//        // after applying dark/light mode
//        if (isDarkModeOn) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            darkmode.setIconDrawable(getResources().getDrawable(R.drawable.baseline_nights_stay_24));
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            darkmode.setIconDrawable(getResources().getDrawable(R.drawable.baseline_wb_sunny_24));
//        }
//        darkmode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isDarkModeOn) {
//                    //nếu darmode đang bật thì nó sẽ tắt bằng hàm
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    // it will set isDarkModeOn
//                    // boolean to false
//                    editor.putBoolean("isDarkModeOn", false);
//                    editor.apply();
//
//                    // change text of Button
//                    darkmode.setIconDrawable(getResources().getDrawable(R.drawable.baseline_nights_stay_24));
//                } else {
//
//                    // if dark mode is off
//                    // it will turn it on
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//
//                    // it will set isDarkModeOn
//                    // boolean to true
//                    editor.putBoolean("isDarkModeOn", true);
//                    editor.apply();
//
//                    // change text of Button
//                    darkmode.setIconDrawable(getResources().getDrawable(R.drawable.baseline_wb_sunny_24));
//                }
//
//            }
//        });
//        return view;
//    }
//    CardView mCVBasics,mCVExpert,mCVRelax,mCVMediation;
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//
//        // OPEN BASIC VIDEO LIST VIEW
//        mCVBasics = view.findViewById(R.id.CVBasics);
//        mCVBasics.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentBasics = new Intent(getActivity(), PhoneListActivity.class);
//                startActivity(intentBasics);
//            }
//        });
//        //open expert video
//        mCVExpert = view.findViewById(R.id.CVExpert);
//        mCVExpert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentBasics = new Intent(getActivity(), PhoneListActivity.class);
//                startActivity(intentBasics);
//            }
//        });
//
//        mCVRelax = view.findViewById(R.id.cvRelax);
//        mCVRelax.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentBasics = new Intent(getActivity(), PhoneListActivity.class);
//                startActivity(intentBasics);
//            }
//        });
//
//        mCVMediation = view.findViewById(R.id.cvMediation);
//        mCVMediation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentBasics = new Intent(getActivity(), PhoneListActivity.class);
//                startActivity(intentBasics);
//            }
//        });
//        //gach chan
//        TextView textView1 = view.findViewById(R.id.tvNew);
//        textView1.setPaintFlags(textView1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//
//        TextView textView2 = view.findViewById(R.id.tvRcmd);
//        textView2.setPaintFlags(textView1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//        //banner image
//        ImageView home_banner = view.findViewById(R.id.Home_banner);
//        Glide.with(HomeFragment.this)
//                .load("https://firebasestorage.googleapis.com/v0/b/qlyoga.appspot.com/o/home_fragment%2Fyoga_home_banner-transformed.png?alt=media&token=b9fddbe6-2d9d-4038-a83b-42fb700ddc62")
//                .into(home_banner);
//        ImageView home_basic = view.findViewById(R.id.imageViewA);
//        Glide.with(HomeFragment.this)
//                .load("https://firebasestorage.googleapis.com/v0/b/qlyoga.appspot.com/o/home_fragment%2Fhome_banner1.png?alt=media&token=ceb9c591-0253-481a-b0db-f2f2a017ff66")
//                .into(home_basic);
//        ImageView home_expert = view.findViewById(R.id.imageViewB);
//        Glide.with(HomeFragment.this)
//                .load("https://firebasestorage.googleapis.com/v0/b/qlyoga.appspot.com/o/home_fragment%2Fhome_banner2.png?alt=media&token=e864b05a-f14a-4586-b0c1-49671cd09d7b")
//                .into(home_expert);
//        ImageView home_daily = view.findViewById(R.id.imageViewC);
//        Glide.with(HomeFragment.this)
//                .load("https://firebasestorage.googleapis.com/v0/b/qlyoga.appspot.com/o/home_fragment%2Fhome_banner3.png?alt=media&token=812e6bbc-e87b-4439-96f6-f3510a603cbe")
//                .into(home_daily);
//        ImageView home_weekly = view.findViewById(R.id.imageViewD);
//        Glide.with(HomeFragment.this)
//                .load("https://firebasestorage.googleapis.com/v0/b/qlyoga.appspot.com/o/home_fragment%2Fhome_banner4.png?alt=media&token=03a57624-c14a-4623-accc-517cef189f40")
//                .into(home_weekly);
//
//    }
//}