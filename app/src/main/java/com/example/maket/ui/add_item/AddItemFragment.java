package com.example.maket.ui.add_item;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maket.Activity.Home_Activity2;
import com.example.maket.Convert.DataConvert;
import com.example.maket.DAO.AppDatabase;
import com.example.maket.Entity.Foody;
import com.example.maket.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddItemFragment extends Fragment {
    private static final int REQUESTCODE_CAMERA = 777;
    private static final int REQUESTCODE_FOLDER = 999;
    private EditText mEditText_name;
    private EditText mEditText_price;
    private EditText mEditText_discribe;
    private Spinner mSpinner_food;
    private Button mButton_add;
    private ImageButton mimageButton_camera;
    private ImageButton mimageButton_folder;
    private ImageView mImageView_imagesfood;
    Context context;
    private List<String> list;
    Bitmap bitmapImages = null;

    private TextView textView;
    private AddItemVIewModel addItemVIewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addItemVIewModel =
                ViewModelProviders.of(this).get(AddItemVIewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_item, container, false);
        mEditText_name = root.findViewById(R.id.edt_namefood);
        mEditText_price = root.findViewById(R.id.edt_price);
        mEditText_discribe = root.findViewById(R.id.edt_describe);
        mButton_add = root.findViewById(R.id.btn_addfood);
        mimageButton_camera = root.findViewById(R.id.imv_camera);
        mimageButton_folder = root.findViewById(R.id.imv_folder);
        mImageView_imagesfood = root.findViewById(R.id.imv_add_food);
        mSpinner_food = root.findViewById(R.id.spn_food);
        list = new ArrayList<>();
        list.add("Áo");
        list.add("Quần");
        list.add("Áo khoác");
        list.add("Váy");
        list.add("Mũ");
        list.add("Giày/dép");
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, list){
            public View getView(int position, View convertView,ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextSize(16);

                return v;

            }

            public View getDropDownView(int position, View convertView,ViewGroup parent) {

                View v = super.getDropDownView(position, convertView,parent);

                ((TextView) v).setGravity(Gravity.CENTER);

                return v;

            }
        };
        mSpinner_food.setAdapter(adapter);

        mEditText_price.setInputType(InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_VARIATION_NORMAL);


        mimageButton_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, REQUESTCODE_CAMERA);

            }
        });
        mimageButton_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUESTCODE_FOLDER);
            }
        });

        mButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loai = mSpinner_food.getSelectedItem().toString();
                String name = mEditText_name.getText().toString();
                String pri = mEditText_price.getText().toString();
                String review = mEditText_discribe.getText().toString();
                try {
                    if (name.isEmpty()) {
                        mEditText_name.setError("Nhập tên!");
                        return;
                    }
                    if (pri.isEmpty()) {
                        mEditText_price.setError("Nhập giá");
                        return;
                    }
                    if (review.isEmpty()) {
                        mEditText_discribe.setError("Nhập mô tả !");
                        return;
                    }
                } catch (Exception e) {
                    Log.e("ERRO", "" + e);
                }

                try {
                    AppDatabase db = AppDatabase.getInstance(getContext());
                    Foody foody = new Foody();
                    foody.setName(name);
                    foody.setCategory(loai);
                    Double price = Double.parseDouble(pri);
                    foody.setPrice(price);
                    foody.setDetail(review);
                    foody.setImage(DataConvert.ConvertImages(bitmapImages));
                    db.daoFood().insertFoody(foody);
                    Intent intent = new Intent(getContext(), Home_Activity2.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("ERRO", "" + e);
                }
            }
        });

        getActivity().setTitle("Add item");
        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUESTCODE_CAMERA && resultCode == Activity.RESULT_OK && data != null) ;
        {
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mImageView_imagesfood.setImageBitmap(bitmap);
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
                mImageView_imagesfood.setImageURI(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Toast.makeText(getContext(), "Thêm ảnh thành công !", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}





