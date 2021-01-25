package com.ksu.nafea.logic;

import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.data.sql.Attribute;
import com.ksu.nafea.data.sql.EAttributeConstraint;
import com.ksu.nafea.data.sql.EntityObject;

import java.util.ArrayList;

public class GeneralPool
{
    public final static String TAG = "GeneralPool";



    //-----------------------------------------------------------[Update Queries]-----------------------------------------------------------
    public static <EntityType extends Entity<EntityType>> void
    insertRecord(Entity<EntityType> newRecord, QueryRequestFlag<QueryPostStatus> requestFlag)
    {
        try
        {
            Entity.getPool().insert(newRecord, requestFlag);
        }
        catch (Exception e)
        {
            String msg = "Failed to insert record: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }


    public static <EntityType extends Entity<EntityType>> void
    updateRecord(Integer targetID, Entity<EntityType> entity, QueryRequestFlag<QueryPostStatus> requestFlag)
    {
        try
        {
            Entity.getPool().update(entity, requestFlag, targetID);
        }
        catch (Exception e)
        {
            String msg = "Failed to update record: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }

    public static <EntityType extends Entity<EntityType>> void
    updateRecordsInRange(Integer minTargetID, Integer maxTargetID, Entity<EntityType> entity, QueryRequestFlag<QueryPostStatus> requestFlag)
    {
        try
        {
            EntityObject entityObject = entity.toEntity();
            Attribute primaryKey = entityObject.getFirstAttribute(EAttributeConstraint.PRIMARY_KEY);
            String condition = primaryKey.getName() + " >= " + minTargetID;
            condition += " AND " + primaryKey.getName() + " <= " + maxTargetID;

            Entity.getPool().update(entity, requestFlag, condition);
        }
        catch (Exception e)
        {
            String msg = "Failed to update record inn the provided range: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }


    public static <EntityType extends Entity<EntityType>> void
    deleteRecord(Class<EntityType> entityClass, Integer targetID, QueryRequestFlag<QueryPostStatus> requestFlag)
    {
        try
        {
            Entity.getPool().delete(entityClass, requestFlag, targetID);
        }
        catch (Exception e)
        {
            String msg = "Failed to delete record: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }

    public static <EntityType extends Entity<EntityType>> void
    deleteRecordsInRange(Class<EntityType> entityClass, Integer minTargetID, Integer maxTargetID, QueryRequestFlag<QueryPostStatus> requestFlag)
    {
        try
        {
            EntityObject entityObject = entityClass.newInstance().toEntity();
            Attribute primaryKey = entityObject.getFirstAttribute(EAttributeConstraint.PRIMARY_KEY);
            String condition = primaryKey.getName() + " >= " + minTargetID;
            condition += " AND " + primaryKey.getName() + " <= " + maxTargetID;

            Entity.getPool().delete(entityClass, requestFlag, condition);
        }
        catch (Exception e)
        {
            String msg = "Failed to delete records in the provided range: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }



    //-----------------------------------------------------------[Retrieve Queries]-----------------------------------------------------------
    public static <EntityType extends Entity<EntityType>> void
    retrieveAllRecords(Class<EntityType> entityClass, QueryRequestFlag<ArrayList<EntityType>> requestFlag)
    {
        try
        {
            Entity.getPool().retrieveAll(entityClass, requestFlag);
        }
        catch (Exception e)
        {
            String msg = "Failed to retrieve all records: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }

    public static <EntityType extends Entity<EntityType>> void
    retrieveRecord(Class<EntityType> entityClass, Integer targetID, final QueryRequestFlag<EntityType> requestFlag)
    {
        try
        {
            Entity.getPool().retrieve(entityClass, new QueryRequestFlag<ArrayList<EntityType>>()
            {
                @Override
                public void onQuerySuccess(ArrayList<EntityType> resultObject)
                {
                    if(resultObject != null)
                    {
                        EntityType object = resultObject.get(0);
                        requestFlag.onQuerySuccess(object);
                    }
                }

                @Override
                public void onQueryFailure(FailureResponse failure)
                {
                    failure.addNode(TAG);
                    requestFlag.onQueryFailure(failure);
                }
            }, targetID);
        }
        catch (Exception e)
        {
            String msg = "Failed to retrieve record: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }

    public static <EntityType extends Entity<EntityType>> void
    retrieveRecordsInRange(Class<EntityType> entityClass, Integer minTargetID, Integer maxTargetID, QueryRequestFlag<ArrayList<EntityType>> requestFlag)
    {
        try
        {
            EntityObject entityObject = entityClass.newInstance().toEntity();
            Attribute primaryKey = entityObject.getFirstAttribute(EAttributeConstraint.PRIMARY_KEY);
            String condition = primaryKey.getName() + " >= " + minTargetID;
            condition += " AND " + primaryKey.getName() + " <= " + maxTargetID;

            Entity.getPool().retrieve(entityClass, requestFlag, "*", condition);
        }
        catch (Exception e)
        {
            String msg = "Failed to retrieve records in the provided range: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }

}
