package com.ksu.nafea.ui.fragments.course.ematerial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksu.nafea.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadEMaterialPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadEMaterialPage extends Fragment {

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

        //Code Here

        return main;
    }
}