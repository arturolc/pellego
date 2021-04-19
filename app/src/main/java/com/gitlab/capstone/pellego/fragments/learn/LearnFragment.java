package com.gitlab.capstone.pellego.fragments.learn;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.network.models.CompletionResponse;
import com.gitlab.capstone.pellego.network.models.LMResponse;

import java.util.List;

/**********************************************
    Chris Bordoy, Arturo Lara & Eli Hebdon

    The Learning Modules Component
 **********************************************/

public class LearnFragment extends BaseFragment {

    private LearnViewModel learnViewModel;
    private ListView moduleList;
    private LiveData<List<LMResponse>> lmResponses;
    public int[] completionResponse;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        learnViewModel = new ViewModelProvider(requireActivity()).get(LearnViewModel.class);
        View root = inflater.inflate(R.layout.fragment_learn, container, false);
        ProgressBar pgsBar = (ProgressBar)getActivity().findViewById(R.id.progress_loader);
        pgsBar.setVisibility(View.VISIBLE);
        moduleList = root.findViewById(R.id.nav_module_list);

        super.setupHeader(root);

        learnViewModel.getCompletionResponse().observe(getViewLifecycleOwner(), new Observer<List<CompletionResponse>>() {
            @Override
            public void onChanged(List<CompletionResponse> completionResponses) {
                completionResponse = learnViewModel.parseCompletionResponses(completionResponses);
                lmResponses = learnViewModel.getLMResponse();
                learnViewModel.getLMResponse().observe(getViewLifecycleOwner(), new Observer<List<LMResponse>>() {
                    @Override
                    public void onChanged(List<LMResponse> lmResponses) {
                        pgsBar.setVisibility(View.INVISIBLE);

                        LearnCardAdapter adapter = new LearnCardAdapter(getContext(), completionResponse, lmResponses);
                        moduleList.setAdapter(adapter);
                    }
                });

                // Drawer Item click listeners
                moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

                        Bundle bundle = new Bundle();
                        bundle.putString("moduleID", lmResponses.getValue().get(position).getMID().toString());
                        navController.navigate(R.id.nav_module_overview, bundle);
                    }
                });
            }
        });

        return root;
    }
}
