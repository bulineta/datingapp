package firecupon.firecupon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    Button btnLogin;
    EditText input_email,input_password;
    TextView btnSignup,btnForgetPass;

    RelativeLayout activity_Login;

    private FirebaseAuth auth;


   @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login);

       //view
       btnLogin = (Button)findViewById(R.id.login_btn_login);
       input_email = (EditText)findViewById(R.id.login_input_email);
       input_password = (EditText)findViewById(R.id.login_input_password);
       btnSignup = (TextView)findViewById(R.id.login_btn_signup);
       btnForgetPass = (TextView)findViewById(R.id.login_btn_forgot_password);

       btnSignup.setOnClickListener(this);
       btnForgetPass.setOnClickListener(this);
       btnForgetPass.setOnClickListener(this);

       //init firebase auth
       auth = FirebaseAuth.getInstance();

       //check already session, if ok-> dashboard
       if(auth.getCurrentUser() != null)
       startActivity(new Intent(LoginActivity.this,DashBoard.class));

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login_btn_forgot_password)
        {
           startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
            finish();
        }
        else if(view.getId() == R.id.login_btn_signup)
        {
            startActivity(new Intent(LoginActivity.this,SignUp.class));
            finish();
        }
        else if(view.getId() == R.id.login_btn_login)
        {
           loginUser(input_email.getText().toString(),input_password.getText().toString());
        }

    }

    private void loginUser(String email, final String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(!task.isSuccessful())
                       {
                           if(password.length() < 6)
                           {
                               Snackbar snackBar = Snackbar.make(activity_Login, "Password lenght must be over 6 characters", Snackbar.LENGTH_SHORT);
                               snackBar.show();
                           }
                       }
                       else{
                           startActivity(new Intent(LoginActivity.this,DashBoard.class));
                       }
                    }
                });
    }
}

