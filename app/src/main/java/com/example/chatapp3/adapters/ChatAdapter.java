package com.example.chatapp3.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp3.databinding.ItemContainerReceivedMessageBinding;
import com.example.chatapp3.databinding.ItemContainerSentMessageBinding;
import com.example.chatapp3.models.ChatMessage;

import java.util.List;

public class ChatAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{





    private List<ChatMessage> chatMessage;
    private final Bitmap receiverProfileImage;
    private final String senderId;
    public static final int VIEW_TYPE_SENT =1;
    public static final int VIEW_TYPE_RECEIVED =1;

    public ChatAdapter(List<ChatMessage> chatMessage, Bitmap receiverProfileImage, String senderId) {
        this.chatMessage = chatMessage;
        this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT ){
            return new SentMessageViewHolder(
                    ItemContainerSentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }else {
            return new ReceivedMessegeViewHolder(
                    ItemContainerReceivedMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)== VIEW_TYPE_SENT){
            ((SentMessageViewHolder) holder).setData(chatMessage.get(position));
        }else {
            ((ReceivedMessegeViewHolder)holder).setData(chatMessage.get(position),receiverProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessage.size();
    }

    public int getViewType(int position){
        if(chatMessage.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT;
        }else {
            return VIEW_TYPE_RECEIVED;
        }
    }
    static  class SentMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerSentMessageBinding binding;

        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding){
            super(itemContainerSentMessageBinding.getRoot());
            binding = itemContainerSentMessageBinding;
        }

        void setData(ChatMessage chatMessage){
           binding.textMessege.setText(chatMessage.messege);
           binding.texDateTime.setText(chatMessage.dateTime);
        }
    }
    static class ReceivedMessegeViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerReceivedMessageBinding binding;

    ReceivedMessegeViewHolder(ItemContainerReceivedMessageBinding itemContainerReceivedMessageBinding){
        super(itemContainerReceivedMessageBinding.getRoot());
        binding = itemContainerReceivedMessageBinding;
    }
    void setData(ChatMessage chatMessage, Bitmap receiverProfileImage){
        binding.textMessege.setText(chatMessage.messege);
        binding.textMessege.setText(chatMessage.dateTime);
        binding.imageProfile.setImageBitmap(receiverProfileImage);
    }
    }
}
