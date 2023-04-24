package com.example.maket;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.maket.Convert.DataConvert;
import com.example.maket.DAO.AppDatabase;
import com.example.maket.Entity.Foody;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class DialogEditFood extends AppCompatDialogFragment {
    EditText mEditTextName;
    EditText mEditTextPrice;
    EditText mEditTextReview;
    ImageView mImageView;
    ImageButton mButtonCamera;
    ImageButton mButtonFolder;
    int REQUESTCODE_FOLDER = 777;
    int REQUESTCODE_CAMERA = 999;
    Bitmap bitmapImages = null;
    Button mButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_food, null);
        builder.setTitle("Sửa mặt hàng");
        builder.setView(view);
//        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        mEditTextName = view.findViewById(R.id.edt_namefood_dal);
        mEditTextPrice = view.findViewById(R.id.edt_price_dal);
        mEditTextReview = view.findViewById(R.id.edt_review_dal);
        mImageView = view.findViewById(R.id.imv_add_food_dal);
        mButtonCamera = view.findViewById(R.id.imv_camera_dal);
        mButtonFolder = view.findViewById(R.id.imv_folder_dal);
        mButton=view.findViewById(R.id.btn_edit_dal);
        mButtonFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUESTCODE_FOLDER);
            }
        });
        mButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, REQUESTCODE_CAMERA);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUESTCODE_CAMERA && resultCode == Activity.RESULT_OK && data != null) ;
        {
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mImageView.setImageBitmap(bitmap);
                bitmapImages = bitmap;
            } catch (Exception e) {
                Log.e("erro", "" + e);
            }
            Toast.makeText(getContext(), "Thêm ảnh thành công !", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == REQUESTCODE_FOLDER && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmapImages = bitmap;
                mImageView.setImageURI(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Toast.makeText(getContext(), "Thêm ảnh thành công !", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);


    }

    public void EditFood(Foody foody) { ;

        foody.setName(mEditTextName.getText().toString());
        foody.setPrice(Double.parseDouble(mEditTextPrice.getText().toString()));
        foody.setDetail(mEditTextReview.getText().toString());
        foody.setImage(DataConvert.ConvertImages(bitmapImages));
        Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
    }
    public String Name(){
        return mEditTextName.getText().toString();
    }
    public String Review(){
        return mEditTextReview.getText().toString();
    }
    public Double Price(){
        return Double.parseDouble(mEditTextPrice.getText().toString());
    }

//    public Byte[] I(){
//        return Double.parseDouble(mEditTextPrice.getText().toString());
//    }

}
