package com.example.chatapp3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp3.databinding.ActivitySingInBinding;
import com.example.chatapp3.utilities.Constants;
import com.example.chatapp3.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SingInActivity extends AppCompatActivity {

    private ActivitySingInBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        binding = ActivitySingInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();

    }

    private void setListeners(){
       binding.txtnuevoUsuario.setOnClickListener(v ->
               startActivity(new Intent(getApplicationContext(), SingUpActivity.class)));
       binding.btnEntrar.setOnClickListener(v ->{
          if(isValidSingInDetails()){
              signIn();
          }
       });
    }

    private void signIn(){
    loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL,binding.inputEmail.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD,binding.inputPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                  if(task.isSuccessful() && task.getResult() != null
                  && task.getResult().getDocuments().size()>0) {
                      DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                      preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                      preferenceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId());
                      preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                      preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                      Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      startActivity(intent);
                  } else {
                      loading(false);
                      showToast("No se puede Acceder");
                  }
                });
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.btnEntrar.setVisibility(View.INVISIBLE);
            binding.progessbar.setVisibility(View.VISIBLE);
        } else{
            binding.btnEntrar.setVisibility(View.VISIBLE);
            binding.progessbar.setVisibility(View.INVISIBLE);

        }
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidSingInDetails(){
        if(binding.inputEmail.getText().toString().trim().isEmpty()){
            showToast("Ingrese su Correo");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()){
            showToast("Ingrese Correo Valido");
            return false;
        }else{
            return true;
        }
    }



}