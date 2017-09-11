package Presenter;


import java.util.List;

import Model.DataCenter;
import Model.Product;


public class DetailActivityPresenter implements DataCenter.DataCenterListener {

    public DetailActivityPresenterListener detailActivityPresenterListener;
    DataCenter dataCenter;

    public DetailActivityPresenter(DetailActivityPresenterListener detailActivityPresenterListener){
        this.detailActivityPresenterListener = detailActivityPresenterListener;
        dataCenter = DataCenter.getDataCenterInstance();
    }
    @Override
    public void notifyDataReady(List<Product> products) {
            detailActivityPresenterListener.assignList(products);
    }

    @Override
    public void notifyFailure(Throwable t) {
        detailActivityPresenterListener.displayFailure(t);
    }

    public void onLoad() {
        dataCenter.getProducts(this);


        detailActivityPresenterListener.assignPage(dataCenter.getPages());
    }

    public void onUpdate(int page) {
        dataCenter.getProducts(page,this);
    }

    public interface DetailActivityPresenterListener{
         void assignList(List<Product> productList);
         void assignPage(int page);
         void displayFailure(Throwable t);
    }
}
