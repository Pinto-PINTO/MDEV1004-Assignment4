package ca.mdev1004.assignment4.models;

import com.squareup.moshi.Json;

public class BasicResponse
{
    @Json(name = "success")
    private boolean success;
    @Json(name = "msg")
    private String message;

    public boolean isSuccess()
    {
        return success;
    }

    public String getMessage()
    {
        return message;
    }
}
