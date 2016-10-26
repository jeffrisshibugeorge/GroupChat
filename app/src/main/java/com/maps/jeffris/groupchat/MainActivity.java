package com.maps.jeffris.groupchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener  {

    private Button buttonReg;
    private EditText user,passwd;
    private TextView signin;

    private ProgressDialog progress;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();

        buttonReg=(Button) findViewById(R.id.buttonReg);
        user=(EditText) findViewById(R.id.username);
        passwd=(EditText) findViewById(R.id.password);

        signin=(TextView) findViewById(R.id.textsigin);
        progress=new ProgressDialog(this);

        buttonReg.setOnClickListener(this);
        signin.setOnClickListener(this);

    }

    public void registeruser(){
        final String email=user.getText().toString().trim();
        String pwd=passwd.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please Enter Email!!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pwd))
        {
            Toast.makeText(this, "Please Enter Password!!", Toast.LENGTH_LONG).show();
            return;
        }

        progress.setMessage("Registering User....");
        progress.show();
        firebaseAuth.createUserWithEmailAndPassword(email,pwd);

                                    Toast.makeText(MainActivity.this,"Registration Succesful", Toast.LENGTH_LONG).show();
                                    progress.hide();
                                    Intent intent = new Intent(getApplicationContext(),chatroom.class);
                                    intent.putExtra("user_name",email);
                                    startActivity(intent);



    }




    @Override
    public void onClick(View view) {

        if(view==buttonReg)
        {

            registeruser();
        }
        if(view==signin) {

            //Login activity
        Intent intent = new Intent(getApplicationContext(),Registration.class);
            startActivity(intent);
        }
    }

}
