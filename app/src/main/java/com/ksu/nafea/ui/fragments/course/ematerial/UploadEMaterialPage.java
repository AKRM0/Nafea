package com.ksu.nafea.ui.fragments.course.ematerial;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ksu.nafea.R;
import static android.app.Activity.RESULT_OK;
//TEST
//TEST
//TEST
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadEMaterialPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadEMaterialPage extends Fragment {


    Uri PDFUri;
    Uri WordUri;
    Uri PPTXUri;
    private TextView DocView;
    private Spinner spinnerType;
    private TextView LinkText;
    private TextView Link;

    private Button cancel;
    private Button choosePDFFile_btn;

    private TextView path_tv;
    private static final String TAG="uploadMaterial";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UploadEMaterialPage()
    {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View main = inflater.inflate(R.layout.fragment_upload_e_material_page, container, false);

        spinnerType=main.findViewById(R.id.spinnerType);
        DocView=main.findViewById(R.id.DocView);
        Link=main.findViewById(R.id.vLink);
        LinkText=main.findViewById(R.id.VideoLink);
        cancel=main.findViewById(R.id.cancelUButton);
        choosePDFFile_btn=main.findViewById(R.id.choose_file_btn);

        path_tv=main.findViewById(R.id.path_tv);

        choosePDFFile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectPDF();
                }
                else {
                    //check down
                    ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // go back to page before report
               // finish();
            }
        });




        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 1) {
                    DocView.setText("اسم للرابط:");
                    Link.setVisibility(View.VISIBLE);
                    LinkText.setVisibility(View.VISIBLE);
                    choosePDFFile_btn.setVisibility(View.INVISIBLE);
                    path_tv.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "  تم إختيار " + spinnerType.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    DocView.setText("اسم للملف:");
                    Link.setVisibility(View.INVISIBLE);
                    choosePDFFile_btn.setVisibility(View.VISIBLE);
                    LinkText.setVisibility(View.INVISIBLE);
                    path_tv.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "  تم إختيار " + spinnerType.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                }


            }
            @Override
            public void onNothingSelected (AdapterView < ? > parent){

            }

        });
        return main;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        Intent intent = new Intent();
        intent.getType();



        if (requestCode == 9) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                selectPDF();
            } else
                Toast.makeText(getContext(), "الرجاء السماح بقراءة الملفات ", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 10) {
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                selectWord();
            } else
                Toast.makeText(getContext(), "الرجاء السماح بقراءة الملفات ", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 11) {
            if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                selectPPTX();
            } else
                Toast.makeText(getContext(), "الرجاء السماح بقراءة الملفات ", Toast.LENGTH_SHORT).show();
        }

    }

    public void selectPDF() {
        Intent intent = new Intent();
        intent.setType("");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    public void selectWord() {
        Intent intent = new Intent();
        intent.setType("docx/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 87);
    }

    public void selectPPTX() {
        Intent intent = new Intent();
        intent.setType("pptx/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 88);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 86 && resultCode == RESULT_OK && resultData != null) {
            PDFUri = resultData.getData();
            path_tv.setText("File Path:" + resultData.getData());
        } else if (requestCode == 87 && resultCode == RESULT_OK && resultData != null) {
            WordUri = resultData.getData();
            path_tv.setText("File Path:" + resultData.getData());
        } else if (requestCode == 88 && resultCode == RESULT_OK && resultData != null) {
            PPTXUri = resultData.getData();
            path_tv.setText("File Path:" + resultData.getData());
        } else
            Toast.makeText(getContext(), "رجاءً قم باختيار ملف ", Toast.LENGTH_SHORT).show();

    }

}


