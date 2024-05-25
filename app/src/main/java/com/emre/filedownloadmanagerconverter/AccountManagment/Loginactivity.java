package com.emre.filedownloadmanagerconverter.AccountManagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emre.filedownloadmanagerconverter.ui.MainActivity;
import com.emre.filedownloadmanagerconverter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Loginactivity extends AppCompatActivity {

    EditText LoginEmail,Loginpassword;
    Button signInBtn;

    TextView signUpText;

    FirebaseAuth auth;
    FirebaseUser currentuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        LoginEmail=findViewById(R.id.emailsignin);
        Loginpassword= findViewById(R.id.passwordsignin);
        signInBtn=findViewById(R.id.signinbutton);
        signUpText=findViewById(R.id.signuptext);
        auth=FirebaseAuth.getInstance();
        currentuser= auth.getCurrentUser();


        if(currentuser!=null){
            startActivity(new Intent(Loginactivity.this, MainActivity.class));
            finish();
        }
        else{

            signInBtn.setOnClickListener(v -> {



                String emailText=LoginEmail.getText().toString().trim();
                String passText=Loginpassword.getText().toString().trim();

                if(TextUtils.isEmpty(emailText)){
                    LoginEmail.setError("Email dont exist!");
                }
                if(TextUtils.isEmpty(passText)){
                    Loginpassword.setError("Password dont exist!");
                }

                if(!emailText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
                    if(!passText.isEmpty()){
                        auth.signInWithEmailAndPassword(emailText,passText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(Loginactivity.this, "Login Succesful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Loginactivity.this,MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Loginactivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });

            signUpText.setOnClickListener(v -> {
                startActivity(new Intent(Loginactivity.this,SignUpAcitvity.class));
                finish();
            });

        }

    }


}