package com.example.idocs.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idocs.R;
import com.example.idocs.models.data.Workspace;
import com.example.idocs.utils.Utils;

public class WorkspaceAdapter extends ListAdapter<Workspace, WorkspaceAdapter.WorkspaceHolder> {
    private OnItemClickListener listener;
    private Context context;

    public WorkspaceAdapter(OnItemClickListener listener, Context context) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Workspace> DIFF_CALLBACK = new DiffUtil.ItemCallback<Workspace>() {
        @Override
        public boolean areItemsTheSame(@NonNull Workspace oldItem, @NonNull Workspace newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Workspace oldItem, @NonNull Workspace newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getNoOfGroups() == newItem.getNoOfGroups();
        }
    };

    @NonNull
    @Override
    public WorkspaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workspace_layout, parent, false);
        return new WorkspaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkspaceHolder holder, int position) {
        Workspace currentWorkspace = getItem(position);
        holder.textViewName.setText(currentWorkspace.getName());
        holder.textViewNoOfGroups.setText(String.valueOf(currentWorkspace.getNoOfGroups()));
        if (currentWorkspace.getImage() == null || currentWorkspace.getPath() == null)
            holder.imageViewImg.setImageResource(R.drawable.ic_workspace);
        else
        {
            String path = currentWorkspace.getPath();
            String filename = currentWorkspace.getImage() + ".jpg";
            Utils utils = new Utils(context);
            Bitmap bitmap = utils.loadImageFromStorage(path, filename);
            holder.imageViewImg.setImageBitmap(bitmap);
        }
    }

    public Workspace getItemAt(int position)
    {
        return getItem(position);
    }

    class WorkspaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewName;
        private TextView textViewNoOfGroups;
        private ImageView imageViewImg;

        public WorkspaceHolder(View itemView) {
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
                WorkspaceAdapter.this.listener.onItemClick(getItem(position));
            }
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(Workspace workspace);
    }
}