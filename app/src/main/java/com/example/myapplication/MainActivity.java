package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText edtusername,edtpassword;
    private Button btnlogin;
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListeners();
    }

    private void setListeners() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = new Request.Builder().url("https://api.myjson.com/bins/arlt2").build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String data = null;
                                try {
                                    data = response.body().string();
                                    if(validateAccounts(data)){
                                        Toast.makeText(MainActivity.this, "Account Valid", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(MainActivity.this,gamelist.class);
                                        startActivity(i);
                                    }else
                                        Toast.makeText(MainActivity.this, "Account Invalid", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private boolean validateAccounts(String data){
        try {
            JSONObject dataObject = new JSONObject(data);
            JSONArray accountArray = dataObject.getJSONArray("accounts");

            String inputUsername = edtusername.getText().toString();
            String inputPassword = edtpassword.getText().toString();

            inputPassword = SHA256(inputPassword);

            for(int i=0 ; i<accountArray.length(); i++){
                JSONObject accountObject = accountArray.getJSONObject(i);

                String validationUsername = accountObject.getString("username");
                String validationPassword = accountObject.getString("password");

                if(inputUsername.equals(validationUsername) && inputPassword.equals(validationPassword)){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String SHA256 (String text) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(text.getBytes(StandardCharsets.UTF_8));

        BigInteger number = new BigInteger(1,hash);

        StringBuilder hexString = new StringBuilder((number.toString(16)));
        while (hexString.length() < 32){
            hexString.insert(0,"0");
        }
        return  hexString.toString();
    }

    private void findViews() {
        edtusername = findViewById(R.id.editname);
        edtpassword = findViewById(R.id.editpw);
        btnlogin = findViewById(R.id.buttonlogin);
    }
}
