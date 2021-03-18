package com.ksu.nafea.ui.fragments.course.ematerial;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.logic.course.Course;
import com.ksu.nafea.ui.fragments.browse.SelectFragment;

import static com.ksu.nafea.utilities.NafeaUtil.showToastMsg;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EMatReportPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EMatReportPage extends SelectFragment<Course>  {



    private Spinner spinnerType;
    private Button report;
    private Button cancle ;
    private TextView reportText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EMatReportPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EMatReportPage.
     */
    // TODO: Rename and change types and number of parameters
    public static EMatReportPage newInstance(String param1, String param2) {
        EMatReportPage fragment = new EMatReportPage();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View main = inflater.inflate(R.layout.fragment_e_mat_report_page, container, false);

        spinnerType = (Spinner) main.findViewById(R.id.reportSpinner);
        report =(Button) main.findViewById(R.id.reportBTN2);
        cancle = (Button)main.findViewById(R.id.cancelBTN);
        reportText = (TextView) main.findViewById(R.id.reportText);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 2) {
                    reportText.setVisibility(View.VISIBLE);

                } else if(position == 1) {
                    reportText.setVisibility(View.INVISIBLE);
                    showToastMsg( "  تم إختيار " + spinnerType.getSelectedItem().toString());
                }   else {
                    reportText.setVisibility(View.INVISIBLE);
                    showToastMsg("  تم إختيار " + spinnerType.getSelectedItem().toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return main;
    }

}