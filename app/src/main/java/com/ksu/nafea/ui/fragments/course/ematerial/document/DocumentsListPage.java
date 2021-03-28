package com.ksu.nafea.ui.fragments.course.ematerial.document;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ksu.nafea.R;
import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.logic.QueryPostStatus;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.logic.account.Student;
import com.ksu.nafea.logic.material.EMaterialEvaluation;
import com.ksu.nafea.logic.material.ElectronicMaterial;
import com.ksu.nafea.ui.fragments.course.ContentListFragment;

import java.util.ArrayList;

public class DocumentsListPage extends ContentListFragment<ElectronicMaterial>
{

    @Override
    protected void onContentListCreated(View main)
    {
        addButtonView(getString(R.string.EMaterial_addContent)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onAddContentClicked();
            }
        });
    }

    @Override
    protected void updateData()
    {
        String type = "Document";
        ArrayList<ElectronicMaterial> documents = ElectronicMaterial.getEMaterialsByType(User.course.getEMats(), type);
        this.data = documents;

        super.updateData();
    }



    @Override
    protected int getItemViewLayout()
    {
        return R.layout.item_view_document;
    }

    @Override
    protected void onItemViewBind(View itemView, final int position)
    {
        ConstraintLayout mainLayout = (ConstraintLayout) itemView.findViewById(R.id.doc_mainLayout);
        LinearLayout likeLayout = (LinearLayout) itemView.findViewById(R.id.doc_layout_like);
        LinearLayout dislikeLayout = (LinearLayout) itemView.findViewById(R.id.doc_layout_dislike);
        TextView documentName = (TextView) itemView.findViewById(R.id.doc_txt_docName);
        final TextView likeText = (TextView) itemView.findViewById(R.id.doc_txt_like);
        final TextView dislikeText = (TextView) itemView.findViewById(R.id.doc_txt_dislike);
        TextView documentExt = (TextView) itemView.findViewById(R.id.doc_txt_ext);

        final ElectronicMaterial document = getData().get(position);

        documentName.setText(document.getName());
        documentExt.setText(document.getExtension());
        updateEvaluationsOnScreen(likeText, dislikeText, document);


        likeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onEvaluationClicked(document, true);
                updateEvaluationsOnScreen(likeText, dislikeText, document);
            }
        });
        dislikeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onEvaluationClicked(document, false);
                updateEvaluationsOnScreen(likeText, dislikeText, document);
            }
        });

        mainLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onDocumentClicked(position);
            }
        });
    }


    private void onDocumentClicked(int position)
    {
        User.material = getData().get(position);
        openPage(R.id.action_documents_to_downloadDoucmentPage, R.id.action_downloadDoucmentPage_to_documents, false);
    }

    private void onAddContentClicked()
    {
        openPage(R.id.action_documents_to_uploadEMaterialPage, R.id.action_uploadEMaterialPage_to_documents, false);
    }

    //--------------------------------------------------------[Evaluation methods]--------------------------------------------------------

    private void updateEvaluationsOnScreen(TextView likeText, TextView dislikeText, ElectronicMaterial eMaterial)
    {
        likeText.setText(String.valueOf(eMaterial.getLikes()));
        dislikeText.setText(String.valueOf(eMaterial.getDislikes()));
    }


    private void onEvaluationClicked(ElectronicMaterial eMaterial, final boolean like)
    {
        if(User.userAccount == null)
        {
            showToastMsg("يجب أن يكون لديك حساب لتقيم");
            return;
        }

        String email = User.userAccount.getEmail();
        boolean isSuccess = like ? eMaterial.addLike(email) : eMaterial.addDislike(email);

        if(isSuccess)
            updateEvaluation(eMaterial, like);
    }

    private void updateEvaluation(final ElectronicMaterial eMaterial, final boolean like)
    {
        final Student student = (Student) User.userAccount;

        EMaterialEvaluation.insertEvaluation(student, User.course, eMaterial, like, new QueryRequestFlag<QueryPostStatus>()
        {
            @Override
            public void onQuerySuccess(QueryPostStatus resultObject)
            {
                if(resultObject != null)
                {
                    if(resultObject.getAffectedRows() > 0)
                        return;
                }

                EMaterialEvaluation.updateEvaluation(student, eMaterial, like, null);
            }

            @Override
            public void onQueryFailure(FailureResponse failure)
            {
                EMaterialEvaluation.updateEvaluation(student, eMaterial, like, null);
            }
        });
    }


}
