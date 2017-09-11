package Presenter;


import java.util.List;

import Model.DataCenter;
import Model.Product;

/**
 * Created by rishi on 9/10/2017.
 */

public class MainActivityPresenter implements DataCenter.DataCenterListener {

    MainActivityPresenterListener activityPresenterListener;
    DataCenter dataCenter;

    public MainActivityPresenter(MainActivityPresenterListener activityPresenterListener){
        this.activityPresenterListener = activityPresenterListener;
        dataCenter = DataCenter.getDataCenterInstance();
    }

    @Override
    public void notifyDataReady(List<Product> products) {

            activityPresenterListener.display(products);

    }


    public void onLoad() {
        dataCenter.getProducts(1,this);

    }

    @Override
    public void notifyFailure(Throwable t) {
        activityPresenterListener.displayFailure(t);
    }


    public void update(int page) {
        dataCenter.getProducts(page,this);
    }

    public interface MainActivityPresenterListener{
         void display(List<Product> productList);
         void displayFailure(Throwable t);
    }
}
