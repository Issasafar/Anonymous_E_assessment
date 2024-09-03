package com.issasafar.anonymouse_assessment.views.teacher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.issasafar.anonymouse_assessment.data.models.common.UnderstandingMessage;
import com.issasafar.anonymouse_assessment.databinding.MessageItemBinding;

import java.util.ArrayList;

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.ViewHolder> {
    private ArrayList<UnderstandingMessage> messages = new ArrayList<>();

    public MessageRecyclerAdapter(ArrayList<UnderstandingMessage> messages) {
        this.messages = messages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private MessageItemBinding messageItemBinding;
        public ViewHolder(@NonNull View itemView,MessageItemBinding messageItemBinding) {
            super(itemView);
            this.messageItemBinding = messageItemBinding;
        }
    }
    @NonNull
    @Override
    public MessageRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MessageItemBinding messageItemBinding = MessageItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(messageItemBinding.getRoot(),messageItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageRecyclerAdapter.ViewHolder holder, int position) {
        holder.messageItemBinding.messageText.setText(messages.get(position).getMessage());
        holder.messageItemBinding.studentNameText.setText(messages.get(position).getStudent_name()+": ");
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
