package com.ksu.nafea.ui.fragments.course.pmateerial;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.logic.User;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadPhysMatPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadPhysMatPage extends Fragment {

    private ImageView mImageView;
    private Button mChooseBtn;
    private Button cancel;
    protected View main;
    private Button upload;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UploadPhysMatPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadPhysMatPage.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadPhysMatPage newInstance(String param1, String param2) {
        UploadPhysMatPage fragment = new UploadPhysMatPage();
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
          /*  mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        main =inflater.inflate(R.layout.fragment_upload_phys_mat_page, container, false);

        pView();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        upload=main.findViewById(R.id.addUpB);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (User.userAccount != null) {
                    // Code for Upload server


                }
                else  Toast.makeText(getContext(), getString(R.string.toastMsg_loginFirst), Toast.LENGTH_SHORT).show();
            }
        });

        //handle button click
        mChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (User.userAccount != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_DENIED) {
                            //permission not granted, request it.
                            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                            //show popup for runtime permission
                            requestPermissions(permissions, PERMISSION_CODE);
                        } else {
                            //permission already granted
                            pickImageFromGallery();
                        }
                    } else {
                        //system os is less then marshmallow
                        pickImageFromGallery();
                    }

                }
                else  Toast.makeText(getContext(), getString(R.string.toastMsg_loginFirst), Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return main;
    }

    private void pView() {


        mImageView = (ImageView) main.findViewById(R.id.imageview);
        mChooseBtn =(Button) main.findViewById(R.id.choose_image_btn);
        //urL=findViewById(R.id.textView2);
        cancel= (Button) main.findViewById(R.id.cancelPButton);
    }




    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length >0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                }
                else {
                    //permission was denied
                    Toast.makeText(getActivity(), "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //handle result of picked image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            mImageView.setImageURI(data.getData());
            data.getData().getPath();
            //urL.setText(data.toString());

        }
    }
}