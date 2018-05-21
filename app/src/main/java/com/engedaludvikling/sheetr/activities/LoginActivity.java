package com.engedaludvikling.sheetr.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.engedaludvikling.sheetr.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 1000;
    private static final String FACEBOOKTAG = "FACEBOOKTAG";

    @Nullable
    @BindView(R.id.login_button_facebook_hidden)
    LoginButton btnFbLogIn;
    private CallbackManager mCallbackManager;

    private GoogleApiClient mGoogleApiClient;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        initializeGoogleSignInHandler();
        initializeFacebookLoginHandler();
    }

    private void initializeGoogleSignInHandler() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }

    private void initializeFacebookLoginHandler() {
        mCallbackManager = CallbackManager.Factory.create();
        assert btnFbLogIn != null;
        btnFbLogIn.setReadPermissions("email", "public_profile");
        btnFbLogIn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(FACEBOOKTAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(FACEBOOKTAG, "facebook:onError", error);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        try {
            AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
            mAuth.signInWithCredential(credential)
                    .addOnSuccessListener(e -> {
                        onLoginSuccess();
                    }).addOnFailureListener(e -> {
                onLoginFailed(e.getMessage());
            });
        } catch (Exception e) {
            Log.e(FACEBOOKTAG, e.getLocalizedMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //showProgressDialog(getString(R.string.auth_progress_dialog_logging_in));

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                handleSignInResult(result);
            } else {
                Toast.makeText(this, "Connection with Google failed", Toast.LENGTH_SHORT).show();
            }
        } else
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            AuthCredential credential = GoogleAuthProvider.getCredential(acct != null ? acct.getIdToken() : null, null);
            mAuth.signInWithCredential(credential)
                    .addOnSuccessListener(authResult -> {
                        onLoginSuccess();
                    }).addOnFailureListener(authResult -> {
                onLoginFailed(authResult.getMessage());
            });
        } else {
            Toast.makeText(this, "ResultCode failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void onLoginFailed(String errorMessage) {
        /*dismissProgressDialog();
        Toasty.error(this, errorMessage, Toast.LENGTH_LONG).show();*/
    }

    private void onLoginSuccess() {
        /*dismissProgressDialog();
        Toasty.success(getContext(), getString(R.string.auth_logged_in)).show();*/
        Toast.makeText(this, "SUCCESS!", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    public void onFacebookButtonClick(View v) {
        assert btnFbLogIn != null;
        btnFbLogIn.performClick();
    }

    public void onGoogleButtonClick(View v) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google connection failed", Toast.LENGTH_SHORT).show();
    }
}
