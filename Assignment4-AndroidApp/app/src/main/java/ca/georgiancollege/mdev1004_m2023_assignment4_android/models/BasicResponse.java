package ca.georgiancollege.mdev1004_m2023_assignment4_android.models;

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
