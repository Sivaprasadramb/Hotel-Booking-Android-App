package com.example.surface.ramhotelapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String nameValue;
    String username;

    Button b1;
    Button b2;
    Button b3;
    Button adminButton;
    TextView user1;

    private int mButtonChoice = 4;

    private FirebaseAuth auth;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String uid = mAuth.getCurrentUser().getUid().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Blocks orientation change
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        // Adding username to navigation bar

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        TextView name = (TextView) v.findViewById(R.id.usernameNav);
        Bundle extras = getIntent().getExtras();
        nameValue = extras.getString("userName");
        name.setText(nameValue);

        // Make adminPage button visible

        adminButton = (Button) v.findViewById(R.id.nav_admin);

        if(nameValue.equals("siva.sotc@gmail.com") ||nameValue.equals("ramgroupofhotels@gmail.com") )
        {
            toggleVisibility(navigationView.getMenu(), R.id.nav_admin, true);
        }

        // end

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            Fragment firstFragment = new Fragment();

            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
        b1=(Button) findViewById(R.id.btnOne);
        b2=(Button) findViewById(R.id.btnTwo);
        b3=(Button) findViewById(R.id.btnThree);
        b1.setOnClickListener(view -> {
            ServiceFrag f1=new ServiceFrag();
            f1.setArguments(getIntent().getExtras());
            final FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, f1);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        b2.setOnClickListener(view -> {
            ServiceFragment2 f2=new ServiceFragment2();
            f2.setArguments(getIntent().getExtras());

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, f2);
            transaction.addToBackStack(null);

            transaction.commit();
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceFrag3 f3=new ServiceFrag3();
                f3.setArguments(getIntent().getExtras());

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, f3);
                transaction.addToBackStack(null);

                transaction.commit();

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_room) {
            Intent i = new Intent(HomeActivity.this, Room.class);
            i.putExtra("userName", nameValue);
            startActivity(i);
        } else if (id == R.id.nav_activities) {
            Intent i = new Intent(HomeActivity.this, ActivitiesActivity.class);
            i.putExtra("userName", nameValue);
            startActivity(i);
        } else if (id == R.id.nav_roomservice) {
            Intent i = new Intent(HomeActivity.this, RoomServiceActivity.class);
            i.putExtra("userName", nameValue);
            startActivity(i);
        } else if (id == R.id.nav_invoice) {
            Intent i = new Intent(HomeActivity.this, Invoice.class);
            i.putExtra("userName", nameValue);
            startActivity(i);
        } else if (id == R.id.nav_admin) {
            Intent i = new Intent(HomeActivity.this, AdminPage_Activity.class);
            startActivity(i);
        } else if (id == R.id.nav_review) {
            Intent i = new Intent(HomeActivity.this, Rank.class);
            i.putExtra("userName", nameValue);
            startActivity(i);
        }
        else if (id==R.id.nav_contact)
        {
            Intent i=new Intent(HomeActivity.this,Contactus.class);
            startActivity(i);
        }
        else if (id== R.id.nav_signout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(HomeActivity.this, "You have successfully Signed Out", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(HomeActivity.this, MainActivity.class);
            i.putExtra("userName", nameValue);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Method for set visibility of adminPage button

    private void toggleVisibility(Menu menu, int id, boolean visible){
        menu.findItem(id).setVisible(visible);
    }
}
