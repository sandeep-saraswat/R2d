package com.example.r2d

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class RegisterActivity : AppCompatActivity() {
    private var editTextName: EditText? = null
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private val editTextPhone: EditText? = null
    private var editTextcPassword: EditText? = null
    var UserRegisterBtn: Button? = null
    private var progressBar: ProgressBar? = null

  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        editTextName = findViewById(R.id.departmentName)
        editTextEmail = findViewById(R.id.emailRegister)
        editTextPassword = findViewById(R.id.passwordRegister)
        editTextcPassword = findViewById(R.id.confirmPassword)
        UserRegisterBtn = findViewById(R.id.button_register)
        //        editTextPhone = findViewById(R.id.edit_text_phone);
        progressBar = findViewById(R.id.progressbar)
        progressBar!!.visibility = View.GONE


        //  findViewById(R.id.button_register).setOnClickListener(this);
        UserRegisterBtn!!.setOnClickListener {        startActivity(
            Intent(
                this@RegisterActivity,
                DashboardActivity::class.java
            )
        ) }
    }

   override fun onStart() {
        super.onStart()

    }

    //    public void addStudent(){
    //        String studentNameValue = editTextName.getText().toString();
    //        String mcneeseIdValue = editTextEmail.getText().toString();
    //        if(!TextUtils.isEmpty(studentNameValue)&&!TextUtils.isEmpty(mcneeseIdValue)){
    //            String id = FirebaseDatabase.getInstance().getReference("Users").push().getKey();
    //            User students = new User(studentNameValue,mcneeseIdValue);
    //            // databaseReference.child(bttnName.getText().toString()).push().setValue(students);
    //            FirebaseDatabase.getInstance().getReference("Users").setValue(students);
    //            editTextName.setText("");
    //            editTextEmail.setText("");
    //            Toast.makeText(RegisterActivity.this,"Student Details Added",Toast.LENGTH_SHORT).show();
    //        }
    //        else{
    //            Toast.makeText(RegisterActivity.this,"Please Fill Fields",Toast.LENGTH_SHORT).show();
    //        }
    //    }
  //    //Set UserDisplay Name
    //    private void userProfile()
    //    {
    //        FirebaseUser user = mAuth.getCurrentUser();
    //        if(user!= null)
    //        {
    //            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
    //                    .setDisplayName(editTextName.getText().toString().trim())
    //                    //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))  // here you can set image link also.
    //                    .build();
    //
    //            user.updateProfile(profileUpdates)
    //                    .addOnCompleteListener(new OnCompleteListener<Void>() {
    //                        @Override
    //                        public void onComplete(@NonNull Task<Void> task) {
    //                            if (task.isSuccessful()) {
    //
    //                            }
    //                        }
    //                    });
    //        }
    //    }
}
