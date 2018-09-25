package com.example.hi.reminder;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*import com.example.hi.reminder.*;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
*/

public class Login extends AppCompatActivity {

    private View contanier;
    private View hello;
    private View profile;
    private boolean playAnimation=true;
    private ProgressDialog progressDialog;
    Button login;
    EditText pass;
    EditText uname;
    //LoginButton loginButton;
    //CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        contanier=findViewById(R.id.container);
        hello=findViewById(R.id.welcome);
        profile=findViewById(R.id.profile);

        login=(Button) findViewById(R.id.sign_in);
        pass=(EditText) findViewById(R.id.password);
        uname=(EditText) findViewById(R.id.username);

        progressDialog=new ProgressDialog(this);

        //intilization();
       // LoginWithFb();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus && playAnimation)
        {
            showContainer();
            showOtherItems();

            playAnimation=false;
        }
    }

    private void showOtherItems()
    {
        float startXhello=0-hello.getWidth();
        float endXhello=hello.getX();

        ObjectAnimator animator=ObjectAnimator.ofFloat(hello,View.X,startXhello,endXhello);
        animator.setDuration(500);
        hello.setVisibility(View.VISIBLE);
        animator.start();

        PropertyValuesHolder scaleXHolder=PropertyValuesHolder.ofFloat(View.SCALE_X,1f);
        PropertyValuesHolder scaleYHolder=PropertyValuesHolder.ofFloat(View.SCALE_Y,1f);

        ObjectAnimator Animprofile=ObjectAnimator.ofPropertyValuesHolder(profile,scaleXHolder,scaleYHolder);
        Animprofile.setDuration(500);
        Animprofile.start();


    }

    private void showContainer()
    {
        contanier.animate().alpha(1f).setDuration(1000);
    }
    public void btnlogin(View view)
    {
        String psw=pass.getText().toString().trim();
        String user=uname.getText().toString().trim();
        if(TextUtils.isEmpty(user))
        {

            Snackbar snackbar=Snackbar.make(contanier,"Please enter user name",Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;
        }
        if (TextUtils.isEmpty(psw))
        {

            Snackbar snackbar=Snackbar.make(contanier,"Please enter password",Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;
        }
        progressDialog.setMessage("Login Successfully !!!!!!");
        progressDialog.show();
        Intent i=new Intent(Login.this,Splash.class);
        startActivity(i);

    }
   /* private void intilization()
    {
        callbackManager=CallbackManager.Factory.create();
        loginButton=(LoginButton) findViewById(R.id.login_button);

    }
    private void LoginWithFb()
    {

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Snackbar snackbar=Snackbar.make(loginButton,"Login Successfully!!!",Snackbar.LENGTH_SHORT);
                snackbar.show();
                //Intent i=new Intent(Login.this,Splash.class);
                //startActivity(i);
            }

            @Override
            public void onCancel() {
                Snackbar snackbar=Snackbar.make(loginButton,"Login Cancel!!!",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

            @Override
            public void onError(FacebookException error) {
                Snackbar snackbar=Snackbar.make(loginButton,"Login Error!!!",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }*/
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/
}
