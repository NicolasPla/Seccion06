package com.example.seccion06;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Switch switchRemember;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        bindUI();
        setCredentialsIfExist();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(login(email, password)){

                    saveOnPreferences(email, password);
                    goToMain();
                }
            }
        });
    }

    private void bindUI (){

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        switchRemember = findViewById(R.id.switchRemember);
        btnLogin = findViewById(R.id.buttonLogin);
    }

    private void setCredentialsIfExist(){
        String email = getUserMailPrefs();
        String password = getUserPassPrefs();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            editTextEmail.setText(email);
            editTextPassword.setText(password);
        }
    }

    private boolean login(String email, String password){
        if (!isValidEmail(email)){
            Toast.makeText(this,"Email is not valid, please try again", Toast.LENGTH_LONG).show();
            return false;
        } else if (!isValidPassword(password)) {
            Toast.makeText(this, "Email is not valid, please try again", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    private void saveOnPreferences(String email, String password){
        if (switchRemember.isChecked()){

            // Objeto editor para poder escribir en los shared preferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.putString("pass", password);
            // editor.commit();  // Frena el codigo hasta guardar los archivos Syncronized
            editor.apply();   // El codigo sigue mientras en segundo plano se guardan los archivos Asyncronized
        }
    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidPassword(String password){

        return password.length() > 4;
    }

    private void goToMain(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private String getUserMailPrefs(){
        return prefs.getString("email", "");
    }

    private String getUserPassPrefs(){
        return prefs.getString("pass", "");
    }

}



