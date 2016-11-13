package scuse.com.dietaryanalyze001.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.dgo.IndexMealDatas;
import scuse.com.dietaryanalyze001.logic.subclass.Dishes;

/**
 * Created by Administrator on 2016/8/18.
 */
public class IndexListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "IndexListAdapter";

    private static final int INDEX_ITEM_VIEW_TYPE_TITLE = 0;
    private static final int INDEX_ITEM_VIEW_TYPE_MEAL = 1;

    private Context context;
    private OnIndexItemClickListener onIndexItemClickListener;
    private IndexMealDatas mDatas;

    public IndexListAdapter(final Context context, IndexMealDatas mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.getArrayDatas().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.isTitle(position)) {
            return INDEX_ITEM_VIEW_TYPE_TITLE;
        }
        return INDEX_ITEM_VIEW_TYPE_MEAL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == INDEX_ITEM_VIEW_TYPE_TITLE) {
            IndexTitleViewHolder titleHolder = (IndexTitleViewHolder) holder;
            titleHolder.bindView((String) mDatas.getArrayDatas().get(holder.getLayoutPosition()));
        } else {
            IndexMealViewHolder mealHolder = (IndexMealViewHolder) holder;
            mealHolder.bindView(context, (Dishes) mDatas.getArrayDatas().get(holder.getLayoutPosition()));
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == INDEX_ITEM_VIEW_TYPE_TITLE) {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_index_title, parent, false);
            return new IndexTitleViewHolder(view);
        } else {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_index_meal, parent, false);
            final IndexMealViewHolder holder = new IndexMealViewHolder(view);
            holder.ibDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onIndexItemClickListener.onDetailsClick(holder.getLayoutPosition());
                }
            });
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onIndexItemClickListener.onItemClick(holder.getLayoutPosition());
                }
            });
            return holder;
        }
    }

    public void setOnIndexItemClickListener(OnIndexItemClickListener onIndexItemClickListener) {
        this.onIndexItemClickListener = onIndexItemClickListener;
    }

    public interface OnIndexItemClickListener {
        void onDetailsClick(int position);

        void onItemClick(int position);
    }

    public static class IndexTitleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_index_title_letter)
        TextView tvTime;

        public IndexTitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(String title) {
            tvTime.setText(title);
        }
    }

    public static class IndexMealViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_view_diary_item)
        CardView container;
        @Bind(R.id.img_item_index_meal)
        ImageView ivMeal;
        @Bind(R.id.tv_item_diary_meal_name)
        TextView tvMealName;
        @Bind(R.id.tv_item_index_meal_info)
        TextView tvMealInfo;
        @Bind(R.id.ib_diary_item)
        ImageButton ibDetails;

        public IndexMealViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Context context, Dishes dish) {
            int imgSize = context.getResources().getDimensionPixelSize(R.dimen.item_recyclerview_img_size);

            Picasso picasso = new Picasso.Builder(context)
                    .listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            exception.printStackTrace();
                            Log.e(TAG, "onImageLoadFailed: ", exception);
                        }
                    })
                    .build();

            picasso.load(dish.getImgFile())
                    .resize(imgSize, imgSize)
                    .error(R.drawable.img_loading)
                    .centerCrop()
                    .into(ivMeal);

            Log.d(TAG, "bindView: " + dish.getImgFile());
            tvMealName.setText(dish.getName());
            tvMealInfo.setText(dish.getAllIngtsName());
        }
    }
}
