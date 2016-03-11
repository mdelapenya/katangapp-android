package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.models.QueryResult;

import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;

/**
 * @author Cristóbal Hermida
 */
public class ShowStopsActivity extends AppCompatActivity {

    private Adapter busStopAdapter = new StopsAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_stops);

        Intent intent = getIntent();

        QueryResult queryResult = (QueryResult) intent.getSerializableExtra("queryResult");

        ((StopsAdapter)busStopAdapter).addBusStops(queryResult.getResults());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stops);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(busStopAdapter);
    }

}