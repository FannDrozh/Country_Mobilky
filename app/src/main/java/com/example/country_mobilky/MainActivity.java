package com.example.country_mobilky;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    View v;
    Connection connection;
    List<mask> data;
    ListView listView;
    Adapter pAdapter;
    Spinner spinnerFilter;
    String  label="Marka";
    String [] Filter={"без фильтрации","Популяция по возрастанию","Страна по алфавиту"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        v = findViewById(com.google.android.material.R.id.ghost_view);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Filter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter=findViewById(R.id.filter);
        spinnerFilter.setAdapter(adapter);


        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                String Choice = null;
                switch (position)
                {
                    case 0:
                    {
                        Choice = "Select * From Countries";
                        SelectList(Choice);
                    }
                    break;
                    case 1:
                    {
                        Choice = "Select * From Countries ORDER BY Population";
                        SelectList(Choice);
                    }
                    break;
                    case 2:
                    {
                        Choice = "Select * From Countries ORDER BY Country";
                        SelectList(Choice);
                    }
                    break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GetTextFromSQL(v);


    }

    public void enterMobile() {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }

    public void GetTextFromSQL(View v) {
        data = new ArrayList<mask>();
        listView = findViewById(R.id.BD);
        pAdapter = new Adapter(MainActivity.this, data);
        try {
            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.conclass();
            if (connection != null) {

                String query = "Select * From Countries";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    mask tempMask = new mask
                            (resultSet.getInt("ID"),
                                    resultSet.getString("Country"),
                                    resultSet.getString("Population"),
                                    resultSet.getString("Image")

                            );
                    data.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            } else {
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        enterMobile();

    }

    public void SelectList(String Choice)
    {
        data = new ArrayList<mask>();
        listView = findViewById(R.id.BD);
        pAdapter = new Adapter(MainActivity.this, data);
        try {
            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.conclass();
            if (connection != null) {

                String query = Choice;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    mask tempMask = new mask
                            (resultSet.getInt("ID"),
                                    resultSet.getString("Country"),
                                    resultSet.getString("Population"),
                                    resultSet.getString("Image")

                            );
                    data.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            } else {
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        enterMobile();
    }

    public void onClickADD(View v) {
        switch (v.getId()) {
            case R.id.btnadd:
                Intent intent = new Intent(this, ADD.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                txtSearch(newText);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.SearchName)
        {
            label="Marka";
        }
        else
        if(id==R.id.SearchPower)
        {
            label="Power";
        }
        return super.onOptionsItemSelected(item);
    }

    private  void txtSearch(String str)
    {
        data = new ArrayList<mask>();
        listView = findViewById(R.id.BD);
        pAdapter = new Adapter(MainActivity.this, data);
        try {
            String query="";
            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.conclass();
            if (connection != null) {
                if(label=="Marka")
                {
                    query = "Select * From Countries WHERE Country like'%"+str+"%'";
                }
                else
                if(label=="Power")
                {
                    query = "Select * From Countries WHERE Population like'%"+str+"%'";
                }

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    mask tempMask = new mask
                            (resultSet.getInt("ID"),
                                    resultSet.getString("Country"),
                                    resultSet.getString("Population"),
                                    resultSet.getString("Image")

                            );
                    data.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            } else {
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        enterMobile();
    }


}