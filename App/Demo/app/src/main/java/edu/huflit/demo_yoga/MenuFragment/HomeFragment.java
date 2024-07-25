package com.android.shopdt.MenuFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import com.android.shopdt.R;
import com.android.shopdt.VideoListActivity;

public class HomeFragment extends Fragment {



    private StorageReference mStorageReference;
    private StorageReference mStorageReferenceA;
    private StorageReference mStorageReferenceB;
    private StorageReference mStorageReferenceC;
    private StorageReference mStorageReferenceD;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    CardView mCVBasics,mCVExpert;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // OPEN BASIC VIDEO LIST VIEW
        mCVBasics = view.findViewById(R.id.CVBasics);
        mCVBasics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBasics = new Intent(getActivity(), VideoListActivity.class);
                startActivity(intentBasics);
            }
        });
        //Home banner 1, bitmap retrieved from firebase storage
        mStorageReferenceA = FirebaseStorage.getInstance().getReference().child("home_fragment/home_banner1.png");

        try {
            final File localFile = File.createTempFile("home_banner1","png");
            mStorageReferenceA.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmapA = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ((ImageView) view.findViewById(R.id.imageViewA)).setImageBitmap(bitmapA);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        mStorageReferenceB = FirebaseStorage.getInstance().getReference().child("home_fragment/home_banner2.png");
        try {
            final File localFile = File.createTempFile("home_banner2","png");
            mStorageReferenceB.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmapB = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ((ImageView) view.findViewById(R.id.imageViewB)).setImageBitmap(bitmapB);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mStorageReferenceC = FirebaseStorage.getInstance().getReference().child("home_fragment/home_banner3.png");
        try {
            final File localFile = File.createTempFile("home_banner3","png");
            mStorageReferenceC.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmapC = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ((ImageView) view.findViewById(R.id.imageViewC)).setImageBitmap(bitmapC);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mStorageReferenceD = FirebaseStorage.getInstance().getReference().child("home_fragment/home_banner4.png");
        try {
            final File localFile = File.createTempFile("home_banner4","png");
            mStorageReferenceD.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmapD = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ((ImageView) view.findViewById(R.id.imageViewD)).setImageBitmap(bitmapD);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        mStorageReference = FirebaseStorage.getInstance().getReference().child("home_fragment/yoga_home_banner.png");
        try {
            final File localFile = File.createTempFile("yoga_home_banner","png");
            mStorageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ((ImageView) view.findViewById(R.id.imageView)).setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}