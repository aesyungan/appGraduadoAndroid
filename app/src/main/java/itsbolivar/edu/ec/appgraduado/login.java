package itsbolivar.edu.ec.appgraduado;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;

import itsbolivar.edu.ec.appgraduado.LN.LNGraduado;
import itsbolivar.edu.ec.appgraduado.clases.Graduado;
import itsbolivar.edu.ec.appgraduado.clases.staticos.UsuarioSingleton;

public class login extends AppCompatActivity {
    private ImageView imageViewLogin;
    CardView cardViewLogin;
    EditText txtUser, txtPassword;
    LNGraduado lnGraduado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //verifica si esta logueado
        lnGraduado = new LNGraduado(login.this, getBaseContext(), getString(R.string.IP_SERVIDOR) + "ws/graduado/login");

        try {
            UsuarioSingleton.getInstance().graduado = lnGraduado.optenerUsuarioLogueado();
            if (UsuarioSingleton.getInstance().graduado == null) {
               // Toast.makeText(getBaseContext(), "No esta Logeado", Toast.LENGTH_LONG).show();
            } else {
                startActivity(new Intent(this, Main.class));//si ya esta logeado se va al menu
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar_login);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar_login);
        collapsingToolbarLayout.setTitle("Login");
        Context context = this;
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(context, R.color.blanco));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(context, R.color.blanco));
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.respuesta));

        cardViewLogin = (CardView) findViewById(R.id.cardViewLogin);
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPassword = (EditText) findViewById(R.id.txtpass);

        cardViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Graduado g = new Graduado();
                g.setCi(txtUser.getText().toString());
                g.setClave(txtPassword.getText().toString());
                if (g.getCi() != "" && g.getClave() != "") {

                    lnGraduado.Logear(g,Main.class);
                } else {
                    Toast.makeText(getBaseContext(), "LLene Todos los Datos", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
