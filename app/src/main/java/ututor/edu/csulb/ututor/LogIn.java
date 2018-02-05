package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;


public class LogIn extends AppCompatActivity{

    public EditText mEmail;
    public EditText mPassword;
    public Button mSignIn;
    public Button mSignUp;
    public String email;
    public String password;
    public CheckBox mRemember;
    public ImageView mLogo;
    public TextView mTitle;
    public TextView mForgot;
    public TextView mAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mSignIn = (Button) findViewById(R.id.bSignInButton);
        mSignUp = (Button) findViewById(R.id.bSignUpButton);
        mRemember = (CheckBox) findViewById(R.id.rememberMe);
        mLogo = (ImageView) findViewById(R.id.logo);
        mTitle = (TextView) findViewById(R.id.titleLogo);
        mAlert = (TextView) findViewById(R.id.alertPassword);
        //mForgot = (TextView) findViewById(R.id.forgotPassword);

        Animation expandIn = AnimationUtils.loadAnimation(this, R.anim.expand);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1500);

        // start loading animation
        mLogo.startAnimation(expandIn);
        mEmail.setAnimation(fadeIn);
        mPassword.setAnimation(fadeIn);
        mSignIn.setAnimation(fadeIn);
        mSignUp.setAnimation(fadeIn);
        mTitle.setAnimation(fadeIn);
        mRemember.setAnimation(fadeIn);
        //mForgot.setAnimation(fadeIn);


        //load shared preferences to check if Remember Me was clicked
        SharedPreferences sp = getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        boolean checkSaved = sp.getBoolean("isSaved", false);

        if(checkSaved == true){
            String insertEmail = sp.getString("Email", null);
            String insertPassword = sp.getString("Password", null);

            mEmail.setText(insertEmail);
            mPassword.setText(insertPassword);
            mRemember.setChecked(true);
        }



        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to Registration Activity
                startActivity(new Intent(LogIn.this, Registration.class));
                finish();
            }
        });


        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // first set alert invisible
                mAlert.setVisibility(View.INVISIBLE);

                // validate email & password, so access database
                String databasePassword = "yes";

                if(mEmail.getText().toString().equals("")){ // if email is blank
                    mAlert.setVisibility(View.VISIBLE);
                    Animation shake = AnimationUtils.loadAnimation(LogIn.this, R.anim.shake);
                    mAlert.startAnimation(shake);

                } else if(mPassword.getText().toString().equals(databasePassword)){

                    // if remember me is selected
                    if(mRemember.isChecked()){
                        String sEmail = mEmail.getText().toString();
                        String sPass = mPassword.getText().toString();

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("isSaved", true);
                        editor.putString("Email", sEmail);
                        editor.putString("Password", sPass);
                        editor.commit();
                    }
                    else{

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.apply();

                    }

                    //upload server info about User to the User class
                    // get info from Database and set them to the User class
                    // for now, I've just set random values
                    User cUser = new User();
                    cUser.setEmail(mEmail.getText().toString());
                    cUser.setTutor(true);
                    cUser.setFirstName("[First Name]");
                    cUser.setLastName("[Last Name]");
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ututorlogo); // drawable to bitmap
                    cUser.setProfilePic(bitmap);

                    // send user info to HomePage
                    Intent i = new Intent(LogIn.this, HomePage.class);
                    i.putExtra("currentUser", cUser);
                    startActivity(i);
                    finish();

                }
                else{ // password is wrong, give user notification.
                    mAlert.setVisibility(View.VISIBLE);
                    Animation shake = AnimationUtils.loadAnimation(LogIn.this, R.anim.shake);
                    mAlert.startAnimation(shake);
                }


            }
        });


    }


    /**
     * Temporary Skip button until database is created
     * @param view
     */
    public void byPassLogIn(View view){

        //validate username and email in the database


        // TEMPORARY - set random variables
        mEmail.setText("aenah.ramones@gmail.com");
        mPassword.setText("1234");

        // if remember me is selected
        if(mRemember.isChecked()){
            String sEmail = mEmail.getText().toString();
            String sPass = mPassword.getText().toString();

            SharedPreferences pref = getApplicationContext().getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isSaved", true);
            editor.putString("Email", sEmail);
            editor.putString("Password", sPass);
            editor.commit();
        }
        else{
            SharedPreferences pref = getApplicationContext().getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.apply();

        }

        //upload server info about User to the User class
        User cUser = new User();
        cUser.setEmail(mEmail.getText().toString());
        cUser.setTutor(false);
        cUser.setFirstName("Aenah");
        cUser.setLastName("Ramones");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ututorlogo); // drawable to bitmap
        cUser.setProfilePic(bitmap);

        // send user info to HomePage
        Intent i = new Intent(LogIn.this, HomePage.class);
        i.putExtra("currentUser", cUser);
        startActivity(i);
        finish();


    }
}

