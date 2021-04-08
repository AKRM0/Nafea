package com.ksu.nafea.ui.fragments.course.ematerial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ksu.nafea.R;
import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.logic.GeneralPool;
import com.ksu.nafea.logic.QueryPostStatus;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.logic.account.Student;
import com.ksu.nafea.logic.course.Course;
import com.ksu.nafea.logic.material.ElectronicMaterial;
import com.ksu.nafea.ui.activities.CoursePageActivity;
import com.ksu.nafea.ui.fragments.browse.SelectFragment;
import com.ksu.nafea.ui.nafea_views.NSpinner;
import com.ksu.nafea.utilities.NafeaUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EMatReportPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EMatReportPage extends SelectFragment<Course>
{
    public static final String TAG = "EMatReportPage";
    private View main;
    private NSpinner reportType;
    private Button reportButton;
    private EditText userReportComment;
    private ProgressDialog progressDialog;

    String illegalType = null;
    String similarType = null;
    String otherType = null;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        main = inflater.inflate(R.layout.fragment_e_mat_report_page, container, false);

        illegalType = getString(R.string.matReport_illegalType);
        similarType = getString(R.string.matReport_similarType);
        otherType = getString(R.string.matReport_otherType);


        initViews();
        initDropdown();
        initListeners();


        return main;
    }


    private void initViews()
    {
        reportType = (NSpinner) main.findViewById(R.id.ematReport_dropdown_type);
        reportButton = (Button) main.findViewById(R.id.ematReport_b_report);
        userReportComment = (EditText) main.findViewById(R.id.ematReport_ed_comment);
        progressDialog = new ProgressDialog(getContext());
    }

    private void initDropdown()
    {
        reportType.addOption(illegalType);
        reportType.addOption(similarType);
        reportType.addOption(otherType);


        // in page opening.
        String selectedOption = reportType.getSelectedOption();
        if(selectedOption.equalsIgnoreCase(otherType))
            userReportComment.setVisibility(View.VISIBLE);
        else
            userReportComment.setVisibility(View.INVISIBLE);


        reportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedOption = reportType.getSelectedOption();

                if(selectedOption.equalsIgnoreCase(otherType))
                {
                    userReportComment.setVisibility(View.VISIBLE);
                }
                else
                    userReportComment.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    private void initListeners()
    {
        reportButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onReportClicked();
            }
        });
    }

    private void onReportClicked()
    {
        ElectronicMaterial material = (ElectronicMaterial) User.material;

        String selectedOption = reportType.getSelectedOption();
        String userComment = userReportComment.getText().toString();
        Integer similarMatID = null;

        if(selectedOption.equalsIgnoreCase(similarType))
            similarMatID = material.getId();


        Student student = (Student) User.userAccount;
        progressDialog.show();

        GeneralPool.insertEMatReport(student, User.course, material, selectedOption, userComment, similarMatID, new QueryRequestFlag<QueryPostStatus>()
        {
            @Override
            public void onQuerySuccess(QueryPostStatus resultObject)
            {
                progressDialog.dismiss();

                if(resultObject != null)
                {
                    if(resultObject.getAffectedRows() > 0)
                    {
                        NafeaUtil.showToastMsg(getContext(), "تم إرسال البلاغ");

                        CoursePageActivity activity = ((CoursePageActivity) getActivity());
                        if(!activity.isPageStackEmpty())
                            activity.onBackClicked();
                    }
                }
            }

            @Override
            public void onQueryFailure(FailureResponse failure)
            {
                progressDialog.dismiss();

                NafeaUtil.showToastMsg(getContext(), "فشل إرسال البلاغ");
                Log.d(TAG, failure.getMsg() + "\n" + failure.toString());

                CoursePageActivity activity = ((CoursePageActivity) getActivity());
                if(!activity.isPageStackEmpty())
                    activity.onBackClicked();
            }
        });
    }


}