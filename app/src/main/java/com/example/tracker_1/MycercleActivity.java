package com.example.tracker_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MycercleActivity extends AppCompatActivity {



    ArrayList<String> listUser;
    ArrayList<Integer> listUser2;



    int    id ;
    String name  ;
    String email ;
    String username ;
    String password ;
    double atitude  ;
    double longtitude ;
    String id_cercle ;
    String issharing ;

    Intent in1 = null;
    Intent in2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycercle);


        initIntent1();
        ListView ls = (ListView) findViewById(R.id.lv);
        ConnectionDB conndb = new ConnectionDB(this);

        initIntent1();
        conndb.execute("getMemberCercle", id_cercle);
        String s = "";

        try {
            s = conndb.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        listUser = new ArrayList<String>();
        listUser2 = new ArrayList<Integer>();



        try {

            JSONObject obj = new JSONObject(s);
            JSONArray users = obj.getJSONArray("users");
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                listUser.add(user.getString("name"));
                listUser2.add(user.getInt("id"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listUser);
        ls.setAdapter(arrayAdapter);


        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getp(position);
            }
        });
    }

    void getp( int p){

        Intent  in3 = new Intent(this, MapsActivityFriend.class);
        in3.putExtra("id_user",Integer.toString(listUser2.get(p)));
        in3.putExtra("atitude", atitude);
        in3.putExtra("longtitude", longtitude);
        in3.putExtra("name", name);

        Toast.makeText(this,"id_user : "+listUser2.get(p),Toast.LENGTH_LONG).show();
        startActivity(in3);
    }




    public void initIntent1(){

        in1 = getIntent();

        id    =     in1.getIntExtra("id", 0);
        name  =     in1.getStringExtra("name");
        email =     in1.getStringExtra("email");
        username =  in1.getStringExtra("username");
        password =  in1.getStringExtra("password");
        atitude =   in1.getDoubleExtra("atitude",0);
        longtitude =in1.getDoubleExtra("longtitude",0);
        id_cercle = in1.getStringExtra("id_cercle");
        issharing = in1.getStringExtra("issharing");
    }

    public void initIntent2(){

        in2.putExtra("id", id);
        in2.putExtra("name", name);
        in2.putExtra("email", email);
        in2.putExtra("username", username);
        in2.putExtra("password", password);
        in2.putExtra("atitude", atitude);
        in2.putExtra("longtitude", longtitude);
        in2.putExtra("id_cercle", id_cercle);
        in2.putExtra("issharing", issharing);
    }





    /* --------------------------------------------  MENU ----------------------------------------*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.profile:
                in2 = new Intent(this, ProfileActivity.class);
                initIntent2();
                startActivity(in2);
                return true;

            case R.id.cercle:
                return true;


            case R.id.mycercle:
                in2 = new Intent(this, MycercleActivity.class);
                initIntent2();
                startActivity(in2);
                return true;

            case R.id.joinacercle:
                in2 = new Intent(this, JoincercleActivity.class);
                initIntent2();
                startActivity(in2);
                return true;


            case R.id.joinedcercle:
                in2 = new Intent(this,JoinedCercleActivity.class);
                initIntent2();
                startActivity(in2);
                return true;

            case R.id.invite:
                in2 = new Intent(this, InviteActivity.class);
                initIntent2();
                startActivity(in2);
                return true;


            case R.id.singout:
                in2 = new Intent(this, MainActivity.class);
                startActivity(in2);
                finish();

                return true;


                case R.id.mylocation:
                in2 = new Intent(this, MapsActivity.class);
                    initIntent2();
                    startActivity(in2);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    public void reload(View view) {


        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}
