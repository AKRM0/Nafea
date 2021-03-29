package com.ksu.nafea.ui.fragments.course.ematerial;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ksu.nafea.R;
//TEST
//TEST
//TEST
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadEMaterialPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadEMaterialPage extends Fragment  {
    Uri PDFUri;
    private TextView DocView;
    private Spinner spinnerType;
    private TextView LinkText;
    private TextView Link;
    private Button choose;
    private Button cancel;
    private TextView path_tv;
    private String path;
    Intent myFileIntent;
    private static final String TAG = "uploadMaterial";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UploadEMaterialPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadEMaterialPage.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadEMaterialPage newInstance(String param1, String param2) {
        UploadEMaterialPage fragment = new UploadEMaterialPage();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View main = inflater.inflate(R.layout.fragment_upload_e_material_page, container, false);


        spinnerType = main.findViewById(R.id.spinnerType);
        DocView = main.findViewById(R.id.DocView);
        Link = main.findViewById(R.id.vLink);
        LinkText = main.findViewById(R.id.VideoLink);
        cancel = main.findViewById(R.id.cancelUButton);
        choose = (Button)main.findViewById(R.id.choose_file_btn);
        path_tv =(TextView) main.findViewById(R.id.path_tv);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFileIntent=new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent,10);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 1) {
                    DocView.setText("اسم للرابط:");
                    Link.setVisibility(View.VISIBLE);
                    LinkText.setVisibility(View.VISIBLE);
                    choose.setVisibility(View.INVISIBLE);
                    path_tv.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "  تم إختيار " + spinnerType.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    DocView.setText("اسم للملف:");
                    Link.setVisibility(View.INVISIBLE);
                    choose.setVisibility(View.VISIBLE);
                    LinkText.setVisibility(View.INVISIBLE);
                    path_tv.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "  تم إختيار " + spinnerType.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return main;
    }


    private void finish() {
        // go back to page before report

    }


    public void onActivityResult(int requestCode, int resultCode,@Nullable Intent resultData) {

       switch (requestCode){
           case 10:
               if (resultCode==Activity.RESULT_OK){
                   path=resultData.getData().getPath();
                   path_tv.setText("File Path:" +path);
               }

               break;

       }
    }



}