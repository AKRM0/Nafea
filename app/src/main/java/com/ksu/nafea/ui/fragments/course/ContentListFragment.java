package com.ksu.nafea.ui.fragments.course;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.ui.nafea_views.NSpinner;
import com.ksu.nafea.ui.nafea_views.recycler_view.GeneralRecyclerAdapter;
import com.ksu.nafea.ui.nafea_views.recycler_view.ListAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentListFragment<T> extends Fragment
{
    public static final String TAG = "ContentList";
    protected View main;
    private RecyclerView recyclerView;
    protected LinearLayout bottomLayout;
    protected ProgressDialog progressDialog;

    protected ArrayList<T> data;

    public ContentListFragment()
    {
        // Required empty public constructor
        data = new ArrayList<T>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContentListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContentListFragment newInstance(String param1, String param2) {
        ContentListFragment fragment = new ContentListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        main = inflater.inflate(R.layout.fragment_content_list, container, false);

        viewsInit();
        setBarTitle(User.course.getSymbol());
        onContentListCreated(main);
        setData();
        updateRecyclerView();

        return main;
    }


    protected void openPage(int pageID)
    {
        Navigation.findNavController(getActivity(), R.id.crs_navHost).navigate(pageID);
    }
    protected void openPage(int pageID, boolean visibility)
    {
        setBottomNavigationVisibility(visibility);
        Navigation.findNavController(getActivity(), R.id.crs_navHost).navigate(pageID);
    }

    //-------------------------------------------------[UI methods]-------------------------------------------------

    private void viewsInit()
    {
        recyclerView = (RecyclerView) main.findViewById(R.id.contList_recyclerView);
        bottomLayout = (LinearLayout) main.findViewById(R.id.contList_bottomLayout);
        progressDialog = new ProgressDialog(getContext());
    }

    protected void updateRecyclerView()
    {
        final int itemViewLayout = getItemViewLayout();

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
    }


    protected void showToastMsg(String msg)
    {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    protected void setBarTitle(String title)
    {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    protected void setBottomNavigationVisibility(boolean visibility)
    {
        int visibilityValue = visibility ? View.VISIBLE : View.GONE;
        ((CoursePageActivity) getActivity()).bottomNav.setVisibility(visibilityValue);
    }


    protected Button addButtonView(String text)
    {
        Button button = new Button(getContext(), null, 0, R.style.Nafea_DefaultButton);
        bottomLayout.addView(button);
        button.setText(text);

        return button;
    }
    protected Button addButtonView(String text, int style)
    {
        Button button = new Button(getContext(), null, 0, style);
        bottomLayout.addView(button);
        button.setText(text);

        return button;
    }

    //-------------------------------------------------[Data methods]-------------------------------------------------

    protected ArrayList<T> getData()
    {
        return data;
    }

    //-------------------------------------------------[Overridable methods]-------------------------------------------------

    protected void onContentListCreated(View main)
    {

    }

    protected void setData()
    {

    }


    protected int getItemViewLayout()
    {
        return 0;
    }
    protected void onItemViewBind(View itemView, final int position)
    {

    }



}