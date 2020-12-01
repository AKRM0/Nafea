package com.ksu.nafea.data;

public class DatabaseException extends Exception
{
    private String failedOperation;

    public DatabaseException(String msg, String failedOperation)
    {
        super(msg);
        this.failedOperation = failedOperation;
    }

    public void setFailedOperation(String failedOperation)
    {
        this.failedOperation = failedOperation;
    }
    public String getFailedOperation()
    {
        return failedOperation;
    }


    public String getQueryErrorFormat()
    {
        return failedOperation + ":\n" + getMessage();
    }
}
