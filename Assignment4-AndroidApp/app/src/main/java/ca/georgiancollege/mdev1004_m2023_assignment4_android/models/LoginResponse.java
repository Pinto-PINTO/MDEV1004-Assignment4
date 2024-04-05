// File Name: LoginResponse.java
// Student Name: Rajat Rajat
// Student ID: 200519561
// Date: 17th August 2023
package ca.georgiancollege.mdev1004_m2023_assignment4_android.models;
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
