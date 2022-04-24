package com.example.idocs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class GroupAdapter extends ListAdapter<Group, GroupAdapter.GroupHolder> {
    private OnItemClickListener listener;
    private Context context;

    public GroupAdapter(OnItemClickListener listener, Context context) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Group> DIFF_CALLBACK = new DiffUtil.ItemCallback<Group>() {
        @Override
        public boolean areItemsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
            return oldItem.getGroupId() == newItem.getGroupId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
            return oldItem.getGroupName().equals(newItem.getGroupName()) &&
                    oldItem.getWorkspaceId() == newItem.getWorkspaceId();
        }
    };

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workspace_layout, parent, false);
        return new GroupHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, int position) {
        Group currentGroup = getItem(position);
        holder.textViewName.setText(currentGroup.getGroupName());
        holder.textViewNoOfGroups.setText("0");
        holder.imageViewImg.setImageResource(R.drawable.ic_group);
    }

    public Group getItemAt(int position)
    {
        return getItem(position);
    }

    class GroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewName;
        private TextView textViewNoOfGroups;
        private ImageView imageViewImg;

        public GroupHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tv_workspace_name);
            textViewNoOfGroups = itemView.findViewById(R.id.tv_workspace_groups);
            imageViewImg = itemView.findViewById(R.id.workspace_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
            {
                GroupAdapter.this.listener.onItemClick(getItem(position));
            }
        }
    }

    interface OnItemClickListener
    {
        void onItemClick(Group group);
    }
}