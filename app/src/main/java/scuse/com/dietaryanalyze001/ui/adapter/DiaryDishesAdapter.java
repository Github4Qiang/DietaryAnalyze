package scuse.com.dietaryanalyze001.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import scuse.com.dietaryanalyze001.logic.bean.DiaryMealBean;
import scuse.com.dietaryanalyze001.logic.dgo.DiaryDatas;

/**
 * Created by Administrator on 2016/8/18.
 */
public class DiaryDishesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DIARY_ITEM_VIEW_TYPE_TITLE = 0;
    private static final int DIARY_ITEM_VIEW_TYPE_MEAL = 1;

    private Context context;
    private DiaryDatas diaryDatas;
    private OnMealItemClickListener onMealItemClickListener;

    public DiaryDishesAdapter(final Context context, DiaryDatas diaryDatas) {
        this.context = context;
        this.diaryDatas = diaryDatas;
    }

    @Override
    public int getItemCount() {
        return diaryDatas.getArrayDatas().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (diaryDatas.isTitle(position)) {
            return DIARY_ITEM_VIEW_TYPE_TITLE;
        }
        return DIARY_ITEM_VIEW_TYPE_MEAL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == DIARY_ITEM_VIEW_TYPE_TITLE) {
            DiaryTitleViewHolder titleHolder = (DiaryTitleViewHolder) holder;
            titleHolder.bindView((String) diaryDatas.getArrayDatas().get(holder.getLayoutPosition()));
        } else {
            DiaryMealViewHolder mealHolder = (DiaryMealViewHolder) holder;
            mealHolder.bindView(context, (DiaryMealBean) diaryDatas.getArrayDatas().get(holder.getLayoutPosition()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DIARY_ITEM_VIEW_TYPE_TITLE) {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_diary_title, parent, false);
            return new DiaryTitleViewHolder(view);
        } else {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_diary_meal, parent, false);
            final DiaryMealViewHolder holder = new DiaryMealViewHolder(view);
            holder.ibDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMealItemClickListener.onDetailsClick(holder.getLayoutPosition());
                }
            });
            return holder;
        }
    }

    public void setOnMealItemClickListener(OnMealItemClickListener onMealItemClickListener) {
        this.onMealItemClickListener = onMealItemClickListener;
    }

    public interface OnMealItemClickListener {
        void onDetailsClick(int position);
    }

    public static class DiaryTitleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_diary_title_time)
        TextView tvTime;
        @Bind(R.id.tv_item_diary_title_quality)
        TextView tvQuality;

        public DiaryTitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(String title) {
            tvTime.setText(title);
        }
    }

    public static class DiaryMealViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_item_index_meal)
        ImageView ivMeal;
        @Bind(R.id.tv_item_diary_meal_name)
        TextView tvMealName;
        @Bind(R.id.tv_item_diary_meal_quality)
        TextView tvMealQuality;
        @Bind(R.id.ib_diary_item)
        ImageButton ibDetails;

        public DiaryMealViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Context context, DiaryMealBean diaryMeal) {
            int imgSize = context.getResources().getDimensionPixelSize(R.dimen.item_recyclerview_img_size);

            Picasso.with(context)
                    .load(diaryMeal.getDiaryDish().getDish().getImgFile())
                    .resize(imgSize, imgSize)
                    .centerCrop()
                    .into(ivMeal);
            tvMealName.setText(diaryMeal.getDiaryDish().getDish().getName());
            tvMealQuality.setText(diaryMeal.getDiaryDish().getAmount() + " X " + diaryMeal.getDiaryDish().getUnit());
        }
    }
}
