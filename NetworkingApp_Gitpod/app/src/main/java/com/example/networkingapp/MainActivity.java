package com.example.networkingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button btnRetrofit, btnVolley, btnPost;
    private TextView txtResult;
    private ProgressBar progressBar;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRetrofit = findViewById(R.id.btnRetrofit);
        btnVolley = findViewById(R.id.btnVolley);
        btnPost = findViewById(R.id.btnPost);
        txtResult = findViewById(R.id.txtResult);
        progressBar = findViewById(R.id.progressBar);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        btnRetrofit.setOnClickListener(v -> loadPostWithRetrofit());
        btnVolley.setOnClickListener(v -> loadPostWithVolley());
        btnPost.setOnClickListener(v -> createPost());
    }

    private void showLoading(String message) {
        progressBar.setVisibility(View.VISIBLE);
        setButtonsEnabled(false);
        txtResult.setText(message);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        setButtonsEnabled(true);
    }

    private void setButtonsEnabled(boolean enabled) {
        btnRetrofit.setEnabled(enabled);
        btnVolley.setEnabled(enabled);
        btnPost.setEnabled(enabled);
    }

    private void loadPostWithRetrofit() {
        showLoading("Connecting with Retrofit...");
        ApiService api = RetrofitClient.getApiService();
        Call<Post> call = api.getPost(1);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    Post post = response.body();
                    txtResult.setText("SUCCESS • RETROFIT\n\n"
                            + "ID: " + post.getId()
                            + "\nUser ID: " + post.getUserId()
                            + "\n\nTitle\n" + post.getTitle()
                            + "\n\nBody\n" + post.getBody());
                } else {
                    txtResult.setText("API error: HTTP " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                hideLoading();
                txtResult.setText("Network error: " + getErrorMessage(t));
            }
        });
    }

    private void loadPostWithVolley() {
        showLoading("Connecting with Volley...");
        String url = "https://jsonplaceholder.typicode.com/posts/1";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    String result = "SUCCESS • VOLLEY\n\n"
                            + "ID: " + response.optInt("id")
                            + "\nUser ID: " + response.optInt("userId")
                            + "\n\nTitle\n" + response.optString("title")
                            + "\n\nBody\n" + response.optString("body");
                    txtResult.setText(result);
                    hideLoading();
                },
                error -> {
                    hideLoading();
                    txtResult.setText("Volley error: " + getErrorMessage(error));
                });
        requestQueue.add(request);
    }

    private void createPost() {
        showLoading("Sending new post...");
        String url = "https://jsonplaceholder.typicode.com/posts";
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("title", "Hello from Android");
            requestBody.put("body", "This is a sample POST request");
            requestBody.put("userId", 1);
        } catch (Exception e) {
            hideLoading();
            txtResult.setText("Could not prepare post data: " + getErrorMessage(e));
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, requestBody,
                response -> {
                    int newId = response.optInt("id");
                    txtResult.setText("SUCCESS • VOLLEY POST\n\n"
                            + "Your sample post was sent.\n"
                            + "Created post ID: " + newId
                            + "\n\nNote: JSONPlaceholder simulates creating a post; it does not permanently save it.");
                    hideLoading();
                },
                error -> {
                    hideLoading();
                    txtResult.setText("POST failed: " + getErrorMessage(error));
                });
        requestQueue.add(request);
    }

    private String getErrorMessage(Throwable error) {
        if (error == null || error.getMessage() == null) {
            return "Please check your internet connection and try again.";
        }
        return error.getMessage();
    }
}
