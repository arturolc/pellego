package com.gitlab.capstone.pellego.fragments.learn;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitlab.capstone.pellego.database.LearningModulesRepo;
import com.gitlab.capstone.pellego.database.UsersRepo;
import com.gitlab.capstone.pellego.network.models.CompletionResponse;
import com.gitlab.capstone.pellego.network.models.LMResponse;

import java.util.List;

/**********************************************
 Chris Bordoy & Arturo Lara

 The Learn Fragment view model
 **********************************************/

public class LearnViewModel extends AndroidViewModel {
    private final LearningModulesRepo learningModulesRepo;
    private final UsersRepo usersRepo;
    private LiveData<List<LMResponse>> lmResponse;
    private LiveData<List<CompletionResponse>> completionResponse;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LearnViewModel(@NonNull Application application) {
        super(application);
        learningModulesRepo = LearningModulesRepo.getInstance(application);
        usersRepo = UsersRepo.getInstance(application);
        lmResponse = new MutableLiveData<>();
        completionResponse = new MutableLiveData<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LiveData<List<LMResponse>> getLMResponse() {
        lmResponse = learningModulesRepo.getModules();

        return lmResponse;
    }

    public LiveData<List<CompletionResponse>> getCompletionResponse() {
        completionResponse = usersRepo.getUserLearningModulesCompletionCount();

        return completionResponse;
    }

    public int[] parseCompletionResponses(List<CompletionResponse> resp) {
        int[] responses = new int[4];

        for (int i = 0; i < resp.size(); i++) {
            switch(resp.get(i).getMid()) {
                case 1:
                    responses[0] += 1;
                    break;
                case 2:
                    responses[1] += 1;
                    break;
                case 3:
                    responses[2] += 1;
                    break;
                case 4:
                    responses[3] += 1;
                    break;
                default:
                    break;
            }
        }

        return responses;
    }
}
