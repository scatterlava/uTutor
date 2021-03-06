package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    boolean isVisible = true;
    User currentUser;
    // when other activities want to navigate back to a specific fragment page
    String uploadPage;


    // navigation bar changes
    public TextView hName;
    public TextView hEmail;
    public ImageView hProfilePic;
    public Button mWork;

    // card layouts
    Menu menu;
    LinearLayout cardSearch;
    LinearLayout cardAppointment;
    LinearLayout cardFavorites;
    LinearLayout cardProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        uploadPage = i.getExtras().getString("uploadPage");

        mWork = (Button) findViewById(R.id.workButton);
        //check if the person is a tutor or not
        if( !currentUser.isTutor){ mWork.setVisibility(View.INVISIBLE); }
        else{ mWork.setVisibility(View.VISIBLE); }

        // Navigation Drawer Code
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Changing navigation bar values
        hName = (TextView) findViewById(R.id.hName);
        hEmail = (TextView) findViewById(R.id.hEmail);
        hProfilePic = (ImageView) findViewById(R.id.hProfilePic);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        menu = navigationView.getMenu();

        // Changing navigation bar values depending if tutor or not
        hName = (TextView) headerView.findViewById(R.id.hName);
        hEmail = (TextView) headerView.findViewById(R.id.hEmail);
        hProfilePic = (ImageView) headerView.findViewById(R.id.hProfilePic);
        if(currentUser.isTutor){
            MenuItem menuTutor = menu.findItem(R.id.nav_becomeTutor);
            menuTutor.setVisible(false);
        }
        else{
            MenuItem menuWork = menu.findItem(R.id.nav_work);
            menuWork.setVisible(false);
        }

        hName.setText(currentUser.getFirstName() +  " " + currentUser.getLastName());
        hEmail.setText(currentUser.getEmail());

        ProfilePicture p = new ProfilePicture(this);
        p.setColor(currentUser.getuNumProfilePic());
        hProfilePic.setImageBitmap(p.getBitmapColor());

        mWork.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, WalkInActivity.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);
                finish();
            }
        });

        // make card layouts clickable
        cardSearch = (LinearLayout) findViewById(R.id.cardSearch);
        cardAppointment = (LinearLayout) findViewById(R.id.cardAppointment);
        cardFavorites = (LinearLayout) findViewById(R.id.cardFavorite);
        cardProfile = (LinearLayout) findViewById(R.id.cardProfile);

        cardSearch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                MenuItem item = menu.findItem(R.id.nav_searchList);
                onNavigationItemSelected(item);
            }
        });

        cardAppointment.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                MenuItem item = menu.findItem(R.id.nav_appointmentManager);
                onNavigationItemSelected(item);
            }
        });

        cardFavorites.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                MenuItem item = menu.findItem(R.id.nav_Favorites);
                onNavigationItemSelected(item);
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                MenuItem item = menu.findItem(R.id.nav_userProfile);
                onNavigationItemSelected(item);
            }
        });

        // check if needed to navigate to a frag
        if(uploadPage != null){
            if (uploadPage.equals("workManager")) {
                MenuItem item = menu.findItem(R.id.nav_work);
                onNavigationItemSelected(item);
            } else if(uploadPage.equals("myProfile")){
                Toast.makeText(getApplicationContext(), "Changes saved!" , Toast.LENGTH_SHORT).show();
                MenuItem item = menu.findItem(R.id.nav_userProfile);
                onNavigationItemSelected(item);
            }
            else if(uploadPage.equals("searchPage")){
                MenuItem item = menu.findItem(R.id.nav_searchList);
                onNavigationItemSelected(item);
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) { drawer.closeDrawer(GravityCompat.START); }
        else {
            if(isVisible == false){
                isVisible = true;
                // send user info to HomePage
                Intent i = new Intent(HomePage.this, HomePage.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int storedID = 0;
        Fragment fragment = null;

        if (id == R.id.nav_home) {
            if(isVisible == false){
                isVisible = true;
                setClickable();

                // send user info to HomePage
                Intent i = new Intent(HomePage.this, HomePage.class);
                i.putExtra("currentUser", currentUser);
                i.putExtra("veganState", true);
                startActivity(i);
            }
        } else if (id == R.id.nav_userProfile) {
            setUnclickable();
            isVisible = false;
            fragment = new MyProfile();

        } else if (id == R.id.nav_searchList) {
            setUnclickable();
            isVisible = false;
            fragment = new SearchList();
        } else if (id == R.id.nav_Favorites) {
            setUnclickable();
            isVisible = false;
            fragment = new Favorites();

        } else if (id == R.id.nav_appointmentManager) {
            setUnclickable();
            isVisible = false;
            fragment = new AppointmentManager();

        } else if (id == R.id.nav_faq) {
            setUnclickable();
            isVisible = false;
            fragment = new FAQ();

        } else if (id == R.id.nav_aboutUs) {
            setUnclickable();
            isVisible = false;
            fragment = new AboutUs();

        } else if (id == R.id.nav_becomeTutor) {
            isVisible = false;
            // send user info to HomePage
            Intent i = new Intent(HomePage.this, BecomeATutor.class);
            i.putExtra("currentUser", currentUser);
            startActivity(i);

        } else if (id == R.id.nav_SignOut) {
            Toast.makeText(getApplicationContext(), "Signed Out." , Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomePage.this, LogIn.class));
            finish();
        }
        else if( id == R.id.nav_work){
            setUnclickable();
            isVisible = false;
            fragment = new WorkManager();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void setUnclickable(){
        // set home page buttons as unclickable
        cardSearch.setClickable(false);
        cardProfile.setClickable(false);
        cardFavorites.setClickable(false);
        cardAppointment.setClickable(false);
        mWork.setClickable(false);
    }

    public void setClickable(){
        // set home page buttons as unclickable
        cardSearch.setClickable(true);
        cardProfile.setClickable(true);
        cardFavorites.setClickable(true);
        cardAppointment.setClickable(true);
        mWork.setClickable(true);
    }


}
