package com.example.rishi.walmartbonus;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.Product;
import Presenter.MainActivityPresenter;

/**
 * Created by rishi on 9/11/2017.
 */

public class TitleFragment extends Fragment implements MainActivityPresenter.MainActivityPresenterListener {


    private RecyclerView recyclerView;
    public TitleRecyclerAdapter titleRecyclerAdapter;
    private LinearLayoutManager layoutManager;
    private List<Product> items;
    private Context context;
    private ProgressBar progessBar;

    private boolean loading = true;
    int pastvisibleItems, visibleItemCount, totalItem;
    private int page;
    private MainActivityPresenter mainActivityPresenter;


    boolean mDualPane;
    int mCurCheckPosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.title_fragment_layout, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loading = true;
        page = 1;
        items = new ArrayList<Product>();

        View detailsFrame = getActivity().findViewById(R.id.details);

        mDualPane = detailsFrame != null
                && detailsFrame.getVisibility() == View.VISIBLE;


        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progessBar = (ProgressBar) getView().findViewById(R.id.progress);
        mainActivityPresenter = new MainActivityPresenter(this);


        progessBar.setVisibility(View.VISIBLE);

        if(isOnline()) {
            mainActivityPresenter.onLoad();
        }
        else
        {
            Toast.makeText(getActivity(),"Internet Connection Needed", Toast.LENGTH_LONG).show();

        }

        recyclerView.addOnItemTouchListener(
                new MyRecyclerItemClickListener(getActivity(), new MyRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if(items.get(position) != null) {


                            showDetails(position);
                        }
                    }
                })
        );
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //   super.onScrolled(recyclerView, dx, dy);

                if(dy>0){

                    visibleItemCount = layoutManager.getChildCount();
                    totalItem = layoutManager.getItemCount();

                    pastvisibleItems= layoutManager.findFirstVisibleItemPosition();

                    if(loading){

                        if((visibleItemCount + pastvisibleItems) >= totalItem ){

                            recyclerView.setVisibility(View.GONE);
                            progessBar.setVisibility(View.VISIBLE);
                            loading = false;
                            page++;
                            mainActivityPresenter.update(page);

                        }
                    }

                }
            }
        });

    }

    void showDetails(int position) {
        mCurCheckPosition = position;

        // The basic design is mutli-pane (landscape on the phone) allows us
        // to display both fragments (titles and details) with in the same
        // activity; that is FragmentLayout -- one activity with two
        // fragments.
        // Else, it's single-pane (portrait on the phone) and we fire
        // another activity to render the details fragment - two activities
        // each with its own fragment .
        //
        if (mDualPane) {



            DetailsFragment details = (DetailsFragment) getFragmentManager()
                    .findFragmentById(R.id.details);
            if (details == null || details.getShownIndex() != position) {


                details = DetailsFragment.newInstance(position);


                FragmentTransaction ft = getFragmentManager()
                        .beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {

            Intent intent = new Intent();

            intent.setClass(getActivity(), DetailActivity.class);

            intent.putExtra("position", position);

            startActivity(intent);
        }
    }

    @Override
    public void display(List<Product> productList) {
        if(items.size() == 0)
        {
            items.addAll(productList);
            titleRecyclerAdapter = new TitleRecyclerAdapter(getActivity(), items);
            recyclerView.setAdapter(titleRecyclerAdapter);
        }
        else
        {
            items.addAll(productList);
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    titleRecyclerAdapter.notifyDataSetChanged();
                }
            });
        }

        loading = true;
        progessBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayFailure(Throwable t) {
        Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
