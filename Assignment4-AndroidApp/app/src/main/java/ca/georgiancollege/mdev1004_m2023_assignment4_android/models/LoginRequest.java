// File Name: LoginRequest.java
// Student Name: Rajat Rajat
// Student ID: 200519561
// Date: 17th August 2023
package ca.georgiancollege.mdev1004_m2023_assignment4_android.models;

import com.squareup.moshi.Json;

public class LoginRequest
{
    @Json(name = "username") String username;
    @Json(name = "password") String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
