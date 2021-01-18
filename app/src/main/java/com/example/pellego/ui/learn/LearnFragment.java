package com.example.pellego.ui.learn;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pellego.HomeActivity;
import com.example.pellego.R;
import com.example.pellego.ui.clumpReading.ClumpReadingFragment;
import com.example.pellego.ui.rsvp.RsvpOverviewFragment;

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

    private LearnViewModel learnViewModel;
    private ListView moduleList;
    private RelativeLayout modulePane;
    private ArrayList<ModuleItemModel> mNavItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mNavItems = new ArrayList<>();
        learnViewModel =
                new ViewModelProvider(this).get(LearnViewModel.class);

        View root = inflater.inflate(R.layout.fragment_learn, container, false);
        final TextView textView = root.findViewById(R.id.text_learn);
        learnViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // Query DB for learning modules
//        getApiData(inflater, container, new ResponseCallBack() {
//            @Override
//            public void onResponse(Object response) {
//                // Update UI only after response is received &
//                // Add icons and subtitles for modules manually for now
//                // TODO: query DB for icons, titles, etc
//                mNavItems.get(0).setIcon(R.drawable.ic_rsvp);
//                mNavItems.get(0).setSubtitle("1 out of 4 submodules completed");
//                mNavItems.get(1).setIcon(R.drawable.ic_clump_reading);
//                mNavItems.get(1).setSubtitle("0 out of 4 submodules completed");
//                mNavItems.get(2).setIcon(R.drawable.ic_reducing_subvocalization);
//                mNavItems.get(2).setSubtitle("0 out of 4 submodules completed");
//                mNavItems.get(3).setIcon(R.drawable.ic_meta_guiding);
//                mNavItems.get(3).setSubtitle("0 out of 4 submodules completed");
//                mNavItems.get(4).setIcon(R.drawable.ic_pre_reading);
//                mNavItems.get(4).setSubtitle("0 out of 4 submodules completed");
//
//                // Populate the Navigation Drawer with options
//                modulePane = root.findViewById(R.id.module_pane);
//                moduleList = root.findViewById(R.id.nav_module_list);
//                Log.d("checking", mNavItems.toString());
//                ModuleListAdapter adapter = new ModuleListAdapter(getContext(), mNavItems);
//                moduleList.setAdapter(adapter);
//
//                // Drawer Item click listeners
//                moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
//                        // TODO: navigate to fragment based on click id
//                        switch(position) {
//                            case 0:
//                                fragmentTransaction.replace(R.id.host_fragment_container, new RsvpOverviewFragment());
//                                fragmentTransaction.addToBackStack(null);
//                                fragmentTransaction.commit();
//                                ((HomeActivity) getActivity()).setActionBarIconArrow();
//                                break;
//                            case 1:
//                                fragmentTransaction.replace(R.id.host_fragment_container, new ClumpReadingFragment());
//                                fragmentTransaction.addToBackStack(null);
//                                fragmentTransaction.commit();
//                                ((HomeActivity) getActivity()).setActionBarIconArrow();
//                                break;
//                        }
//                    }
//                });
//            }
//        });

        // Manual way of populating learning modules without the DB. Comment out until 'END' to use DB
        // Add nav items to the list of learning techniques
        mNavItems.add(new ModuleItemModel("Rapid Serial Visual Presentation", "1 out of 4 submodules completed", R.drawable.ic_rsvp));
        mNavItems.add(new ModuleItemModel("Clump Reading", "0 out of 4 submodules completed", R.drawable.ic_clump_reading));
        mNavItems.add(new ModuleItemModel("Reducing Subvocalization", "3 out of 4 submodules completed", R.drawable.ic_reducing_subvocalization));
        mNavItems.add(new ModuleItemModel("Meta Guiding", "2 out of 4 submodules completed", R.drawable.ic_meta_guiding));
        mNavItems.add(new ModuleItemModel("Pre-Reading", "1 out of 4 submodules completed", R.drawable.ic_pre_reading));
        // Populate the Navigation Drawer with options
        modulePane = root.findViewById(R.id.module_pane);
        moduleList = root.findViewById(R.id.nav_module_list);
        ModuleListAdapter adapter = new ModuleListAdapter(getContext(), mNavItems);
        moduleList.setAdapter(adapter);

        // Drawer Item click listeners
        moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                // TODO: navigate to fragment based on click id
                switch(position) {
                    case 0:
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.nav_module_overview);

//                        fragmentTransaction.replace(R.id.host_fragment_container, new RsvpOverviewFragment());
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
//                        ((HomeActivity) getActivity()).setActionBarIconArrow();
                        break;
                    case 1:
//                        fragmentTransaction.replace(R.id.host_fragment_container, new ClumpReadingFragment());
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
//                        ((HomeActivity) getActivity()).setActionBarIconArrow();
                        break;
                }

            }
        });

        // END

        return root;
    }

    /**
     * Interface to to update UI after response from server is received
     */
    public interface ResponseCallBack{
        void onResponse(Object response);
    }


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
                        // TODO: Handle error
                        Log.d("error", error.toString());

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }
}