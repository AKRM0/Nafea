package com.ksu.nafea.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.logic.Contain;
import com.ksu.nafea.logic.course.Course;
import com.ksu.nafea.ui.activities.CoursePageActivity;
import com.ksu.nafea.ui.activities.MainActivity;
import com.ksu.nafea.R;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.logic.account.Student;
import com.ksu.nafea.ui.fragments.browse.SelectFragment;
import com.ksu.nafea.ui.nafea_views.NSpinner;
import com.ksu.nafea.ui.nafea_views.dialogs.PopupDetailsDialog;
import com.ksu.nafea.ui.nafea_views.recycler_view.GeneralRecyclerAdapter;
import com.ksu.nafea.ui.nafea_views.recycler_view.ListAdapter;

import java.util.ArrayList;

import static com.ksu.nafea.logic.User.major;
import static com.ksu.nafea.logic.User.university;
import static com.ksu.nafea.logic.User.college;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends  SelectFragment<Course>
{
private int courseDataCounter = 0;
private TextView CurrentLevel;
private TextView DepartmentPlan;
private NSpinner spinnerType;
private ArrayList<Course> courses=new ArrayList<Course> ();
private ArrayList <Course> courses2 = new ArrayList<Course>();
private RecyclerView recyclerView;
private RecyclerView recyclerView2;

private ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePageFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2)
    {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private void openLoginPage()
    {
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_home_to_login);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        final View main = inflater.inflate(R.layout.fragment_home_page, container, false);
        DepartmentPlan=main.findViewById(R.id.DepartmentPlan);
        CurrentLevel = main.findViewById(R.id.CurrentLevel);
        spinnerType = main.findViewById(R.id.NSpinner);
        recyclerView=main.findViewById(R.id.HomeRec);
        recyclerView2=main.findViewById(R.id.HomeRec2);
        progressDialog=new ProgressDialog(getContext());

        if(User.userAccount != null) {
            final Student student = (Student) User.userAccount;
            spinnerType.addOption(getString(R.string.allOption));
            Contain.retrieveAllLevels(student.getMajor(), new QueryRequestFlag<ArrayList<String>>() {
                @Override
                public void onQuerySuccess(ArrayList<String> resultObject) {
                    if(resultObject!= null){
                        spinnerType.addOptionsList(resultObject);
                    }
                }

                @Override
                public void onQueryFailure(FailureResponse failure) {
                    Log.d(TAG,failure.getMsg()+"/n"+failure.toString());
                }
            });

            spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selected =spinnerType.getSelectedOption();
                    fillRecyclerView(selected);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            DepartmentPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShowPlanClicked();
                }
            });

        }
else
        {
            openLoginPage();
        }

        return main;
    }

    protected <T> void updateRecyclerView(final ArrayList <T> data)
    {
        int size=data.size()/2;

        final int itemViewLayout = R.layout.item_view_course_home;

            for (int i=0;i<size;i++)
            {
                courses2.add(courses.get(i));
                courses.remove(i);
            }
        ListAdapter listAdapter = new ListAdapter()
        {
            @Override
            public int getResourceLayout()
            {
                return itemViewLayout;
            }

            @Override
            public int getItemCount()
            {
                return data.size();
            }

            @Override
            public void onBind(View itemView, final int position)
            {
                onItemViewBind(itemView, position);
            }
        };


        GeneralRecyclerAdapter adapter = new GeneralRecyclerAdapter(getContext(), listAdapter);
        recyclerView.setItemViewCacheSize(data.size());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ListAdapter listAdapter2 = new ListAdapter()
        {
            @Override
            public int getResourceLayout()
            {
                return itemViewLayout;
            }

            @Override
            public int getItemCount()
            {
                return courses2.size();
            }

            @Override
            public void onBind(View itemView, final int position)
            {
                onItemViewBind(itemView, position);
            }
        };
        GeneralRecyclerAdapter adapter2 = new GeneralRecyclerAdapter(getContext(), listAdapter2);
        recyclerView2.setItemViewCacheSize(courses2.size());
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
    }



    protected void fillRecyclerView(String selectedOption)
    {
        Student student =(Student)User.userAccount;
        if(selectedOption != null && !selectedOption.equalsIgnoreCase(getString(R.string.allOption)))
            Course.retrieveCoursesInMajor(student.getMajor(), selectedOption, retrieveRecyclerViewData());
        else
            Course.retrieveAllCoursesInMajor(student.getMajor(), retrieveRecyclerViewData());
    }



    protected QueryRequestFlag<ArrayList<Course>> retrieveRecyclerViewData()
    {
        progressDialog.show();

        QueryRequestFlag<ArrayList<Course>> requestFlag = new QueryRequestFlag<ArrayList<Course>>()
        {
            @Override
            public void onQuerySuccess(ArrayList<Course> resultObject)
            {
                progressDialog.dismiss();

                if(resultObject != null)
                {
                    courses = resultObject;
                    updateRecyclerView(courses);
                }
            }

            @Override
            public void onQueryFailure(FailureResponse failure)
            {
                progressDialog.dismiss();

                showToastMsg("فشل عرض البيانات");
                Log.d(TAG, failure.getMsg() + "\n" + failure.toString());
            }
        };

        return requestFlag;
    }
    protected void onItemViewBind(View itemView, final int position)
    {
        ConstraintLayout mainLayout = (ConstraintLayout) itemView.findViewById(R.id.crsInfo_homePage);
        final Course course = courses.get(position);
        TextView crsName=itemView.findViewById(R.id.crsInfo_crsSymbol);
        crsName.setText(course.getSymbol());
        TextView courseDetails=itemView.findViewById(R.id.crsInfo_crsDetails);

        courseDetails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PopupDetailsDialog detailsDialog = new PopupDetailsDialog(course.getName(), course.getDescription(), "حسناً");
                detailsDialog.show(getParentFragmentManager(), course.getSymbol());
            }
        });

        mainLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onItemClicked(course);
            }
        });
    }

    private void onItemClicked(Course course)
    {
        User.course = retrieveCourseData(course);
    }

    private void openCoursePage()
    {
        courseDataCounter = 0;

        // To-Do open Course Page
        Intent intent = new Intent(getContext(), CoursePageActivity.class);
        startActivity(intent);
    }


    private Course retrieveCourseData(final Course course)
    {
        progressDialog.show();

        return Course.retrieveFullCourse(course, new QueryRequestFlag<Boolean>()
        {
            @Override
            public void onQuerySuccess(Boolean resultObject)
            {
                if(resultObject != null)
                {
                    if(resultObject)
                    {
                        if(courseDataCounter == 2)
                        {
                            progressDialog.dismiss();
                            openCoursePage();
                        }
                        else
                            ++courseDataCounter;
                    }
                    else
                    {
                        progressDialog.dismiss();
                        showToastMsg("فشل إظهار البيانات");
                    }
                }
            }

            @Override
            public void onQueryFailure(FailureResponse failure)
            {
                progressDialog.dismiss();
                Log.d(TAG, failure.getMsg() + "\n" + failure.toString());
                showToastMsg("فشلت العملية");
            }
        });
    }
    private void onShowPlanClicked()
    {

        // Navigation change
        //openPage(R.id.action_majorPage_to_departmentPlan);
    }

}