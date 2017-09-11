package Service;


import Model.SetOfItems;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rishi on 9/7/2017.
 */

public class ItemService {

    public static final String BASE_URL = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/";

    public interface ItemAPI {

        @GET("walmartproducts/e0a4274f-45b6-405b-839e-1096222be4fc/{page}/30/")
        Call<SetOfItems> getResults(@Path("page") int page);

    }



    public ItemAPI getApi(){

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ItemAPI.class);
        }





    }


