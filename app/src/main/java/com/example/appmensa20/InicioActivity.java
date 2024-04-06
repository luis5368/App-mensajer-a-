package com.example.appmensa20;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InicioActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager myviewPager;
    private TabLayout  mytabLayout;
    private AccesoTabsAdapter myaccesoTabsAdapter;
    private String CurrentUserId;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);

        toolbar =(Toolbar)findViewById(R.id.app_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MTEXT");

        myviewPager = (ViewPager)findViewById(R.id.main_tabs_pager);
        myaccesoTabsAdapter = new AccesoTabsAdapter(getSupportFragmentManager());
        myviewPager.setAdapter(myaccesoTabsAdapter);

        mytabLayout = (TabLayout)findViewById(R.id.main_tabs);
        mytabLayout.setupWithViewPager(myviewPager);

        UserRef = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        mAuth = FirebaseAuth.getInstance();
        CurrentUserId = mAuth.getCurrentUser().getUid();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser curUser = mAuth.getCurrentUser();
        if (curUser == null){
            EnviarALogin();
        }else {
            VerificarUsuario();
        }
    }

    private void VerificarUsuario() {
        final String CurrentUserID = mAuth.getCurrentUser().getUid();
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.hasChild(CurrentUserID)){
                    CompletarDatosUsuario();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});
    }

    private void CompletarDatosUsuario() {
        Intent intent = new Intent(InicioActivity.this, SetupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    private void EnviarALogin() {
        Intent intent = new Intent(InicioActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}