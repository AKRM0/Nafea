package com.ksu.nafea.ui.fragments.course.ematerial.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ksu.nafea.R;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.logic.material.ElectronicMaterial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoPage extends Fragment {

    private String urlFromDB="https://www.youtube.com";
    private Button watch;
    private Button report;
    private TextView docNameFromDB;
    private  String UpdatedDocNameFromDB="Math 244 past exam";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VideoPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoPage.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoPage newInstance(String param1, String param2) {
        VideoPage fragment = new VideoPage();
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
        View main = inflater.inflate(R.layout.fragment_video_page, container, false);



        watch= main.findViewById(R.id.WatchBTN);
        report= main.findViewById(R.id.reportBTN);
        docNameFromDB= main.findViewById(R.id.docNameFromDB);


        //here we change the text to the docName we stored in the DB
        docNameFromDB.setText(UpdatedDocNameFromDB);


        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get the url from DB and put it in the next line;;
                gotoUrl(urlFromDB) ;
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we should have the code will make trans to report page
            }
        });
        return main ;
    }
    private void gotoUrl(String urlFromDB) {
        Uri uri=Uri.parse(urlFromDB);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}