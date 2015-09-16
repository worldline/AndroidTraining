package worldline.ssm.rd.ux.wltwitter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WLTwitterLoginActivity extends Activity
        implements View.OnClickListener {

    // The EditText in which the user type login
    private EditText mLoginEditText;

    // The EditText in which the user type password
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Keep a reference to the EditText
        mLoginEditText = (EditText) findViewById(R.id.loginEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
    }

    @Override
    public void onClick(View v) {

        // Check if a login is set
        if (TextUtils.isEmpty(mLoginEditText.getText())){
            // Display a Toast to the user
            Toast.makeText(this, R.string.error_no_login, Toast.LENGTH_LONG).show();
            return;
        }

        // Check if a password is set
        if (TextUtils.isEmpty(mPasswordEditText.getText())){
            // Display a Toast to the user
            Toast.makeText(this, R.string.error_no_password, Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, WLTwitterActivity.class);
        startActivity(intent);

    }
}


