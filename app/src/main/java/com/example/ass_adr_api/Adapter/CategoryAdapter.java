//package com.example.ass_adr_api.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.ass_adr_api.Models.CategoryList;
//import com.example.ass_adr_api.R;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//
//public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {
//    ArrayList<CategoryList> items;
//    Context context;
//
//    public CategoryAdapter(ArrayList<CategoryList> items) {
//        this.items = items;
//    }
//
//    @NonNull
//    @Override
//    public CategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        context = parent.getContext();
//        View inflater=LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
//        return new viewHolder(inflater);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CategoryAdapter.viewHolder holder, int position) {
//holder.titleTxt.setText(items.get(position).getTitle());
//int drawableResourceid = holder.itemView.getResources().geIndetifier(items.get(position).getImgPath(),"drawable",holder.itemView.getContext().getPackageName());
//
//        Glide.with(context).load(drawableResourceid)
//                .into(holder.pic);
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//    public class viewHolder extends RecyclerView.ViewHolder{
//        TextView titleTxt;
//        ImageView pic;
//
//        public viewHolder(@NonNull View itemView) {
//            super(itemView);
//            titleTxt = itemView.findViewById(R.id.textView);
//            pic = itemView.findViewById(R.id.imageView5);
//        }
//    }
//}
