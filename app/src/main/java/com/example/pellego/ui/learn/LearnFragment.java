package com.example.pellego.ui.learn;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pellego.R;
import com.example.pellego.ui.module.ModuleItemModel;
import com.example.pellego.ui.module.ModuleViewModel;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

/**********************************************
    Chris Bordoy & Eli Hebdon

    The Learn Modules Component
 **********************************************/
public class LearnFragment extends Fragment {

    private ModuleViewModel moduleViewModel;
    private ListView moduleList;
    private ArrayList<ModuleItemModel> mNavItems;
    ProgressBar spinner;
    NavigationView modulesView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mNavItems = new ArrayList<>();

        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);

        View root = inflater.inflate(R.layout.fragment_learn, container, false);
        final TextView textView = root.findViewById(R.id.text_learn);
        textView.setText(R.string.title_learn);
        moduleList = root.findViewById(R.id.nav_module_list);

        spinner = root.findViewById(R.id.loading_spinner);
        modulesView = root.findViewById(R.id.nav_module_overview);
        // Query DB for learning modules
        getApiData(inflater, container, new ResponseCallBack() {
            @Override
            public void onResponse(Object response) {
                // Update UI only after response is received &
                // Add icons and subtitles for modules manually for now
                System.out.println("response " + response);
                // TODO: query DB for icons, titles, etc
                mNavItems.get(0).setIcon(R.drawable.ic_rsvp);
                mNavItems.get(0).setSubtitle("1 out of 4 submodules completed");
                mNavItems.get(1).setIcon(R.drawable.ic_clump_reading);
                mNavItems.get(1).setSubtitle("0 out of 4 submodules completed");
                mNavItems.get(2).setIcon(R.drawable.ic_reducing_subvocalization);
                mNavItems.get(2).setSubtitle("0 out of 4 submodules completed");
                mNavItems.get(3).setIcon(R.drawable.ic_meta_guiding);
                mNavItems.get(3).setSubtitle("0 out of 4 submodules completed");
                mNavItems.get(4).setIcon(R.drawable.ic_pre_reading);
                mNavItems.get(4).setSubtitle("0 out of 4 submodules completed");

                // Populate the Navigation Drawer with options
                Log.d("checking", mNavItems.toString());
                ModuleListAdapter adapter = new ModuleListAdapter(getContext(), mNavItems);
                moduleList.setAdapter(adapter);
            }
        });
        // Drawer Item click listeners
        moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                // TODO: navigate to fragment based on click id
                switch(position) {
                    case 0: // rsvp
                        moduleViewModel.setViewModelVars(getResources().getString(R.string.title_rsvp), getResources().getString(R.string.description_rsvp), R.id.nav_rsvp_intro, R.array.intro_rsvp_content, R.array.intro_rsvp_header, R.id.nav_rsvp_module);
                        break;
                    case 1:
                    case 2:
                        break;
                    case 3: // metaguiding
                        moduleViewModel.setViewModelVars(getResources().getString(R.string.title_meta_guiding), getResources().getString(R.string.description_metaguiding), R.id.nav_metaguiding_intro, R.array.intro_metaguiding_content, R.array.intro_metaguiding_header, R.id.nav_metaguiding_module);
                        break;
                }
                navController.navigate(R.id.nav_module_overview);
            }
        });
        return root;
    }

    /**
     * Interface to to update UI after response from server is received
     */
    public interface ResponseCallBack{
        void onResponse(Object response);
    }

    /**
     * Query the DB for learning modules. If no connection can be established, use the default data
     */
    private void getApiData(@NonNull LayoutInflater inflater, ViewGroup container, ResponseCallBack responseCallBack)
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://54.176.198.201:5000/modules";

        // Request a json response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response", response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject item = response.getJSONObject(i);
                                mNavItems.add(new ModuleItemModel(item.get("Name").toString()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        responseCallBack.onResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                        // Load the default data from shared preferences
                        spinner.setVisibility(View.GONE);
                        modulesView.setVisibility(View.VISIBLE);
                        useDefaultData();
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    /**
     * Populates the learning module list with default data.
     */
    private void useDefaultData() {
        //TODO use shared preferences to populate list using data stored locally on the device
        mNavItems.add(new ModuleItemModel("Rapid Serial Visual Presentation", "1 out of 4 submodules completed", R.drawable.ic_rsvp));
        mNavItems.add(new ModuleItemModel("Clump Reading", "0 out of 4 submodules completed", R.drawable.ic_clump_reading));
        mNavItems.add(new ModuleItemModel("Reducing Subvocalization", "3 out of 4 submodules completed", R.drawable.ic_reducing_subvocalization));
        mNavItems.add(new ModuleItemModel("Meta Guiding", "2 out of 4 submodules completed", R.drawable.ic_meta_guiding));
        mNavItems.add(new ModuleItemModel("Pre-Reading", "1 out of 4 submodules completed", R.drawable.ic_pre_reading));
        // Populate the Navigation Drawer with options
        ModuleListAdapter adapter = new ModuleListAdapter(getContext(), mNavItems);
        moduleList.setAdapter(adapter);
    }


}