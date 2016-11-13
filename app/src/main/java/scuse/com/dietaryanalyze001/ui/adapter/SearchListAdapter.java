package scuse.com.dietaryanalyze001.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.subclass.Dishes;

/**
 * Created by Polylanger on 2016/9/24.
 */
public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "SearchListAdapter";

    private Context context;
    private List<Dishes> datas;

    private OnSearchItemClickListener onSearchItemClickListener;

    public SearchListAdapter(final Context context, List<Dishes> datas) {
        this.context = context;
        this.datas = datas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_search_grid, parent, false);
        final SearchDishesViewHolder holder = new SearchDishesViewHolder(view);
        holder.llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchItemClickListener.onDetailsClick(holder.getLayoutPosition());
            }
        });
        holder.ivMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchItemClickListener.onItemClick(holder.getLayoutPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchDishesViewHolder indexMealViewHolder = (SearchDishesViewHolder) holder;
        indexMealViewHolder.bindView(context, datas.get(holder.getLayoutPosition()));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnSearchItemClickListener(OnSearchItemClickListener onSearchItemClickListener) {
        this.onSearchItemClickListener = onSearchItemClickListener;
    }

    public interface OnSearchItemClickListener {
        void onDetailsClick(int position);

        void onItemClick(int position);
    }

    public static class SearchDishesViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_view_search_item)
        CardView container;
        @Bind(R.id.img_item_search_meal)
        SimpleDraweeView ivMeal;
        @Bind(R.id.tv_item_search_meal_name)
        TextView tvMealName;
        @Bind(R.id.tv_item_search_meal_info)
        TextView tvMealInfo;
        @Bind(R.id.ll_item_search_info_container)
        LinearLayout llContainer;

        public SearchDishesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Context context, Dishes dish) {
            ivMeal.setImageURI(dish.getImgFile());
            tvMealName.setText(dish.getName());
            tvMealInfo.setText(dish.getAllIngtsName());
        }
    }

}
