package com.example.rishi.walmartbonus;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.Product;
import Presenter.DetailActivityPresenter;

/**
 * Created by rishi on 9/11/2017.
 */

public class DetailsFragment extends Fragment implements DetailActivityPresenter.DetailActivityPresenterListener {

    private RecyclerView recyclerView;
    public DetailRecyclerAdapter detailRecyclerAdapter;
    private LinearLayoutManager layoutManager;
    private List<Product> items;
    private ProgressBar progessBar;

    private boolean loading = true;
    int pastvisibleItems, visibleItemCount, totalItem;
    private int page;
    private DetailActivityPresenter detailActivityPresenter;

    public static DetailsFragment newInstance(int index) {
        DetailsFragment f = new DetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("position", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("position", 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.detail_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loading = true;

        items = new ArrayList<Product>();
        recyclerView =(RecyclerView) getView().findViewById(R.id.recyclerViewDetail);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progessBar = (ProgressBar) getView().findViewById(R.id.progress);
        detailActivityPresenter = new DetailActivityPresenter(this);
        detailActivityPresenter.onLoad();
        progessBar.setVisibility(View.VISIBLE);

        snapRecyclerView(recyclerView);
        Log.e("DETAIL FRAGMENT",Integer.toString(getShownIndex()));
        recyclerView.scrollToPosition(getShownIndex());


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


                if(dx>0){

                    visibleItemCount = layoutManager.getChildCount();
                    totalItem = layoutManager.getItemCount();
                    pastvisibleItems= layoutManager.findFirstVisibleItemPosition();


                    if(loading){

                        if((visibleItemCount + pastvisibleItems) >= totalItem ){
                            recyclerView.setVisibility(View.GONE);
                            progessBar.setVisibility(View.VISIBLE);
                            loading = false;
                            page++;
                            detailActivityPresenter.onUpdate(page);


                        }
                    }

                }
            }
        });


    }

    @Override
    public void assignList(List<Product> productList) {

        if(items.size() == 0)
        {
            items.addAll(productList);
            detailRecyclerAdapter = new DetailRecyclerAdapter(getActivity(), items);
            recyclerView.setAdapter(detailRecyclerAdapter);
            progessBar.setVisibility(View.GONE);
        }
        else
        {
            items.addAll(productList);
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    detailRecyclerAdapter.notifyDataSetChanged();
                }
            });

        }

        loading = true;
        progessBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void assignPage(int page) {
        this.page = page;
    }

    @Override
    public void displayFailure(Throwable t) {
        Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();
    }

    private void snapRecyclerView(RecyclerView recyclerView){

        LinearSnapHelper snapHelper = new LinearSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                View centerView = findSnapView(layoutManager);
                if (centerView == null)
                    return RecyclerView.NO_POSITION;

                int position = layoutManager.getPosition(centerView);
                int targetPosition = -1;
                if (layoutManager.canScrollHorizontally()) {
                    if (velocityX < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                if (layoutManager.canScrollVertically()) {
                    if (velocityY < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                final int firstItem = 0;
                final int lastItem = layoutManager.getItemCount() - 1;
                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));
                return targetPosition;
            }
        };
        snapHelper.attachToRecyclerView(recyclerView);
    }
}
