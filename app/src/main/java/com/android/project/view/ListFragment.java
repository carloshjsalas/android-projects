package com.android.project.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.project.R;
import com.android.project.application.Application;
import com.android.project.data.DataManager;
import com.android.project.databinding.FragmentListBinding;
import com.android.project.model.GalleryItem;
import com.android.project.model.GetPhotosResponse;
import com.android.project.viewmodel.ImageViewModel;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements SearchView.OnQueryTextListener {

    private CompositeSubscription mSubscriptions;
    private DataManager mDataManager;
    private FragmentListBinding mFragmentDataBinding;
    private List<GalleryItem> mImages = new ArrayList<>();
    private ImageViewModel mImagesViewModel;
    private Subscription mSubscription;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
        setHasOptionsMenu(true);
        mSubscriptions = new CompositeSubscription();
        mDataManager = Application.tdApplication().getComponent().dataManager();
        mImagesViewModel = new ImageViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return bindView(inflater.inflate(R.layout.fragment_list, container, false));
    }


    private View bindView(View view) {
        mFragmentDataBinding = DataBindingUtil.bind(view);
        mFragmentDataBinding.setViewModel(mImagesViewModel);
        return view;
    }

    private void loadImages(String queryToSearch) {
        if (mSubscription != null || mSubscriptions.hasSubscriptions()) {
            mSubscriptions.remove(mSubscription);
        }

        if (queryToSearch.length() >= 3) {
            mSubscription = mDataManager.getFlickrImagesUrl(queryToSearch)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(mDataManager.getScheduler())
                    .subscribe(new Subscriber<GetPhotosResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("Error", e.getMessage());
                        }

                        @Override
                        public void onNext(GetPhotosResponse result) {
                            if (result != null && result.getPhotoMeta() != null && result.getStat().equals("ok")) {
                                Log.d("Result", result.toString());
                                parseImages(result);
                            } else if (result.getStat().equals("fail")) {
                                showEmptyMessage();
                            }
                        }
                    });

            mSubscriptions.add(mSubscription);
        } else {
            mImagesViewModel.setImages(null);
        }
    }

    private void parseImages(GetPhotosResponse articleResponse) {
        mImages.clear();
        for (GalleryItem article : articleResponse.getPhotoMeta().getGalleryItems()) {
            mImages.add(article);
        }
        mImagesViewModel.setImages(mImages);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        loadImages(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    private void showEmptyMessage() {

    }

}
