// File Name: LoginActivity.java
// Student Name 1: Menuka Pinto (ID: 200574919)
// Student Name 2: Lathindu Vidanagama (ID: 200574922)
// Date: 5th April 2024
package ca.mdev1004.assignment4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import ca.mdev1004.assignment4.R;
import ca.mdev1004.assignment4.models.LoginRequest;
import ca.mdev1004.assignment4.models.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class LoginActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view ->
        {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            loginUser(username, password);
        });

        Button registerButton = findViewById(R.id.signUpButton);
        registerButton.setOnClickListener(view ->
        {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String username, String password)
    {

        APIService apiService = new Retrofit.Builder()
                .baseUrl("https://5bc5-2607-fea8-6521-9d00-4cbd-bf74-b068-767d.ngrok-free.app/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(APIService.class);

        LoginRequest loginRequest = new LoginRequest(username, password);


        Call<LoginResponse> call = apiService.loginUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>()
        {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                if (response.isSuccessful())
                {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.isSuccess())
                    {
                        // User logged in successfully
                        // Save the token in SharedPreferences for later use
                        String token = loginResponse.getToken();
                        saveAuthToken(token);

                        // Proceed to the next screen, for example, start the CRUDActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else
                    {
                        // Login failed
                        Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                } else
                {
                    // Error handling for unsuccessful login
                    if (response.errorBody() != null)
                    {
                        try
                        {
                            String errorMessage = response.errorBody().string();
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t)
            {
                // Error handling for network failures
                Toast.makeText(LoginActivity.this, "Failed to connect to the server." + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to save the auth token in SharedPreferences
    private void saveAuthToken(String token)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("AuthToken", token);
        editor.apply();
    }
}