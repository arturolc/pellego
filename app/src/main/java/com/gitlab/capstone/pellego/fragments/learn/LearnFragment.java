package com.gitlab.capstone.pellego.fragments.learn;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
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
import com.github.axet.androidlibrary.widgets.InvalidateOptionsMenuCompat;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.app.Storage;
import com.gitlab.capstone.pellego.fragments.library.LibraryFragment;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleListAdapter;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleListItemModel;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
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
public class LearnFragment extends BaseFragment {

    private ModuleViewModel moduleViewModel;
    private ListView moduleList;
    private GridView moduleGrid;
    private ArrayList<ModuleListItemModel> mNavItems;
    ProgressBar spinner;
    NavigationView modulesView;
    LibraryFragment.FragmentHolder holder;
    

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mNavItems = new ArrayList<>();

        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_learn, container, false);
//        moduleList = root.findViewById(R.id.nav_module_list);
        moduleGrid = root.findViewById(R.id.module_card_pane);
        spinner = root.findViewById(R.id.loading_spinner);
        // TODO: show spinner while modules load in
        spinner.setVisibility(View.GONE);

//        modulesView = root.findViewById(R.id.nav_module_overview);
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
        moduleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                // TODO: navigate to fragment based on click id
                switch(position) {
                    case 0: // rsvp
                        moduleViewModel.setTechnique("rsvp");
                        moduleViewModel.setViewModelVars(getResources().getString(R.string.title_rsvp), getResources().getString(R.string.description_rsvp), R.id.nav_rsvp_intro, R.array.intro_rsvp_content, R.array.intro_rsvp_header, R.id.nav_rsvp_module);
                        navController.navigate(R.id.nav_module_overview);
                        break;
                    case 1:
                    case 2:
                        break;
                    case 3: // metaguiding
                        moduleViewModel.setTechnique("metaguiding");
                        moduleViewModel.setViewModelVars(getResources().getString(R.string.title_meta_guiding), getResources().getString(R.string.description_metaguiding), R.id.nav_metaguiding_intro, R.array.intro_metaguiding_content, R.array.intro_metaguiding_header, R.id.nav_metaguiding_module);
                        navController.navigate(R.id.nav_module_overview);
                        break;
                    case 4:
                        break;
                }

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
//        spinner.setVisibility(View.GONE);
//        modulesView.setVisibility(View.VISIBLE);
        useDefaultData();
        // Request a json response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response", response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject item = response.getJSONObject(i);
                                mNavItems.add(new ModuleListItemModel(item.get("Name").toString()));
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
//                        modulesView.setVisibility(View.VISIBLE);
//                        useDefaultData();
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
        mNavItems.add(new ModuleListItemModel("Rapid Serial Visual Presentation", retrieveModuleProgress("rsvp"), R.color.transparent_blue, getString(R.string.short_description_rsvp)));
        mNavItems.add(new ModuleListItemModel("Clump Reading", retrieveModuleProgress("clumpreading"),R.color.transparent_blue, getString(R.string.short_description_clump_reading)));
        mNavItems.add(new ModuleListItemModel("Reducing Subvocalization", retrieveModuleProgress("reducingsubvocalization"), R.color.transparent_blue, getString(R.string.short_description_reducing_subvocalization)));
        mNavItems.add(new ModuleListItemModel("Meta Guiding", retrieveModuleProgress("metaguiding"), R.color.transparent_blue, getString(R.string.short_description_metaguiding)));
        // Populate the Navigation Drawer with options
        ModuleCardAdapter adapter = new ModuleCardAdapter(getContext(), mNavItems);
        moduleGrid.setAdapter(adapter);
    }

    private String retrieveModuleProgress(String technique) {
        // TODO: query DB for module progress and default to local data if connection can't be established
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String key = technique + "_submodule_progress";
        return sharedPref.getInt(key, 0) + " of 4 completed";
    }


}