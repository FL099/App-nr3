package com.example.cardshow;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {

    private List<MagicCard> mItems;
    private Context mContext;
    private ListItemClickListener mListItemClickListener;


    public ListAdapter(List<MagicCard> mItems) {
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(position);
        Log.d("BINDLOG", String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return (mItems == null) ? 0 : mItems.size();
    }

    interface ListItemClickListener {
        void onListItemClick(MagicCard item);
    }

    public void setOnListItemClickListener(ListItemClickListener listItemClickListener){
        mListItemClickListener = listItemClickListener;
    }

    public void swapData(List<MagicCard> p){
        mItems = p;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvName;
        private TextView tvType;
        private LinearLayout llItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvType = itemView.findViewById(R.id.tv_type);
            llItem = itemView.findViewById(R.id.ll_item);
            itemView.setOnClickListener(this);

        }



        public void bind(int position) {
            tvName.setText(mItems.get(position).getName());
            tvType.setText(mItems.get(position).getType());
            llItem.setBackgroundResource(toCol(mItems.get(position).getRarity()));
        }

        private int toCol(String rarity){
            switch (rarity.toUpperCase()){
                case "RARE":
                    return R.color.rare;
                case "UNCOMMON":
                    return R.color.uncommon;
                case "COMMON":
                    return R.color.common;
                default:
                    return R.color.grey;
            }
        }

        @Override
        public void onClick(View v) {
            if (mListItemClickListener != null){
                int clickedIndex = getAdapterPosition();
                MagicCard card = mItems.get(clickedIndex);
                mListItemClickListener.onListItemClick(card);
            }
        }
    }
}

