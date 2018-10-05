package com.egyptsweather.Fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.egyptsweather.Adapter.RecyclerViewAdapter;
import com.egyptsweather.Model.City_Details;
import com.egyptsweather.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class ListFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private SearchView search;

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    ArrayList arrayList_City_Details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        String languageToLoad = "en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());


        recyclerView = view.findViewById(R.id.listOfCities);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Gson gson = new Gson();

        SharedPreferences prefs = getContext().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        if (prefs.contains("list")) {
            String list = prefs.getString("list", null);
            arrayList_City_Details = gson.fromJson(list, new TypeToken<List<City_Details>>() {
            }.getType());
            recyclerViewAdapter = new RecyclerViewAdapter(getContext(), arrayList_City_Details);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.notifyDataSetChanged();
            recyclerViewAdapter.setFilter(arrayList_City_Details);
            prefs.edit().remove("list").apply();
        }else {
            Toast.makeText(getContext(), "Check Error", Toast.LENGTH_SHORT).show();
        }

        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        search = view.findViewById(R.id.search);
        search.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);
                return view;
    }

    @Override
    public String toString() {
        return "Cities";
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        final List<City_Details> filteredModelList = filter(arrayList_City_Details, query);
        recyclerViewAdapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<City_Details> filteredModelList = filter(arrayList_City_Details, newText);
        recyclerViewAdapter.setFilter(filteredModelList);
        return true;
    }

    private List<City_Details> filter(List<City_Details> models, String query) {
        query = query.toLowerCase();
        final List<City_Details> filteredModelList = new ArrayList<>();
        for (City_Details model : models) {
            final String text = model.getCity_name().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
