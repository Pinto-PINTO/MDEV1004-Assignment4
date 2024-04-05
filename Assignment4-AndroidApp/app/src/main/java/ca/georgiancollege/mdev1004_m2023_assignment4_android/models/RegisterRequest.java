package ca.georgiancollege.mdev1004_m2023_assignment4_android.models;

import com.squareup.moshi.Json;

public class RegisterRequest
{
    @Json(name = "FirstName")
    private String firstName;
    @Json(name = "LastName")
    private String lastName;
    @Json(name = "EmailAddress")
    private String emailAddress;
    @Json(name = "username")
    private String username;
    @Json(name = "password")
    private String password;

    public RegisterRequest(String firstName, String lastName, String emailAddress, String username, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
}
