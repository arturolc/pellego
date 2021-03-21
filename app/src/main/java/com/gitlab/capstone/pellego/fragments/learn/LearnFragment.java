package com.gitlab.capstone.pellego.fragments.learn;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import com.gitlab.capstone.pellego.network.models.LMResponse;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static androidx.navigation.Navigation.findNavController;

/**********************************************
    Chris Bordoy, Arturo Lara & Eli Hebdon

    The Learn Modules Component
 **********************************************/
public class LearnFragment extends BaseFragment {

    private LearnViewModel learnViewModel;
    private ListView moduleList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        learnViewModel = new ViewModelProvider(requireActivity()).get(LearnViewModel.class);
        View root = inflater.inflate(R.layout.fragment_learn, container, false);
        moduleList = root.findViewById(R.id.nav_module_list);

        super.setupHeader(root);

        learnViewModel.getLMResponse().observe(getViewLifecycleOwner(), new Observer<List<LMResponse>>() {
            @Override
            public void onChanged(List<LMResponse> lmResponses) {
                LearnCardAdapter adapter = new LearnCardAdapter(getContext(), lmResponses);
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
                        moduleViewModel.setTechnique("rsvp");
                        moduleViewModel.setGradient(new int[] {0xFFF9D976, 0xFFF39F86});
                        moduleViewModel.setViewModelVars(getResources().getString(R.string.title_rsvp), getResources().getString(R.string.description_rsvp), R.id.nav_rsvp_intro, R.array.intro_rsvp_content, R.array.intro_rsvp_header, R.id.nav_rsvp_module);
                        navController.navigate(R.id.nav_module_overview);
                        break;
                    case 1:
                    case 2:
                        break;
                    case 3: // metaguiding
                        moduleViewModel.setTechnique("metaguiding");
                        moduleViewModel.setGradient(new int[] {0xFFF53844, 0xFF42378F});
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
}
