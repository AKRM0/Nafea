package com.ksu.nafea.data;

public interface QueryRequestFlag
{
    public void onRequestSuccess(QueryResult result);
    public void onRequestFailure(DatabaseException error);
}
