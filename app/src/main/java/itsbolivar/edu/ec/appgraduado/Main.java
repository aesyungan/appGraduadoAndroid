package itsbolivar.edu.ec.appgraduado;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import itsbolivar.edu.ec.appgraduado.LN.LNGraduado;
import itsbolivar.edu.ec.appgraduado.clases.staticos.EstadoTelefono;
import itsbolivar.edu.ec.appgraduado.clases.staticos.UsuarioSingleton;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentOfertas.OnFragmentInteractionListener,FragmentOfertasAplicadas.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //header
        View header = navigationView.getHeaderView(0);
        TextView usuario_name_logeado = (TextView) header.findViewById(R.id.txtUsuarioLog);
        usuario_name_logeado.setText(UsuarioSingleton.getInstance().graduado.getNombre());
        TextView usuario_email_logeado = (TextView) header.findViewById(R.id.txtUsuarioCorreo);
        usuario_email_logeado.setText(UsuarioSingleton.getInstance().graduado.getEmail());

        //fracments

        FragmentOfertas fragment= new FragmentOfertas();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContenedorTodo,fragment).commit();
        //cargar  context
        EstadoTelefono.context=getBaseContext();
        EstadoTelefono.appContext=getApplicationContext();
        EstadoTelefono.view=getWindow().getDecorView().getRootView();
        EstadoTelefono.resources=getResources();
        EstadoTelefono.activity=Main.this;

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            FragmentOfertas fragment = new FragmentOfertas();
            CargarFragmet(fragment);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            FragmentOfertasAplicadas fragment = new FragmentOfertasAplicadas();
            CargarFragmet(fragment);

        } else if (id == R.id.nav_send) {
            LNGraduado lnGraduado = new LNGraduado(Main.this, getBaseContext(), getString(R.string.IP_SERVIDOR) + "ws/graduado/login");
            lnGraduado.eliminarDatosDataBase();
            finish();
            startActivity(new Intent(this, login.class));//si ya esta logeado se va al menu
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void CargarFragmet(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContenedorTodo,fragment);
        transaction.commit();
    }

}
