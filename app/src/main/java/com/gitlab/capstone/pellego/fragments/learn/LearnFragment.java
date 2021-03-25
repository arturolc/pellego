package com.gitlab.capstone.pellego.fragments.learn;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.network.models.LMResponse;

import java.util.List;

/**********************************************
    Chris Bordoy, Arturo Lara & Eli Hebdon

    The Learn Modules Component
 **********************************************/

public class LearnFragment extends BaseFragment {

    private LearnViewModel learnViewModel;
    private ListView moduleList;
    private LiveData<List<LMResponse>> lmResponses;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        learnViewModel = new ViewModelProvider(requireActivity()).get(LearnViewModel.class);
        View root = inflater.inflate(R.layout.fragment_learn, container, false);
        moduleList = root.findViewById(R.id.nav_module_list);

        super.setupHeader(root);

        lmResponses = learnViewModel.getLMResponse();
        learnViewModel.getLMResponse().observe(getViewLifecycleOwner(), new Observer<List<LMResponse>>() {
            @Override
            public void onChanged(List<LMResponse> lmResponses) {
                LearnCardAdapter adapter = new LearnCardAdapter(getContext(), lmResponses);
                moduleList.setAdapter(adapter);
            }
        });

        //TODO: NEED TO CLEAN UP AND HOOK UP META GUIDING
        // Drawer Item click listeners
        moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                // TODO: navigate to fragment based on click id
                switch(position) {
                    case 0: // rsvp
                        Bundle bundle = new Bundle();
                        bundle.putString("moduleID", lmResponses.getValue().get(position).getMID().toString());
                        navController.navigate(R.id.nav_module_overview, bundle);
                        break;
                    case 1:
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("moduleID", lmResponses.getValue().get(position).getMID().toString());
                        navController.navigate(R.id.nav_module_overview, bundle1);
                    case 2:
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("moduleID", lmResponses.getValue().get(position).getMID().toString());
                        navController.navigate(R.id.nav_module_overview, bundle2);
                        break;
                    case 3: // metaguiding
                        //TODO: NEED TO IMPLEMENT META-GUIDING DATA COMMUNICATIONS
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("moduleID", lmResponses.getValue().get(position).getMID().toString());
                        navController.navigate(R.id.nav_module_overview, bundle3);
                        break;
                    default:
                        break;
                }
            }
        });

        return root;
    }
}
