package Model;

import android.text.Html;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Product implements Serializable{

    @SerializedName("productId")
    private String productId;

    @SerializedName("productName")
    private String productName;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("longDescription")
    private String longDescription;

    @SerializedName("price")
    private String price;

    @SerializedName("productImage")
    private String productImage;

    @SerializedName("reviewRating")
    private float reviewRating;

    @SerializedName("reviewCount")
    private int reviewCount;

    @SerializedName("inStock")
    private boolean inStock;


    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return stripHtml(productName) ;
    }

    public String getShortDescription() {
        return stripHtml(shortDescription);
    }

    public String getLongDescription() {
        return stripHtml(longDescription);
    }

    public String getPrice() {
        return stripHtml(price);
    }

    public String getProductImage() {
        return productImage;
    }

    public float getReviewRating() {
        return reviewRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public boolean isInStock() {
        return inStock;
    }

    @SuppressWarnings("deprecation")
    private String stripHtml(String html) {

        if(html == null || html.isEmpty())
            return "";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString().replaceAll("[^\\x00-\\x7F]", "");
        } else {
            return Html.fromHtml(html).toString().replaceAll("[^\\x00-\\x7F]", "");
        }

    }

    @Override
    public String toString() {
        return productName;
    }
}
