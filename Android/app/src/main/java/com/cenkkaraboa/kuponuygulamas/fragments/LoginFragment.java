package com.cenkkaraboa.kuponuygulamas.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.activitites.IntroActivity;
import com.cenkkaraboa.kuponuygulamas.activitites.LoginActivity;
import com.cenkkaraboa.kuponuygulamas.activitites.MainActivity;
import com.cenkkaraboa.kuponuygulamas.models.Register;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.kuponuygulamas.Utils.Utils.interfaces;


public class LoginFragment extends Fragment  {

    MaterialCardView go;
    View view;
    CheckBox checkBox;
    EditText password,email;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
MaterialCardView google,facebook;
    private static final String TAG = "FacebookLogin";
    private static final int RC_SIGN = 12345;

    private CallbackManager mCallbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_login, container, false);

        go=view.findViewById(R.id.go);
        checkBox=view.findViewById(R.id.checkbox);
        password=view.findViewById(R.id.password);
        google=view.findViewById(R.id.google);
        facebook=view.findViewById(R.id.facebook);
        email=view.findViewById(R.id.email);
    //    FirebaseAuth.getInstance().signOut();




        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();

                mCallbackManager = CallbackManager.Factory.create();
                createRequest();
                signIn();
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();

                // Initialize Facebook Login button
                mCallbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("email","public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        System.out.println("sadsadasfsa burada");
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().length() != 0 && password.getText().toString().length() != 0 ){

                    Callback<Register> listCallBack = new Callback<Register>() {
                        @Override
                        public void onResponse(Call<Register> call, Response<Register> response) {
                            if (response.isSuccessful()) {

                                Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                if (response.body().isResult()){
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    if(checkBox.isChecked()){
                                        editor.putBoolean("firstTime",true);

                                    }
                                    System.out.println(response.body().getID()+"asdasdsadasdasdasd");
                                        editor.putString("ID",response.body().getID());
                                        editor.putInt("search",Integer.parseInt(response.body().getSearch()));
                                        editor.putInt("category",Integer.parseInt(response.body().getCategory()));
                                        editor.apply();

                                    Intent intent=new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    ((LoginActivity)requireActivity()).finish();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Register> call, Throwable t) {
                            System.out.println(t.getCause());
                            System.out.println(t.getMessage()+"cececece");
                        }
                    };
                    interfaces.login("login",email.getText().toString(),password.getText().toString()).enqueue(listCallBack);


                }else {
                    if(email.getText().toString().length()==0){
                        Toast.makeText(getContext(),"Email Adresi Giriniz",Toast.LENGTH_SHORT).show();

                    }else if(password.getText().toString().length()==0){
                        Toast.makeText(getContext(),"Şifre Giriniz",Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });

        return  view;

    }


    private void createRequest() {


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, UI will update with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Authentication Succeeded.", Toast.LENGTH_SHORT).show();

                            System.out.println("asdsadasdasdsada");





                        } else {
                            // If sign-in fails, a message will display to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = (GoogleSignInAccount) task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();


                            Callback<Register> listCallBack = new Callback<Register>() {
                                @Override
                                public void onResponse(Call<Register> call, Response<Register> response) {
                                    if (response.isSuccessful()) {

                                        Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                        if (response.body().isResult()){
                                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putBoolean("firstTime",true);
                                            System.out.println(response.body().getID()+"asdasdsadasdasdasd");
                                            editor.putString("ID",response.body().getID());
                                            editor.putInt("search",Integer.parseInt(response.body().getSearch()));
                                            editor.putInt("category",Integer.parseInt(response.body().getCategory()));
                                            editor.apply();

                                            Intent intent=new Intent(getContext(), MainActivity.class);
                                            startActivity(intent);
                                            ((LoginActivity)requireActivity()).finish();
                                        }else{
                                            Callback<Register> listCallBack = new Callback<Register>() {
                                                @Override
                                                public void onResponse(Call<Register> call, Response<Register> response) {
                                                    if (response.isSuccessful()) {

                                                        Toast.makeText(getContext(),response.body().getID(),Toast.LENGTH_SHORT).show();
                                                        if (response.body().isResult()){
                                                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                                            editor.putBoolean("firstTime",true);
                                                            editor.putString("ID","");
                                                            System.out.println(response.body().getID()+"asdasdsadasdasdasd");

                                                            editor.putString("ID",response.body().getID());
                                                            editor.putInt("search",Integer.parseInt(response.body().getSearch()));
                                                            editor.putInt("category",Integer.parseInt(response.body().getCategory()));
                                                            editor.apply();



                                                            Intent intent=new Intent(getContext(), MainActivity.class);
                                                            startActivity(intent);
                                                            ((LoginActivity)requireActivity()).finish();
                                                        }
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<Register> call, Throwable t) {
                                                    System.out.println(t.getCause());
                                                    System.out.println(t.getMessage()+"cececece");
                                                }
                                            };
                                            interfaces.registerSosyal("register",user.getEmail(),"", "","","","",user.getDisplayName(),"",user.getUid()).enqueue(listCallBack);

                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<Register> call, Throwable t) {
                                    System.out.println(t.getCause());
                                    System.out.println(t.getMessage()+"cececece");
                                }
                            };
                            interfaces.loginSosyal("login",user.getUid()).enqueue(listCallBack);


                           /* Intent intent = new Intent(getContext(),LoginFragment.class);
                            startActivity(intent);*/


                        } else {
                            Toast.makeText(getContext(), "Sorry auth failed.", Toast.LENGTH_SHORT).show();


                        }


                        // ...
                    }
                });
    }




}


