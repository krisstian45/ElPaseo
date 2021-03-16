package com.example.elpaseov4.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elpaseov4.R;
import com.example.elpaseov4.fragments.CartFragment;
import com.example.elpaseov4.fragments.LoginFragment;
import com.example.elpaseov4.fragments.MainHomeFragment;
import com.example.elpaseov4.fragments.OrderDetailFragment;
import com.example.elpaseov4.fragments.OrdersFragment;
import com.example.elpaseov4.fragments.RegisterFragment;
import com.example.elpaseov4.fragments.SearchFragment;
import com.example.elpaseov4.model.User;
import com.example.elpaseov4.network.ServiceRetrofit;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView textViewUser;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static MainActivity activityMain;
    //Cargar usuario
    User user;

    private ServiceRetrofit service;

    //variables para cargar fragment
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMain=this;

        inicializarElementos();


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open,R.string.close);
        //cambiar icono
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //establecer evento onclick al navigationview

        revisarSesion();
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //cargar Fragment principal
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container,new MainHomeFragment());
        fragmentTransaction.commit();

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (item.getItemId() ){
            case R.id.login:Toast.makeText(getApplicationContext(), "Me voy a loguear", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                Fragment loginFragment = new LoginFragment();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,loginFragment);
                fragmentTransaction.commit();
                break;
            case R.id.home:
                fragmentTransaction.replace(R.id.container,new MainHomeFragment());
                fragmentTransaction.commit();
                break;
            case R.id.busqueda:
                fragmentTransaction.replace(R.id.container,new SearchFragment());
                fragmentTransaction.commit();
                break;
            case R.id.carrito:
                fragmentTransaction.replace(R.id.container,new CartFragment());
                fragmentTransaction.commit();
                break;
            case R.id.pedidos:
                fragmentTransaction.replace(R.id.container,new OrdersFragment());
                fragmentTransaction.commit();
                break;
            case R.id.logOut:
                btnLogOut();
                break;
        }
        return false;
    }

    public static MainActivity getmInstanceActivityMain(){
        return activityMain;
    }
    private boolean revisarSesion(){
        preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String mail = preferences.getString("email",null);
        //String mail = "juan pedro";
        if (mail == null ){
            System.out.println("entre al if");

            navigationView.getMenu().setGroupVisible(R.id.groupUserLogin,false);
        }else {
            System.out.println("entre al else");
            navigationView.getMenu().setGroupVisible(R.id.groupUserLogin,true);
            navigationView.getMenu().setGroupVisible(R.id.groupUserLoginIn,false);
            View hView = navigationView.getHeaderView(0);
            TextView user = (TextView) hView.findViewById(R.id.userHeader);
            user.setText(mail);

        }
        return false;
    }

    private void guardarSesion(){

    }
    private void inicializarElementos(){
        preferences = this.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();

        toolbar = findViewById(R.id.toolbarDrawer);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
    }

    public void login(View view){
        Toast.makeText(getApplicationContext(), "Me voy a loguear", Toast.LENGTH_LONG).show();
        drawerLayout.closeDrawer(GravityCompat.START);
        Fragment loginFragment = new LoginFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,loginFragment);
        fragmentTransaction.commit();
    }

    public void btnLogOut(){
        Toast.makeText(getApplicationContext(), "Cerrar Sesion", Toast.LENGTH_LONG).show();
        //SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().apply();

        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new MainHomeFragment());
        fragmentTransaction.commit();
    }

    public void register(View view){
        Toast.makeText(getApplicationContext(), "Me voy a Registrar", Toast.LENGTH_LONG).show();
        drawerLayout.closeDrawer(GravityCompat.START);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new RegisterFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void cancelRegister(View view){
        Toast.makeText(getApplicationContext(), "Cancelo", Toast.LENGTH_LONG).show();
        drawerLayout.closeDrawer(GravityCompat.START);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new MainHomeFragment());
        fragmentTransaction.commit();
    }
    public void payCart(View view){
        Toast.makeText(getApplicationContext(), "Ya pago", Toast.LENGTH_LONG).show();
        drawerLayout.closeDrawer(GravityCompat.START);
        createCart();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new OrderDetailFragment());
        fragmentTransaction.commit();

    }

    public void createCart(){
        String token = preferences.getString("value",null);
        String body = "{\n" +
                "    \"cartProducts\": [\n" +
                "        {\n" +
                "            \"product\": {\n" +
                "                \"id\": 2\n" +
                "            },\n" +
                "            \"quantity\": 2\n" +
                "        }\n" +
                "    ],\n" +
                "    \"nodeDate\": {\n" +
                "        \"id\": 8,\n" +
                "        \"node\": {\n" +
                "            \"id\": 5\n" +
                "        }\n" +
                "    },\n" +
                "    \"observation\": \"Una observación para el pedido\",\n" +
                "    \"user\": {\n" +
                "        \"id\": 617\n" +
                "    },\n" +
                "    \"general\": {\n" +
                "        \"id\": 6\n" +
                "    }\n" +
                "}";
        service.postCart(token,body).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Toast.makeText(getApplicationContext(), "se creo el carro", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No se creo el carro", Toast.LENGTH_LONG).show();

            }
        });
    }
}