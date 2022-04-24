package com.example.idocs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder>
{
    private List<String> dataset;
    private OnItemClickListener listener;
    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final ImageView imageView;
        private final TextView textView_1;

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
                DocumentAdapter.this.listener.onItemClick(position);
        }

        public ViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.img_document);
            textView_1 = (TextView) view.findViewById(R.id.tv_document_name);
            view.setOnClickListener(this);
        }
    }

    interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public DocumentAdapter(List<String> dataset, OnItemClickListener listener)
    {
        this.dataset = dataset;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String currentItem = dataset.get(position);
        holder.imageView.setImageResource(R.drawable.ic_pdf);
        holder.textView_1.setText(currentItem);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
