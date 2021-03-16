package com.example.elpaseov4.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elpaseov4.R;
import com.example.elpaseov4.model.User;
import com.example.elpaseov4.model.UserLogin;
import com.example.elpaseov4.network.LoginUser;
import com.example.elpaseov4.network.RetrofitClientInstance;
import com.example.elpaseov4.network.ServiceRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {
    EditText email;
    EditText encryptedPassword;
    Button buttonLogin;
    private ImageButton facebook;
    private ImageButton instagram;
    private ServiceRetrofit service;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public LoginFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email = view.findViewById(R.id.editTextUser);
        encryptedPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Logueando", Toast.LENGTH_LONG).show();
                String name = email.getText().toString();
                String password = encryptedPassword.getText().toString();

                LoginUser loginUser = new LoginUser(name,password);

                service = RetrofitClientInstance.getRetrofitInstance().create(ServiceRetrofit.class);

                service.login(loginUser).enqueue(new Callback<UserLogin>() {
                    @Override
                    public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {

                        if (response.isSuccessful()){
                            response.body();
                            Toast.makeText(v.getContext(), "EXITO2222", Toast.LENGTH_LONG).show();
                            guardarSesion(response.body());

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container,new MainHomeFragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }else {
                            Toast.makeText(v.getContext(), "Datos erroneos", Toast.LENGTH_LONG).show();
                            Fragment loginFragment = new LoginFragment();

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container,loginFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }


                    @Override
                    public void onFailure(Call<UserLogin> call, Throwable t) {
                        System.out.println(t.getMessage());
                        Toast.makeText(v.getContext(), "Fracaso", Toast.LENGTH_LONG).show();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container,new MainHomeFragment());
                        fragmentTransaction.commit();
                    }
                });

            }
        });


        facebook = view.findViewById(R.id.facebookLogin);
        instagram = view.findViewById(R.id.instagramlogin);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "abrir Facebook" ,Toast.LENGTH_LONG).show();
                Uri link = Uri.parse("https://www.facebook.com/paseo.unlp");
                Intent i = new Intent(Intent.ACTION_VIEW,link);
                v.getContext().startActivity(i);
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "abrir Instagram" ,Toast.LENGTH_LONG).show();
                Uri link = Uri.parse("https://www.instagram.com/paseo.unlp/");
                Intent i = new Intent(Intent.ACTION_VIEW,link);
                v.getContext().startActivity(i);
            }
        });
        return view;
    }

    public void guardarSesion(UserLogin userLogin){
        preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("value",userLogin.getValue());
        editor.putString("email",userLogin.getUser().getEmail());
        editor.apply();
    }




}