package com.android.project.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
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
import com.android.project.model.Article;
import com.android.project.model.ArticleResponse;
import com.android.project.viewmodel.ArticleViewModel;

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
    private List<Article> mArticles = new ArrayList<>();
    private ArticleViewModel mArticleViewModel;

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
        mArticleViewModel = new ArticleViewModel(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return bindView(inflater.inflate(R.layout.fragment_list, container, false));
    }


    private View bindView(View view) {
        mFragmentDataBinding = DataBindingUtil.bind(view);
        mFragmentDataBinding.setViewModel(mArticleViewModel);

        mFragmentDataBinding.listArticles.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);



            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadArticles();
    }

    private void loadArticles() {
        Subscription subscription = mDataManager.getImagesUrl()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(mDataManager.getScheduler())
                .subscribe(new Subscriber<ArticleResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error", e.getMessage());
                    }

                    @Override
                    public void onNext(ArticleResponse result) {
                        if (result != null && result.getArticles() != null) {
                            Log.d("Result", result.toString());
                            parseImages(result);
                        }
                    }
                });

        mSubscriptions.add(subscription);
    }

    private void parseImages(ArticleResponse articleResponse) {
        mArticles.clear();
        for (Article article : articleResponse.getArticles()) {
            if (article.getType() != null && article.getType().equals("image/jpeg")) {
                mArticles.add(article);
            }
        }
        mArticleViewModel.setArticles(mArticles);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Article> filteredlList = filter(mArticles, newText);
        mArticleViewModel.setArticles(filteredlList);
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

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        menu.findItem(R.id.action_order).setVisible(true);
                        mArticleViewModel.setArticles(mArticles);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        menu.findItem(R.id.action_order).setVisible(false);
                        return true; // Return true to expand action view
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order:
                if (mArticleViewModel != null) {
                    mArticleViewModel.orderList();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<Article> filter(List<Article> articles, String query) {
        query = query.toLowerCase();

        final List<Article> filteredList = new ArrayList<>();
        for (Article article : articles) {
            final String text = article.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(article);
            }
        }
        return filteredList;
    }

}
