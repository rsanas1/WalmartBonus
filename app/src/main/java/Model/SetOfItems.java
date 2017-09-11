package Model;

import java.util.List;

/**
 * Created by rishi on 9/7/2017.
 */

public class SetOfItems {

    private String id;
    private List<Product> products;
    private int totalProducts;
    private int pageNumber;
    private int pageSize;
    private int status;
    private String kind;
    private String etag;

    public String getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getStatus() {
        return status;
    }

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }
}
