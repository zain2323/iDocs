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

public class GroupDocumentsAdapter extends ListAdapter<Document, GroupDocumentsAdapter.DocumentHolder> {
    private GroupDocumentsAdapter.OnItemClickListener listener;
    private Context context;

    public GroupDocumentsAdapter(OnItemClickListener listener, Context context) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Document> DIFF_CALLBACK = new DiffUtil.ItemCallback<Document>() {
        @Override
        public boolean areItemsTheSame(@NonNull Document oldItem, @NonNull Document newItem) {
            return oldItem.getDocument_id() == newItem.getDocument_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Document oldItem, @NonNull Document newItem) {
            return oldItem.getDocumentName().equals(newItem.getDocumentName()) &&
                    oldItem.getGroupId() == newItem.getGroupId() &&
                    oldItem.getDocumentUri().equals(newItem.getDocumentUri());
        }
    };

    @NonNull
    @Override
    public GroupDocumentsAdapter.DocumentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_grid_layout, parent, false);
        return new GroupDocumentsAdapter.DocumentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupDocumentsAdapter.DocumentHolder holder, int position) {
        Document currentGroup = getItem(position);
        holder.textViewName.setText(currentGroup.getDocumentName());
        holder.imageViewImg.setImageResource(R.drawable.google_docs);
    }

    public Document getItemAt(int position)
    {
        return getItem(position);
    }

    class DocumentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewName;
        private ImageView imageViewImg;

        public DocumentHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tv_pdf_name);
            imageViewImg = itemView.findViewById(R.id.document_imageview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
            {
                GroupDocumentsAdapter.this.listener.onItemClick(getItem(position));
            }
        }
    }

    interface OnItemClickListener
    {
        void onItemClick(Document document);
    }

}
