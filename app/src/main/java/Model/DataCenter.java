package Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Service.ItemService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataCenter {

    private final ItemService itemService;
    //private final DataCenterListener dataCenterListener;
    Map<Integer,List<Product>> map;
    //private List<Product> products;

    private static DataCenter dataCenterInstance;

    private DataCenter(){
        this.itemService = new ItemService();
        map = new HashMap<Integer, List<Product>>();
    }

    public static DataCenter getDataCenterInstance(){
        if(dataCenterInstance == null){
            synchronized (DataCenter.class){
                if(dataCenterInstance == null){
                    dataCenterInstance = new DataCenter();
                }
            }
        }
        return dataCenterInstance;
    }


    public void getProducts(final int page, final DataCenterListener dataCenterListener){

        if(map.containsKey(page)){
            Log.d("DataCenter" ,"Page "+page+" was available");
            dataCenterListener.notifyDataReady(map.get(page));
            return;
        }

        itemService
                .getApi()
                .getResults(page)
                .enqueue(new Callback<SetOfItems>() {
                    @Override
                    public void onResponse(Call<SetOfItems> call, Response<SetOfItems> response) {
                        SetOfItems result =response.body();

                        if(result!=null){
                            map.put(page, result.getProducts());
                            Log.d("DataCenter" ,"Page "+page+" fetched");
                            dataCenterListener.notifyDataReady(result.getProducts());

                        }
                    }

                    @Override
                    public void onFailure(Call<SetOfItems> call, Throwable t) {

                        try {
                            throw new InterruptedException("An error occured while communicating with server");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finally {
                           dataCenterListener.notifyFailure(t);
                        }

                    }
                });
    }

    public void getProducts(DataCenterListener dataCenterListener){
        List<Product> list = new ArrayList<>();
        Log.e("LIST SIZE",map.size()+"");
        for(int i = 1;i<=map.size();i++){
            list.addAll(map.get(i));
        }
        dataCenterListener.notifyDataReady(list);
    }

    public int getPages(){
        return map.size();
    }





    public interface DataCenterListener{

         void notifyDataReady(List<Product> products);
         void notifyFailure(Throwable t);

    }
}
