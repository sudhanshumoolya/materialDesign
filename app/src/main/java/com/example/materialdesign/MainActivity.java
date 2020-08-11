package com.example.materialdesign;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements ExampleBottomSheetDialog.BottomSheetListener {


    private ArrayList<item> Items;
    private RecyclerView recyclerView;
    private myAdapater myAdapter;

    private FloatingActionButton AddRow;
    private BottomAppBar bottomAppBar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.bar);

        loadData();
        buildRecyclerView();

        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        title = findViewById(R.id.textView2);
        AddRow=findViewById(R.id.fab_addrow);

        AddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExampleBottomSheetDialog bottomSheetDialog = new ExampleBottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(),"examplebottomsheet");
            }
        });

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.remove_all:
                        Items.clear();
                        myAdapter.notifyDataSetChanged();
                        saveData();
                     //   loadData();
                        return true;
                    case R.id.rename_list:
                        Toast.makeText(MainActivity.this,"Rename list",Toast.LENGTH_LONG).show();
                        return true;
                    default:return false;
                }
            }
        });

    }

    
    public void buildRecyclerView()
    {
        recyclerView = findViewById(R.id.recyclerView);

        myAdapter = new myAdapater(this,Items);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Items.remove(viewHolder.getAdapterPosition());
            myAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            saveData();

        }
    };

    @Override
    public void onButtonClicked(String title,String msg) {
        Items.add(new item(title, msg));
        myAdapter.notifyItemChanged(-1);
        saveData();
    }


    public void saveData()
    {
        SharedPreferences sharedPreference= getSharedPreferences("shared preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        Gson gson = new Gson();
        String json= gson.toJson(Items);
        editor.putString("task list",json);
        editor.apply();
    }

    public void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preference",MODE_PRIVATE);
        Gson gson= new Gson();
        String json = sharedPreferences.getString("task list",null);
        Type type = new TypeToken<ArrayList<item>>() {}.getType();
        Items = gson.fromJson(json, type);

        if (Items == null) {
            Items = new ArrayList<>();
        }
    }
}
