package com.example.maket;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheet extends BottomSheetDialogFragment {

    private BottomSheetListener mSheetListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet,container,false);
        Button mButtonDelete = view.findViewById(R.id.btn_delete);
        Button mButtonUpdate = view.findViewById(R.id.btn_update);
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSheetListener.OnitemClick("DELETE");
                dismiss();
            }
        });
        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSheetListener.OnitemClick("UPDATE");
                dismiss();
            }
        });
        return view;
    }

    public interface BottomSheetListener{
        void OnitemClick(String text);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mSheetListener = (BottomSheetListener) context;
        }catch (Exception e){
//            throw new ClassCastException(context.toString());
        }
    }
}
