package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    //reference permite salvar dados no firebase
    //private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    //necessário para criar usuario no firebase
    private FirebaseAuth user = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*criar usuario no firebase IT WORKS!
        user.createUserWithEmailAndPassword(
                "marcus.ecomp@gmail.com", "123456")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("CreateUser", "Sucesso ao cadastrar usuario!");
                        }else{
                            Log.i("CreateUser", "Erro ao cadastrar usuario!");
                        }
                    }
                });*/

        /*verifica usuario logado*/
        if(user.getCurrentUser() != null){
            Log.i("LoggedUser", "Usuario esta logado!");
        }else{
            Log.i("LoggedUser", "Usuario não esta logado!");
        }


        /*DatabaseReference concorrentes = reference.child("concorrentes");
        concorrentes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("SERVANTE", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("SERVANTE", error.toException());
            }
        });*/

        //cria um novo nó sem filhos no firebase NÃO FUNCIONOU :(
        //reference.child("desafios").child("002").setValue("Rusical");

       /* DatabaseReference concorrentes = reference.child("concorrentes");
        Concorrente concorrente = new Concorrente();
        concorrente.setName("Detox");
        concorrente.setAge(34);
        concorrentes.child("003").setValue(concorrente);*/
    }
}