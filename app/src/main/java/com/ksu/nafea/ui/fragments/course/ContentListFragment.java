package com.ksu.nafea.ui.fragments.course;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.logic.User;
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
    private ConstraintLayout pageBarLayout;
    private TextView pageNum;
    private ImageView leftPage, rightPage;
    private RecyclerView recyclerView;
    protected LinearLayout bottomLayout;
    protected ProgressDialog progressDialog;

    protected ArrayList<T> data;
    private ArrayList<T> pageData;

    private int currentPage = 1;
    private int lastPage = 1;
    private static final int MAX_PAGE_ELEMENTS = 16;



    protected int getMaxPageElements()
    {
        return MAX_PAGE_ELEMENTS;
    }



    public ContentListFragment()
    {
        // Required empty public constructor
        data = new ArrayList<T>();
        pageData = new ArrayList<T>();
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
        initListeners();
        setBarTitle(User.course.getSymbol());
        onContentListCreated(main);
        setData();
        updateRecyclerView();

        return main;
    }


    //-------------------------------------------------[UI methods]-------------------------------------------------

    private void viewsInit()
    {
        pageBarLayout = (ConstraintLayout) main.findViewById(R.id.contList_layout_pageNumBar);
        pageNum = (TextView) main.findViewById(R.id.contList_txt_pageNum);
        leftPage = (ImageView) main.findViewById(R.id.contList_img_leftPage);
        rightPage = (ImageView) main.findViewById(R.id.contList_img_rightPage);

        recyclerView = (RecyclerView) main.findViewById(R.id.contList_recyclerView);
        bottomLayout = (LinearLayout) main.findViewById(R.id.contList_bottomLayout);
        progressDialog = new ProgressDialog(getContext());
    }

    private void initListeners()
    {
        leftPage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onLeftPageClicked();
            }
        });

        rightPage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onRightPageClicked();
            }
        });
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
                return pageData.size();
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


    protected void openPage(int targetPageID, int backPageID, boolean visibility)
    {
        ((CoursePageActivity) getActivity()).openPage(targetPageID, backPageID, visibility);
    }

    protected void setBarTitle(String title)
    {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(actionBar != null)
            actionBar.setTitle(title);
    }


    protected void removeBottomView(View view)
    {
        bottomLayout.removeView(view);
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

    protected EditText addField(String hintText)
    {
        EditText field = new EditText(getContext(), null, 0, R.style.Nafea_DefaultTextField);
        bottomLayout.addView(field);
        field.setHint(hintText);

        return field;
    }
    protected EditText addField(String hintText, int style)
    {
        EditText field = new EditText(getContext(), null, 0, style);
        bottomLayout.addView(field);
        field.setHint(hintText);

        return field;
    }

    //-------------------------------------------------[Data methods]-------------------------------------------------

    protected ArrayList<T> getFullData()
    {
        return data;
    }
    protected ArrayList<T> getData()
    {
        return pageData;
    }

    private void updatePageData()
    {
        if(!data.isEmpty())
        {
            lastPage = ( (data.size() - 1) / getMaxPageElements() ) + 1;
            pageNum.setText(currentPage + "/" + lastPage);
        }

        if(lastPage > 1)
            pageBarLayout.setVisibility(View.VISIBLE);
        else
            pageBarLayout.setVisibility(View.GONE);


        if(!pageData.isEmpty())
            pageData.clear();

        int currentElement = getMaxPageElements() * (currentPage - 1);
        int lastElement = Math.min( (currentElement + getMaxPageElements()), data.size());
        for(int i = currentElement; i < lastElement; i++)
        {
            pageData.add(data.get(i));
        }
    }

    private void onLeftPageClicked()
    {
        currentPage = Math.max(1, (currentPage - 1));
        updatePageData();
        updateRecyclerView();
    }
    private void onRightPageClicked()
    {
        currentPage = Math.min(lastPage, (currentPage + 1));
        updatePageData();
        updateRecyclerView();
    }

    //-------------------------------------------------[Overridable methods]-------------------------------------------------

    protected void onContentListCreated(View main)
    {

    }

    protected void setData()
    {
        updatePageData();
    }


    protected int getItemViewLayout()
    {
        return 0;
    }
    protected void onItemViewBind(View itemView, final int position)
    {

    }



}