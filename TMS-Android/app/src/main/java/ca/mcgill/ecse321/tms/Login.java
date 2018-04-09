package ca.mcgill.ecse321.tms;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class Login extends Activity {

    // Error
    private String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
    }

    public void authenticate(View v){
        TextView tv = (TextView) findViewById(R.id.user_name);
        final String username = tv.getText().toString();
        tv = (TextView) findViewById(R.id.password);
        final String password = tv.getText().toString();

        RequestParams rp = new RequestParams();
        rp.add("password", password);

        HttpUtils.post("user/"+username, rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                if(statusCode == 200){
                    MainActivity.username=username;
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });

    }

    public void registerUser(View v){
        TextView tv = (TextView) findViewById(R.id.user_name);
        final String username = tv.getText().toString();
        tv = (TextView) findViewById(R.id.password);
        final String password = tv.getText().toString();

        RequestParams rp = new RequestParams();
        rp.add("password", password);
        rp.add("inputToken", "");

        HttpUtils.post("users/"+username, rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                if(statusCode == 200){
                    MainActivity.username = username;
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });

    }

    private void refreshErrorMessage() {
//        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error_message_login);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }

    }
}
