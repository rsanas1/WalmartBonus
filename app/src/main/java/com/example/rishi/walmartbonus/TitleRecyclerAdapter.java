package com.example.rishi.walmartbonus;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Product;


class TitleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


   private List<Product> products;
   private Context context;


    TitleRecyclerAdapter(Context context, List<Product> products){
       this.products = products;
       this.context = context;
   }



   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_row_item, parent, false);
           return new ItemViewHolder(view);

   }

   @Override
   public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

           ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

           //Typeface customFont = Typeface.createFromAsset(context.getAssets(), "font/calibril.ttf");

               String productName = products.get(position).getProductName();
               if (productName.length() > 31) {
                   itemViewHolder.name.setText(productName.substring(0, 31).concat("..."));
                   //  holder.name.setTypeface(customFont);
               } else {
                   itemViewHolder.name.setText(productName);
                   // holder.name.setTypeface(customFont);
               }

               itemViewHolder.price.setText(products.get(position).getPrice());
               // holder.price.setTypeface(customFont);

               Uri uri = Uri.parse(products.get(position).getProductImage());
               Picasso.with(context).load(uri).into(itemViewHolder.image);


               itemViewHolder.ratingBar.setIsIndicator(true);
               itemViewHolder.ratingBar.setEnabled(false);
               itemViewHolder.ratingBar.setVisibility(View.VISIBLE);
               itemViewHolder.ratingBar.setNumStars(5);
               itemViewHolder.ratingBar.setStepSize(0.1f);
               itemViewHolder.ratingBar.setRating(products.get(position).getReviewRating());
           }



   @Override
   public int getItemCount() {
       return products.size();
   }

   private class ItemViewHolder extends RecyclerView.ViewHolder{

       TextView name;
       TextView price;
       ImageView image;
       RatingBar ratingBar;

         ItemViewHolder(View itemView) {
           super(itemView);
           name = itemView.findViewById(R.id.nameTv);
           price = itemView.findViewById(R.id.priceTv);
           image =  itemView.findViewById(R.id.itemIv);
           ratingBar =  itemView.findViewById(R.id.rtBar);

       }
   }

}
