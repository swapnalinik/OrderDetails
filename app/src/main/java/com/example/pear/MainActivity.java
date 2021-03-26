package com.example.pear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<MainData> dataArrayList = new ArrayList<>();
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");
        recyclerView = findViewById(R.id.recyclerview);

        adapter = new MainAdapter(MainActivity.this, dataArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://backend.pearpartner.com/order/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MainInterface mainInterface = retrofit.create(MainInterface.class);
        Call<String> stringCall = mainInterface.STRING_CALL();

        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        parseArray(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseArray(JSONArray jsonArray) {
        dataArrayList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MainData data = new MainData();
                data.setGrand_total(jsonObject.getDouble("grand_total"));
                data.setRestaurant_name(jsonObject.getString("restaurant_name"));
                data.setRestaurant_image(jsonObject.getString("restaurant_image"));
                data.setRestaurant_location(jsonObject.getString("restaurant_location"));
                data.setTimestamp(jsonObject.getString("timestamp"));
                String batch = jsonObject.getString("batch");
                JSONArray jsonArray1 = new JSONArray(batch);
                StringBuilder item_information= new StringBuilder();
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    String items = jsonObject1.getString("items");
                    JSONArray jsonArray2 = new JSONArray(items);
                    for (int k = 0; k < jsonArray2.length(); k++) {
                        JSONObject jsonObject2 = jsonArray2.getJSONObject(k);
                        String item_name = jsonObject2.getString("name");
                        int item_no = jsonObject2.getInt("quantity");
                        item_information.append(item_no).append(" x ").append(item_name);
                        if(j<jsonArray2.length()){
                            if(k<jsonObject1.length()){
                                item_information.append(",");
                            }
                        }
                    }
                }
                data.setTotal_info(item_information.toString());
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new MainAdapter(MainActivity.this, dataArrayList);
            recyclerView.setAdapter(adapter);
            invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (adapter == null || adapter.getItemCount() == 0) {
            return super.onCreateOptionsMenu(menu);
        } else {
            getMenuInflater().inflate(R.menu.menu, menu);
            MenuItem menuItem = menu.findItem(R.id.search_view);
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setMaxWidth(Integer.MAX_VALUE);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return true;
                }
            });
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_view) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
