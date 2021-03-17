package com.gitlab.capstone.pellego.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gitlab.capstone.pellego.database.daos.LibraryDao;
import com.gitlab.capstone.pellego.database.entities.Books;
import com.gitlab.capstone.pellego.network.APIService;
import com.gitlab.capstone.pellego.network.RetroInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksRepo {
    private LibraryDao libDao;
    private static BooksRepo sInstance;
    private final PellegoDatabase db;

    private LiveData<List<Books>> mAllBooks;

    public BooksRepo(Application application) {
        db = PellegoDatabase.getDatabase(application);
        libDao = db.libraryDao();
        mAllBooks = db.libraryDao().getBooks();
    }

    public void insertBook(Books book) {
        libDao.insert(book);
    }

//    LiveData<List<Books>> getAllBooks() {
//        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
//        Call<List<Books>> call = apiService.getBooks();
//        call.enqueue(new Callback<List<Books>>() {
//            @Override
//            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Books>> call, Throwable t) {
//
//            }
//        })
//        return mAllBooks;
//    }

    private static class InsertAsyncTask extends AsyncTask<Books, Void, Void> {
        private LibraryDao asyncLibDao;

        InsertAsyncTask(LibraryDao dao) {
            asyncLibDao = dao;
        }

        @Override
        protected Void doInBackground(final Books... params) {
            asyncLibDao.insert(params[0]);
            return null;
        }
    }
}
