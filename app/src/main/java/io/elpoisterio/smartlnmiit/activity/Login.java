package io.elpoisterio.smartlnmiit.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.elpoisterio.smartlnmiit.R;
import io.elpoisterio.smartlnmiit.restClient.RestManager;
import io.elpoisterio.smartlnmiit.utilities.CheckInternetConnection;
import io.elpoisterio.smartlnmiit.utilities.HandlerConstant;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button loginButton;
    EditText email;
    EditText password;
    Button signUpButton;
    static Handler handler = new Handler();
    ProgressDialog dialog;
    private Context context = Login.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        updateUi();



    }
    private void initView(){
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        loginButton = (Button)findViewById(R.id.login);
        signUpButton = (Button) findViewById(R.id.sign_up);

        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }
    private void updateUi(){

        handler = new Handler(Looper.getMainLooper()){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == HandlerConstant.FAILURE){
                    Toast.makeText(Login.this,"Could not log in",Toast.LENGTH_SHORT).show();
                }else {
                    moveToHome();
                }
                hideDialog(dialog);
            }
        };
    }

    private boolean checkEmptyFields(){
        if(email.getText().toString().length()==0) {
            Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.getText().toString().length() == 0 ){
            Toast.makeText(Login.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    public static boolean isEmailValid(CharSequence email) {

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        System.out.println(matcher);
        return matcher.matches();
    }

    @Override
    public void onClick(View v) {
        if (v == loginButton){
            checkEmptyFields();
            if(!isEmailValid(email.getText().toString())){
                Toast.makeText(Login.this, "Please use email id provided by college", Toast.LENGTH_SHORT).show();
            } else {
                if(!new CheckInternetConnection(Login.this).isConnectedToInternet()){
                    Toast.makeText(context,"No internet connection",Toast.LENGTH_SHORT).show();
                }else {
                    callApi();
                }

            }

        } else if(v == signUpButton){
            moveToSignUpScreen();
        }
    }

    private void callApi() {

        dialog = new ProgressDialog(Login.this);
        dialog.setCancelable(false);
        dialog.setTitle("Logging in....");
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                new RestManager().getInstance().login(Login.this, email.getText().toString(), password.getText().toString(),handler);
                Looper.loop();

            }
        });

    }

    private void moveToSignUpScreen() {
        Intent intent = new Intent(Login.this , Home.class);
        startActivity(intent);
        finish();
    }

    private void moveToHome() {
        Intent intent = new Intent(Login.this , ChooseType.class);
        startActivity(intent);
        finish();
    }

    private void hideDialog(ProgressDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            Log.i("HeyThere ", "hide dialog is running !!!");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
