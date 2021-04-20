package com.gitlab.capstone.pellego.fragments.module.overview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;

import java.util.ArrayList;
import java.util.List;

/**********************************************
 Eli Hebdon, Chris Bordoy & Arturo Lara

 Module Overview fragment that
 displays the technique submodules and gives a brief overview
 of the technique.
 **********************************************/

public class ModuleOverviewFragment extends BaseFragment {

    private ModuleViewModel moduleViewModel;
    private ListView moduleList;
    private List<SMResponse> submoduleResponse;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        String moduleID = getArguments().getString("moduleID");
        moduleViewModel =
                new ViewModelProvider(
                        requireActivity(),
                        new ModuleViewModelFactory(
                                getActivity().getApplication(),
                                moduleID)).
                        get(ModuleViewModel.class);
        moduleViewModel.setModuleID(moduleID);

        View root = inflater.inflate(R.layout.fragment_module_overview, container, false);
        ProgressBar pgsBar = (ProgressBar)getActivity().findViewById(R.id.progress_loader);
        pgsBar.setVisibility(View.VISIBLE);
        moduleList = root.findViewById(R.id.nav_module_list);
        moduleViewModel.setTechniqueLabel(moduleViewModel.getModuleID());
        moduleViewModel.setTechnique(moduleViewModel.getTechniqueLabel());
        moduleViewModel.setGradient(moduleGradientPicker(moduleViewModel.getModuleID()));
        TextView moduleTitle = root.findViewById(R.id.title_module_overview);
        TextView moduleDescription = root.findViewById(R.id.text_module_description);
        moduleTitle.setTextColor((App.getAppResources().getColor(R.color.gray_card)));
        moduleDescription.setTextColor((App.getAppResources().getColor(R.color.gray_card)));

        moduleViewModel.getLMDescResponse(moduleViewModel.getModuleID()).observe(getViewLifecycleOwner(), new Observer<List<LMDescResponse>>() {
            @Override
            public void onChanged(List<LMDescResponse> response) {
                moduleTitle.setText(response.get(0).getName());
                moduleDescription.setText(response.get(0).getDescription());
            }
        });

        moduleViewModel.getSubmodulesResponse(moduleViewModel.getModuleID()).observe(getViewLifecycleOwner(), new Observer<List<SMResponse>>() {
            @Override
            public void onChanged(List<SMResponse> response1) {
                pgsBar.setVisibility(View.INVISIBLE);
                submoduleResponse = response1;
                ModuleListAdapter adapter = new ModuleListAdapter(getContext(), response1, ModuleOverviewFragment.this);
                moduleList.setAdapter(adapter);
            }
        });

        this.setupHeader(root, moduleViewModel.getModuleID());

        moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavController navController = Navigation.findNavController(view);
                Bundle args = new Bundle();
                args.putString("moduleID", moduleID);
                if (position != 0) {
                    args.putInt("quizTextCount", moduleViewModel.getQuizTextCount(submoduleResponse.get(position).getText()));
                }
                args.putParcelableArrayList("subModules", (ArrayList<? extends Parcelable>) submoduleResponse);
                moduleViewModel.setShowSubmodulePopupDialog(true);
                int[] submoduleIDs = getSubmoduleIDs(moduleID);
                switch(position) {
                    case 0:
                        args.putString("smID", "1");
                        navController.navigate(submoduleIDs[0], args);
                        break;
                    case 1:
                        args.putString("smID", submoduleIDs[2]+ "");
                        args.putString("difficulty", "beginner");
                        args.putString("wpm", "120");
                        navController.navigate(submoduleIDs[1], args);
                        break;
                    case 2:
                        args.putString("smID", submoduleIDs[2] + 1 + "");
                        args.putString("difficulty", "intermediate");
                        args.putString("wpm", "250");
                        navController.navigate(submoduleIDs[1], args);
                        break;
                    case 3:
                        args.putString("smID", submoduleIDs[2] + 2 + "");
                        args.putString("difficulty", "advanced");
                        args.putString("wpm", "500");
                        navController.navigate(submoduleIDs[1], args);
                        break;
                }
            }
        });

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupHeader(View root, String moduleID) {
        int[] colors = moduleViewModel.getModuleGradientColors(moduleID);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(colors[1]);
        toolbar.setTitle(null);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(colors[1]);
        ConstraintLayout header = root.findViewById(R.id.module_header_container);
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, //set a gradient direction
                moduleViewModel.getModuleGradientColors()); //set the color of gradient
        gradientDrawable.setCornerRadii(new float[] {0, 0, 0, 0, 20, 20, 20, 20 });
        header.setBackground(gradientDrawable);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable getDrawable(String difficulty) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean complete = sharedPref.getBoolean(moduleViewModel.getTechnique() + "_" + difficulty + "_complete", false);

        Drawable r = (complete ? getResources().getDrawable(R.drawable.ic_checked_circle) : getResources().getDrawable(R.drawable.ic_empty_circle));
        r.setColorFilter(moduleViewModel.getModuleGradientColors(moduleViewModel.getModuleID())[0],
                PorterDuff.Mode.MULTIPLY);
        return r;
    }

    private int[] getSubmoduleIDs(String moduleID) {
        switch(moduleID) {
            case "1":
                return new int[] {R.id.nav_rsvp_intro,
                        R.id.nav_rsvp_module, 2
                };
            case "2":
                return new int[] {R.id.nav_metaguiding_intro,
                        R.id.nav_metaguiding_module, 6
                };
            case "3":
                return new int[] {R.id.nav_clumpreading_intro,
                        R.id.nav_clumpreading_module, 10
                };
            default:
                return new int[] {R.id.nav_prereading_intro,
                        R.id.nav_prereading_module, 14
                };
        }
    }

    private Drawable moduleGradientPicker(String moduleID) {
        Drawable moduleGradient = null;

        switch(moduleID) {
            case "1":
                moduleGradient = getResources().getDrawable(R.drawable.orange_gradient);
                break;
            case "2":
                moduleGradient =  getResources().getDrawable(R.drawable.green_gradient);
                break;
            case "3":
                moduleGradient =  getResources().getDrawable(R.drawable.pink_gradient);
                break;
            case "4":
                moduleGradient = getResources().getDrawable(R.drawable.blue_gradient);
            default:
                break;
        }

        return moduleGradient;
    }
}
