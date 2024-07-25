package edu.huflit.shopDT.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

import edu.huflit.shopDT.Phones;
import edu.huflit.shopDT.R;

public class CreateOrUpdateItemActivity extends AppCompatActivity {

    boolean isCreate;
    TextView txtCreateOrUpdate;
    Button btnCreateOrUpdateItem, btnSelect, btnUpload;
    ImageView imgItem;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference itemRef;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ImageView btnBack;
    String itemIdForCreate, itemKey, image, imageFromUri;
    ArrayList<String> arrayList;
    Uri uri;
    int PICK_IMG_REQUEST = 1705;
    int RECEIVE_BOOLEAN = 1;
    int PICK_AVATAR_REQUEST = 1111;

    Phones item;
    Boolean uploadCheck = false;
    EditText edtName, edtPrice, edtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_item_activity);

        init();

        firebaseDatabase = FirebaseDatabase.getInstance();
        itemRef = firebaseDatabase.getReference("List_Phone");

        btnBack = findViewById(R.id.backprevious);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        isCreate = getIntent().getExtras().getBoolean("isCreate");

        if (isCreate){
            getNewItemKeyForCreate();
            txtCreateOrUpdate.setText("Tạo sản phẩm mới");
            btnCreateOrUpdateItem.setText("Tạo sản phẩm mới");

        }else {
            itemKey = getIntent().getStringExtra("key");
            getItemForUpdate();
            txtCreateOrUpdate.setText("Cập nhật sản phẩm");
            btnCreateOrUpdateItem.setText("Cập nhật sản phẩm");

        }

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri!=null){
                    // get the Firebase  storage reference
                    firebaseStorage = FirebaseStorage.getInstance();
                    storageReference = firebaseStorage.getReference("Item");

                    String imageName = UUID.randomUUID().toString();
                    StorageReference imageFolder = storageReference.child(imageName);

                    //put img to storage
                    imageFolder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(CreateOrUpdateItemActivity.this,"Upload succeed",Toast.LENGTH_SHORT).show();

                            //get uri img from storage
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageFromUri = uri.toString();
                                    uploadCheck = true;
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            uploadCheck = false;
                            Toast.makeText(CreateOrUpdateItemActivity.this,"Upload failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(CreateOrUpdateItemActivity.this, "Phải chọn hình trước", Toast.LENGTH_SHORT).show();
                }
            }});

        btnCreateOrUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCreate){
                    if (TextUtils.isEmpty(edtName.getText().toString().trim())){
                        edtName.setError("You have to fill this information!");
                        edtName.requestFocus();
                    }
                    else if(TextUtils.isEmpty(edtPrice.getText().toString().trim())){
                        edtPrice.setError("You have to fill this information!");
                        edtPrice.requestFocus();
                    }
                    else if(TextUtils.isEmpty(edtInfo.getText().toString().trim())){
                        edtInfo.setError("You have to fill this information!");
                        edtInfo.requestFocus();
                    }else if (uploadCheck==false){
                        Toast.makeText(CreateOrUpdateItemActivity.this, "Cần phải upload hình ảnh", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String name = edtName.getText().toString().trim();
                        String info = edtInfo.getText().toString().trim();
                        String price = edtPrice.getText().toString().trim();

                        item = new Phones(itemIdForCreate, name, price,info, imageFromUri);
                        Toast.makeText(CreateOrUpdateItemActivity.this, itemIdForCreate, Toast.LENGTH_SHORT).show();
                        //create item
                        itemRef.child(itemIdForCreate).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"Create item succeed",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else {
                    if (TextUtils.isEmpty(edtName.getText().toString().trim())){
                        edtName.setError("You have to fill this information!");
                        edtName.requestFocus();
                    }
                    else if(TextUtils.isEmpty(edtPrice.getText().toString().trim())){
                        edtPrice.setError("You have to fill this information!");
                        edtPrice.requestFocus();
                    }
                    else if(TextUtils.isEmpty(edtInfo.getText().toString().trim())){
                        edtInfo.setError("You have to fill this information!");
                        edtInfo.requestFocus();
                    }
                    else{
                        if (uri==null){
                            String name = edtName.getText().toString().trim();
                            String info = edtInfo.getText().toString().trim();
                            String price = edtPrice.getText().toString().trim();

                            item = new Phones(itemKey, name, price,info, image);
                            //create item
                            itemRef.child(itemKey).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Update item succeed",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            if (uploadCheck==false){
                                Toast.makeText(CreateOrUpdateItemActivity.this, "Cần phải upload hình ảnh", Toast.LENGTH_SHORT).show();
                            }else {
                                String name = edtName.getText().toString().trim();
                                String info = edtInfo.getText().toString().trim();
                                String price = edtPrice.getText().toString().trim();

                                item = new Phones(itemKey, name, price,info, imageFromUri);

                                //create item
                                itemRef.child(itemKey).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext(),"Update item succeed",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
    }

    void init(){
        txtCreateOrUpdate = findViewById(R.id.txtCreateOrUpdate);

        btnCreateOrUpdateItem = findViewById(R.id.btnCreateItem);
        btnSelect = findViewById(R.id.btnSelect);
        btnUpload = findViewById(R.id.btnUpload);

        imgItem = findViewById(R.id.imgItem);

        edtName = findViewById(R.id.edtName);
        edtInfo = findViewById(R.id.edtInfo);
        edtPrice = findViewById(R.id.edtPrice);
    }

    private void getNewItemKeyForCreate() {
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<String>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    arrayList.add(dataSnapshot.getKey());
                }
                //get last itemId in item and create id for new item
                String itemidString = arrayList.get(arrayList.size()-1);
                int itemidInt = Integer.parseInt(itemidString) +1;
                itemIdForCreate = Integer.toString(itemidInt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getItemForUpdate(){
        itemRef.child(itemKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                image = snapshot.child("imgURL").getValue().toString();
                String info = snapshot.child("de").getValue().toString();
                String name = snapshot.child("name").getValue().toString();
                String price = snapshot.child("price").getValue().toString();

                edtName.setText(name);
                edtPrice.setText(price);
                edtInfo.setText(info);
                Picasso.get().load(image).into(imgItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void chooseImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMG_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG_REQUEST && resultCode == Activity.RESULT_OK){
            uri = data.getData();

            if (null != uri) {
//                 update the preview image in the layout
                imgItem.setImageURI(uri);
            }
        }

    }
}