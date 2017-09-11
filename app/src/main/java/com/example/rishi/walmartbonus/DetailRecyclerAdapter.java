package com.example.rishi.walmartbonus;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Product;

/**
 * Created by rishi on 9/9/2017.
 */

public class DetailRecyclerAdapter extends RecyclerView.Adapter<DetailRecyclerAdapter.DetailViewHolder> {


        private List<Product> products;
        private Context context;



        public DetailRecyclerAdapter(Context context, List<Product> products){
            this.products = products;
            this.context = context;
        }


        @Override
        public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_row_item, parent, false);
                return new DetailViewHolder(view);

        }

        @Override
        public void onBindViewHolder(DetailViewHolder holder, int position) {

           // Typeface customFont = Typeface.createFromAsset(context.getAssets(), "font/calibril.ttf");


                String productName = products.get(position).getProductName();
                holder.productNameTv.setText(productName);
           //     holder.productNameTv.setTypeface(customFont);


                String productDescription = products.get(position).getLongDescription();
                if(!productDescription.isEmpty())
                holder.productDescTv.setVisibility(View.VISIBLE);

                holder.productLongDescTv.setText(productDescription);
          //      holder.productDescTv.setTypeface(customFont);




                Uri uri = Uri.parse(products.get(position).getProductImage().toString());
                Picasso.with(context).load(uri).into(holder.productIv);


                holder.productRatingsTv.setText(String.format("%.1f", products.get(position).getReviewRating()));

                holder.productPrice.setText(products.get(position).getPrice());

                int ratingCount = products.get(position).getReviewCount();
                if(ratingCount != 1)
                 holder.ratingCount.setText(ratingCount+" "+context.getString(R.string.reviews));

                else
                    holder.ratingCount.setText(ratingCount+" "+context.getString(R.string.review));

                holder.ratingsRb.setIsIndicator(true);
                holder.ratingsRb.setEnabled(false);
                holder.ratingsRb.setVisibility(View.VISIBLE);
                holder.ratingsRb.setNumStars(5);
                holder.ratingsRb.setStepSize(0.1f);
                holder.ratingsRb.setRating( products.get(position).getReviewRating());
                holder.scrollView.smoothScrollTo(0,0);

                if(products.get(position).isInStock()){
                    holder.availabilityTv.setText(R.string.available);
                    holder.availabilityTv.setBackgroundColor(context.getResources().getColor(R.color.yellow_800));
                    holder.availabilityTv.setTextColor(context.getResources().getColor(R.color.black));
                }
                else
                {
                    holder.availabilityTv.setText(R.string.not_available);
                    holder.availabilityTv.setTextColor(context.getResources().getColor(R.color.grey_400));
                    holder.availabilityTv.setBackgroundColor(context.getResources().getColor(R.color.grey_300));
                }
            }


        @Override
        public int getItemCount() {
            return products.size();
        }

        public class DetailViewHolder extends RecyclerView.ViewHolder{

            ImageView productIv;
            TextView productNameTv;
            TextView productRatingsTv;
            RatingBar ratingsRb;
            TextView productDescTv;
            TextView productLongDescTv;
            ScrollView scrollView;
            TextView ratingCount;
            TextView productPrice;
            TextView availabilityTv;



            public DetailViewHolder(View itemView) {
                super(itemView);
                productIv =  itemView.findViewById(R.id.productImage);
                productNameTv = itemView.findViewById(R.id.productName);
                productRatingsTv =  itemView.findViewById(R.id.product_ratings);
                ratingsRb= itemView.findViewById(R.id.rtBar);
                productDescTv =  itemView.findViewById(R.id.productDescription);
                productLongDescTv = itemView.findViewById(R.id.productLongDescription);
                scrollView =  itemView.findViewById(R.id.ScrollView01);
                ratingCount = itemView.findViewById(R.id.rating_count);
                productPrice = itemView.findViewById(R.id.product_price);
                availabilityTv = itemView.findViewById(R.id.availabilityTv);


            }
        }


    }

