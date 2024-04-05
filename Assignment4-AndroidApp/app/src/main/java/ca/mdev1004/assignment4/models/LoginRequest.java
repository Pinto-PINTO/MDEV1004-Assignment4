// File Name: LoginRequest.java
// Student Name 1: Menuka Pinto (ID: 200574919)
// Student Name 2: Lathindu Vidanagama (ID: 200574922)
// Date: 5th April 2024
package ca.mdev1004.assignment4.models;

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
