package app.tutorial.com.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.tutorial.com.R;
import app.tutorial.com.adapter.UserDataAdapter;
import app.tutorial.com.database.DatabaseHelper;
import app.tutorial.com.interfaces.RecyclerClickListener;
import app.tutorial.com.model.demoapp.Model;
import app.tutorial.com.model.demoapp.Result;
import app.tutorial.com.model.demoapp.UserResponseParser;
import app.tutorial.com.rest.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerClickListener, UserDataAdapter.IAddListCallback {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Context context;
    private RecyclerView recyclerView = null;
    private DatabaseHelper dbhelper;
    private SQLiteDatabase db;
    private List<Model> userData = new ArrayList<Model>();
    final String key = "example"; // constant key to retrieve data, must be unique
    final String prefName = "example2";// constant name for preference file
    SharedPreferences pref;
    final Gson gson = new Gson();
    UserDataAdapter userDataAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        //Find view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        dbhelper = new DatabaseHelper(this);


        final String serialized = pref.getString(key, null); // if there is no data in preferences yet, it returns null
        if (serialized == null) {
            getData();
            userDataAdapter = new UserDataAdapter(userData, MainActivity.this);
            recyclerView.setHasFixedSize(true);
            userDataAdapter.setRvClickListener(this);
            userDataAdapter.setAddWatchListCallback(this);
            recyclerView.setAdapter(userDataAdapter);
        } else {
            Type collectionType = new TypeToken<List<Model>>() {
            }.getType();
            userData = (List<Model>) new Gson().fromJson(serialized, collectionType);
//            Toast.makeText(MainActivity.this, "" + userData, Toast.LENGTH_SHORT).show();
            userDataAdapter = new UserDataAdapter(userData, MainActivity.this);
            recyclerView.setHasFixedSize(true);
            userDataAdapter.setRvClickListener(MainActivity.this);
            recyclerView.setAdapter(userDataAdapter);
        }

    }

    public void addData(String entry) {
        boolean insertData = dbhelper.insert(entry);
        if (insertData) {
            Toast.makeText(MainActivity.this, "Inserted" + entry, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "not inserted", Toast.LENGTH_SHORT).show();
        }

    }

    private void getData() {
        Call<UserResponseParser> parserCall = UserAPI.getPostService().getResParser();
        parserCall.enqueue(new Callback<UserResponseParser>() {
            @Override
            public void onResponse(Call<UserResponseParser> call, Response<UserResponseParser> response) {
                UserResponseParser resParser = response.body();
                int id = 0;
                if (resParser.getResults() != null) {
                    for (Result result : resParser.getResults()) {
                        userData.add(new Model(id, result.getGender(), result.getName(),
                                result.getLocation(), result.getEmail(), result.getLogin(), result.getDob(),
                                result.getRegistered(), result.getPhone(), result.getCell(), result.getId(),
                                result.getPicture(), result.getNat(), false, false));
                        id++;
                    }
                    pref.edit().putString(key, gson.toJson(userData)).apply();
                    userDataAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserResponseParser> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case R.id.btnAccept:
//                userData.get(position).setAccept(true);
//                userData.get(position).setDecline(false);
                pref.edit().putString(key, gson.toJson(userData)).apply();
//                Toast.makeText(MainActivity.this, "" + position + "Accept", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnReject:
//                userData.get(position).setAccept(false);
//                userData.get(position).setDecline(true);
                pref.edit().putString(key, gson.toJson(userData)).apply();
//                Toast.makeText(MainActivity.this, "" + position + "Reject", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onAddlistSuccess(int position) {
//        isAddedTolist(position);
    }

   /* private Boolean isAddedTolist(int position) {
        return DatabaseHelper.isStockExistInlist(new Model(userData.get(position).getMid(), userData.get(position).getName()
                , userData.get(position).isAccept()));
    }*/

}
