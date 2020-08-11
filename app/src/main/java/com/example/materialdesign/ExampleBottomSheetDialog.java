package com.example.materialdesign;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;



public class ExampleBottomSheetDialog extends BottomSheetDialogFragment {
    BottomSheetListener mListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.bottom_sheet_layout,container,false);

        Button save = v.findViewById(R.id.save_button);
        final EditText newTask = v.findViewById(R.id.new_task);
        final EditText newTaskMsg = v.findViewById(R.id.task_msg);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              if(TextUtils.isEmpty(newTask.getText().toString()))
              {

                  Toast.makeText(getContext(),"Enter the task",Toast.LENGTH_SHORT);
              }
              else {
                    mListener.onButtonClicked(newTask.getText().toString(),newTaskMsg.getText().toString());
                  dismiss();
              }
            }
        });

        return v;
    }


    public interface BottomSheetListener{
        void onButtonClicked(String title,String msg);
    }

   @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        }catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString());
        }
    }
}
