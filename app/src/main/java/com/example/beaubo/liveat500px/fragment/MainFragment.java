package com.example.beaubo.liveat500px.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.beaubo.liveat500px.R;
import com.example.beaubo.liveat500px.adapter.PhotoListAdapter;
import com.example.beaubo.liveat500px.dao.PhotoItemCollectionDao;
import com.example.beaubo.liveat500px.manager.HttpManager;
import com.example.beaubo.liveat500px.manager.PhotoListManager;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {

    ListView listView;
    PhotoListAdapter listAdapter;

    SwipeRefreshLayout swipeRefreshLayout;

    PhotoListManager photoListManager;
    Button btnNewPhotos;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        photoListManager = new PhotoListManager();
        btnNewPhotos = (Button) rootView.findViewById(R.id.btnNewPhotos);

        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView)rootView.findViewById(R.id.listView);
        listAdapter = new PhotoListAdapter();
        listView.setAdapter(listAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();

            }
        });


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                         @Override
                                         public void onScrollStateChanged(AbsListView view, int scrollState) {

                                         }

                                         @Override
                                         public void onScroll(AbsListView view,
                                                              int firstVisibleItem,
                                                              int visibleItemCount,
                                                              int totalItemCount) {
                                             swipeRefreshLayout.setEnabled(firstVisibleItem == 0);

                                         }
                                     });
        refreshData();
    }
    private void refreshData() {
       if (photoListManager.getCount() == 0)
        reloadData();
       else
        reloadDataNewer();
}

    private void reloadDataNewer() {
        int maxId = photoListManager.getMaximumId();

        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoListAfterId(maxId);
        call.enqueue(new Callback<PhotoItemCollectionDao>() {
            @Override
            public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {

                swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful()){
                    PhotoItemCollectionDao dao = response.body();

                    photoListManager.setDao(dao);
                    listAdapter.setDao(dao);
                    listAdapter.notifyDataSetChanged();
                    Toast.makeText(Contextor.getInstance().getContext(),
                           "Load Completed",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {

                    //Handle
                    try {
                        Toast.makeText(Contextor.getInstance().getContext(),
                                response.errorBody().string(),
                                Toast.LENGTH_SHORT)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
                /// Handle
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(Contextor.getInstance().getContext(),
                        t.toString(),
                        Toast.LENGTH_SHORT)
                        .show();


            }
        });
    }

    private void reloadData() {
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new Callback<PhotoItemCollectionDao>() {
            @Override
            public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
                swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful()){
                    PhotoItemCollectionDao dao = response.body();
                    photoListManager.setDao(dao);
                    listAdapter.setDao(dao);
                    listAdapter.notifyDataSetChanged();
                    Toast.makeText(Contextor.getInstance().getContext(),
                            dao.getData().get(0).getCaption(),
                            Toast.LENGTH_SHORT)
                            .show();
                } else {

                    //Handle
                    try {
                        Toast.makeText(Contextor.getInstance().getContext(),
                                response.errorBody().string(),
                                Toast.LENGTH_SHORT)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);

                /// Handle
                Toast.makeText(Contextor.getInstance().getContext(),
                       t.toString(),
                        Toast.LENGTH_SHORT)
                        .show();

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
    public void showButtonNewPhotos(){
        btnNewPhotos.setVisibility(View.VISIBLE);

    }
    public void hideButtonNewPhotos(){
        btnNewPhotos.setVisibility(View.GONE);

    }
}
