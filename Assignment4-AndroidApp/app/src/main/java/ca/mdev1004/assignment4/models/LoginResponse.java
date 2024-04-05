// File Name: LoginResponse.java
// Student Name 1: Menuka Pinto (ID: 200574919)
// Student Name 2: Lathindu Vidanagama (ID: 200574922)
// Date: 5th April 2024
package ca.mdev1004.assignment4.models;
import com.squareup.moshi.Json;

public class LoginResponse extends BasicResponse {
    @Json(name = "user") private Object user;
    @Json(name = "token") private String token;

    public Object getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public static class User {
        @Json(name = "id") private String id;
        @Json(name = "displayName") private String displayName;
        @Json(name = "username") private String username;
        @Json(name = "emailAddress") private String emailAddress;

        public String getId() {
            return id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getUsername() {
            return username;
        }

        public String getEmailAddress() {
            return emailAddress;
        }
    }
}
