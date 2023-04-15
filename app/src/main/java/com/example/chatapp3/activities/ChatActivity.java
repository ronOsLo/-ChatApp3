package com.example.chatapp3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import com.example.chatapp3.adapters.ChatAdapter;
import com.example.chatapp3.databinding.ActivityChatBinding;
import com.example.chatapp3.models.ChatMessage;
import com.example.chatapp3.models.User;
import com.example.chatapp3.utilities.Constants;
import com.example.chatapp3.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;



public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private User receiverUser;
    private List<ChatMessage>   chatMessages;
    private ChatAdapter chatAdapter;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListener();
        loadReceiverDetails();
    }
     private void loadReceiverDetails(){
        receiverUser=(User) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.textName.setText(receiverUser.name);
     }

     private void setListener(){
        binding.imageBack.setOnClickListener(v -> onBackPressed());
     }
}