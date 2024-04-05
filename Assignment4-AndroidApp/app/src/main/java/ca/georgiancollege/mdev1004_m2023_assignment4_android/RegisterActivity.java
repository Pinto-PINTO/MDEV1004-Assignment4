// File Name: RegisterActivity.java
// Student Name: Rajat Rajat
// Student ID: 200519561
// Date: 17th August 2023
package ca.georgiancollege.mdev1004_m2023_assignment4_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.BasicResponse;
import ca.georgiancollege.mdev1004_m2023_assignment4_android.models.RegisterRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RegisterActivity extends AppCompatActivity
{


    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        findViewById(R.id.registerButton).setOnClickListener(v -> registerUser());
        findViewById(R.id.loginButton).setOnClickListener(v -> finish());
    }

    private void registerUser() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        // Perform validation checks (you can add more checks as needed)
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please enter all the required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password and Confirm Password do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(firstName, lastName, email, username, password);

        APIService apiService = new Retrofit.Builder()
                .baseUrl("https://beac-2607-fea8-6521-9d00-211a-3935-b416-4225.ngrok-free.app/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(APIService.class);

        Call<BasicResponse> call = apiService.registerUser(registerRequest);
        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("RegisterActivity", "User registered successfully.");
                    finish();
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String errorMessage = response.errorBody().string();
                            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                Log.e("RegisterActivity", "Failed to connect to the server.", t);
                Toast.makeText(RegisterActivity.this, "Failed to connect to the server.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}