package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    //reference permite salvar dados no firebase
    //private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    //necessário para criar usuario no firebase
    private FirebaseAuth user = FirebaseAuth.getInstance();

    private ImageView imageUpload;
    private Button buttonUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonUpload = findViewById(R.id.buttonUpload);
        imageUpload = findViewById(R.id.imageView);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //configura para imagem ser salva em memoria
                imageUpload.setDrawingCacheEnabled(true);
                imageUpload.buildDrawingCache();
                //recupera bitmap da imagem
                Bitmap bitmap = imageUpload.getDrawingCache();
                // comprimir bitmap para formato png/jpeg
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos );
                //converte baos para pixel brutos em uma matriz de bytes, requerido pelo firebase
                byte[] dadosImagem = baos.toByteArray();
                //define nós para o storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference images = storageReference.child("images");
                //nome da imagem
                String nomeArquivo = UUID.randomUUID().toString();
                StorageReference imageRef = images.child(nomeArquivo + ".jpeg");
                //retorna objeto que ira controlar o upload
                UploadTask uploadTask = imageRef.putBytes(dadosImagem);
                uploadTask.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,
                                "Upload da imagem falhou" + e.getMessage().toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Uri url = task.getResult();
                                Toast.makeText(MainActivity.this,
                                        "Upload da imagem feito com sucesso: " + url.toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
            }
        });

        /*FILTROS IT WORKS
        DatabaseReference concorrentes = reference.child("concorrentes");
        DatabaseReference concorrentePesquisa = concorrentes.child("-MW9oDX8Jv3rbyOyU8jR");
        concorrentePesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Concorrente dadosConcorrente = dataSnapshot.getValue(Concorrente.class);
                //Log.i("Dados concorrente: ", "nome: " + dadosConcorrente.getName());
                Log.i("Dados concorrente: ", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })*/
        /*filtros utilizando query IT WORKS
        DatabaseReference concorrentes = reference.child("concorrentes");
        //Query concorrentePesquisa = concorrentes.orderByChild("name").equalTo("Detox");
        Query concorrentePesquisa = concorrentes.orderByKey().limitToFirst(3);
        concorrentePesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Concorrente dadosConcorrente = dataSnapshot.getValue(Concorrente.class);
                //Log.i("Dados concorrente: ", "nome: " + dadosConcorrente.getName());
                Log.i("Dados concorrente: ", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })*/
        /*adicionar usuário e gerar id automaticamente IT WORKS!
        DatabaseReference concorrentes = reference.child("concorrentes");
        Concorrente concorrente = new Concorrente();
        concorrente.setName("Chad Michaels");
        concorrente.setAge(50);

        concorrentes.push().setValue(concorrente);*/

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
;

        /*deslogar usuario IT WORKS!
        user.signOut();*/

        /*logar usuario IT WORKS!*/
        user.signInWithEmailAndPassword(
                "marcus.ecomp@gmail.com", "123456")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("SigninUser", "Sucesso ao logar usuario!");
                        }else{
                            Log.i("SigninUser", "Erro ao logar usuario!");
                        }
                    }
                });

        /*verifica usuario logado IT WORKS!
        if(user.getCurrentUser() != null){
            Log.i("LoggedUser", "Usuario esta logado!");
        }else{
            Log.i("LoggedUser", "Usuario não esta logado!");
        }*/




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

        //cria um novo nó sem filhos no firebase
        //reference.child("desafios").child("001").setValue("Rusical");


    }


}