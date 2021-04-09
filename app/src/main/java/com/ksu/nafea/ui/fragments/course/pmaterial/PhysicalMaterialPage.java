package com.ksu.nafea.ui.fragments.course.pmaterial;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextWatcher;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.logic.QueryPostStatus;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.logic.account.Student;
import com.ksu.nafea.logic.course.Comment;
import com.ksu.nafea.logic.course.Course;
import com.ksu.nafea.logic.material.Material;
import com.ksu.nafea.logic.material.PMatComment;
import com.ksu.nafea.logic.material.PhysicalMaterial;
import com.ksu.nafea.ui.nafea_views.dialogs.PopupConfirmDialog;
import com.ksu.nafea.ui.nafea_views.recycler_view.GeneralRecyclerAdapter;
import com.ksu.nafea.ui.nafea_views.recycler_view.ListAdapter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhysicalMaterialPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhysicalMaterialPage extends Fragment
{
    private static final String TAG = "PhysicalMaterial";
    private TextView name,sellerName,phone,city,price;
    private ImageView pMatImage;
    protected View main;
    private ArrayList<PMatComment> pMatComments;
    private ProgressDialog progressDialog;
    private RecyclerView pmatRecyclerView;
    private PhysicalMaterial pmat =new PhysicalMaterial();
    private EditText comment;
    private Button sends;
    private Button reporttbn;

    private static final String namePre = "اسم المصدر: ";
    private static final String sellerNamePre= "اسم البائع: ";
    private static final String phonePre= " رقم البائع: ";
    private static final String cityPre= "المدينة: ";
    private static final String pricePre= "السعر: ";
    private static final String currency=" ريال";

    public PhysicalMaterialPage()
    {
        pMatComments=new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhysicalMaterialPage.
     */
    // TODO: Rename and change types and number of parameters
    public static PhysicalMaterialPage newInstance(String param1, String param2)
    {
        PhysicalMaterialPage fragment = new PhysicalMaterialPage();
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
        main = inflater.inflate(R.layout.fragment_physical_material_page, container, false);

        pmat =(PhysicalMaterial) User.material;
        name = (TextView) main.findViewById(R.id.pmatName);
        sellerName = (TextView) main.findViewById(R.id.pmatSeller);
        city = (TextView) main.findViewById(R.id.pmatCity);
        price = (TextView) main.findViewById(R.id.pmatPrice);
        phone = (TextView) main.findViewById(R.id.sellerPhone);
        pMatImage = (ImageView) main.findViewById(R.id.pmatImage);
        pmatRecyclerView = (RecyclerView) main.findViewById(R.id.pmatComments);
        comment = (EditText) main.findViewById(R.id.comment);
        sends= (Button) main.findViewById(R.id.send);
        progressDialog = new ProgressDialog(getContext());



        sellerName.setText(sellerNamePre+pmat.getFullName());
        name.setText(namePre+pmat.getName());
        phone.setText(phonePre+"0"+String.valueOf(pmat.getSellerPhone()));
        price.setText(pricePre+String.valueOf(pmat.getPrice())+currency);
        city.setText(cityPre+pmat.getCity());
        sends.setVisibility(View.GONE);

        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String fieldText = comment.getText().toString();
                if (!fieldText.isEmpty()) {
                    sends.setVisibility(View.VISIBLE);
                } else {
                    sends.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddCommentClicked();
            }
        });
        if(pmat.getImageUrl() != null)
        {
            Picasso.with(getContext()).load(pmat.getImageUrl()).into(pMatImage);

            if(pMatImage.getDrawable() == null)
                pMatImage.setImageResource(R.drawable.no_picture);
        }
            retrievePMatComments();

        return main;
    }
  private void fillRecCom(){

      final int itemViewLayout = R.layout.item_view_course_comment;

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
              return pMatComments.size();
          }

          @Override
          public void onBind(View itemView, final int position)
          {
              onItemViewBind(itemView, position);
          }
      };
      GeneralRecyclerAdapter adapter = new GeneralRecyclerAdapter(getContext(), listAdapter);
      pmatRecyclerView.setItemViewCacheSize(pMatComments.size());
      pmatRecyclerView.setAdapter(adapter);
      pmatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
  }
  private void onItemViewBind(View itemView, int pos){
          ConstraintLayout mainLayout = (ConstraintLayout) itemView.findViewById(R.id.crsComment_mainLayout);
          TextView nameText = (TextView) itemView.findViewById(R.id.crsComment_txt_name);
          TextView commentText = (TextView) itemView.findViewById(R.id.crsComment_txt_comment);
          TextView dateText = (TextView) itemView.findViewById(R.id.crsComment_txt_date);
          final PMatComment comment = pMatComments.get(pos);
          ImageView trash = (ImageView) itemView.findViewById(R.id.crsComment_img_trash);
          String title = "";
          String msg = "هل أنت متأكد تريد أن تحذف هذا التعليق؟";
         // assignDeleteProcess(trash, comment.getOwner(), comment, title, msg)
          nameText.setText(comment.getFullName());
          commentText.setText(comment.getComment());
          dateText.setText(comment.getTime());
  }
   private void retrievePMatComments(){
       Course crs = User.course;
       PhysicalMaterial pmt =(PhysicalMaterial) User.material;
       PMatComment.retrieveAllComments(crs,pmt, new QueryRequestFlag<ArrayList<PMatComment>>() {
           @Override
           public void onQuerySuccess(ArrayList<PMatComment> resultObject) {
               if (resultObject != null) {
                   pMatComments = resultObject;
                   fillRecCom();
               }
           }
           @Override
           public void onQueryFailure(FailureResponse failure) {
            Log.d(TAG,failure.getMsg()+"/n"+failure.toString());
           }
       });
   }





  protected void assignDeleteProcess(View view, String owner, final PMatComment targetData, final String title, final String msg)
  {
      boolean hasPrivilege = false;
      if(User.userAccount != null)
          hasPrivilege = User.userAccount.getEmail().equalsIgnoreCase(owner);

      if(!hasPrivilege)
      {
          view.setVisibility(View.GONE);
          return;
      }

      view.setVisibility(View.VISIBLE);
      view.setOnClickListener(new View.OnClickListener()
      {
          @Override
          public void onClick(View v)
          {
              showConfirmPopup(targetData, title, msg);
          }
      });
  }

  private void showConfirmPopup(final PMatComment targetData, String title, String msg)
  {
      String positive = "نعم";
      String negative = "لا";
      PopupConfirmDialog detailsDialog = new PopupConfirmDialog(title, msg, positive, negative, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which)
          {
              QueryRequestFlag<QueryPostStatus> onDelRequest = onDeleteRequest(targetData);
              progressDialog.show();
              onConfirmDeleteClicked(targetData, onDelRequest);
          }
      }, new DialogInterface.OnClickListener()
      {
          @Override
          public void onClick(DialogInterface dialog, int which)
          {
              onDeleteCancelClicked(targetData);
          }
      });
      detailsDialog.show(getActivity().getSupportFragmentManager(), User.course.getSymbol());
  }

  private QueryRequestFlag<QueryPostStatus> onDeleteRequest(final PMatComment targetData)
  {
      return new QueryRequestFlag<QueryPostStatus>()
      {
          @Override
          public void onQuerySuccess(QueryPostStatus resultObject)
          {
              progressDialog.dismiss();

              if(resultObject != null)
              {
                  if(resultObject.getAffectedRows() > 0)
                  {
                      onDeletionPerform(targetData);
                      fillRecCom();
                      showToastMsg(getString(R.string.material_deleted));
                  }
              }
          }

          @Override
          public void onQueryFailure(FailureResponse failure)
          {
              progressDialog.dismiss();

              showToastMsg("فشل إزالة المحتوى");
              Log.d(TAG, failure.getMsg() + "\n" + failure.toString());
          }
      };
  }

  protected  void onDeletionPerform(final PMatComment targetData)
  {
      pMatComments.remove(targetData);
  }

  protected void onConfirmDeleteClicked(final PMatComment targetData, QueryRequestFlag<QueryPostStatus> onDeleteRequest)
  {
      PhysicalMaterial pmate=(PhysicalMaterial) User.material;
      PMatComment.delete(User.course, pmate, targetData, onDeleteRequest);
  }

  protected void onDeleteCancelClicked(final PMatComment targetData)
  {

  }
  protected void showToastMsg(String msg)
  {
      Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
  }
    private void onAddCommentClicked()
    {
        if(User.userAccount == null)
        {
            showToastMsg(getString(R.string.toastMsg_loginFirst));
            return;
        }

        Student student = (Student)User.userAccount;
        final String commentString = comment.getText().toString();

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(date);

        final PMatComment newComment = new PMatComment(student.getFirstName(), student.getLastName(), commentString, time);

        progressDialog.show();
        PMatComment.insertComment(student, User.course,(PhysicalMaterial) User.material,newComment, new QueryRequestFlag<QueryPostStatus>()

        {
            @Override
            public void onQuerySuccess(QueryPostStatus resultObject)
            {
                progressDialog.dismiss();

                if(resultObject != null)
                {
                    pMatComments.add(0, newComment);

                    fillRecCom();

                    comment.setText("");
                    sends.setVisibility(View.GONE);
                    showToastMsg("تم إضافة تعليقك");
                }
            }

            @Override
            public void onQueryFailure(FailureResponse failure)
            {
                progressDialog.dismiss();
                showToastMsg("لا يمكنك إضافة أكثر من تعليق");
                Log.d(TAG, failure.getMsg() + "\n" + failure.toString());
            }
        });
    }


}