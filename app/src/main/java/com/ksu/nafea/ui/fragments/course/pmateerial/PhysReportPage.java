package com.ksu.nafea.ui.fragments.course.pmateerial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ksu.nafea.R;
import com.ksu.nafea.utilities.NafeaUtil;

import static com.ksu.nafea.utilities.NafeaUtil.showToastMsg;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhysReportPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhysReportPage extends Fragment {

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

    public PhysReportPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhysReportPage.
     */
    // TODO: Rename and change types and number of parameters
    public static PhysReportPage newInstance(String param1, String param2) {
        PhysReportPage fragment = new PhysReportPage();
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
        View main = inflater.inflate(R.layout.fragment_phys_report_page, container, false);
        spinnerType = (Spinner) main.findViewById(R.id.physreportSpinner);
        report =(Button) main.findViewById(R.id.physreportBTN2);
        cancle = (Button)main.findViewById(R.id.physcancelBTN);
        reportText = (TextView) main.findViewById(R.id.physreportText);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 2) {
                    reportText.setVisibility(View.VISIBLE);

                } else if(position == 1) {
                    reportText.setVisibility(View.INVISIBLE);
                    showToastMsg(getContext(), "  تم إختيار " + spinnerType.getSelectedItem().toString());
                }   else {
                    reportText.setVisibility(View.INVISIBLE);
                    showToastMsg(getContext(),"  تم إختيار " + spinnerType.getSelectedItem().toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       return main;
        }


}