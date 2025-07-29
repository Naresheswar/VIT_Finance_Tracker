package com.example.vitfinancetracker

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth


class Login : AppCompatActivity() {
    private val googleSignInRequestCode = 234

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth here
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this,gso) // Renamed for clarity

        val loginButton = findViewById<Button>(R.id.button)
        loginButton.setOnClickListener {
            if(isConnected(this)){
                val signIntent = googleSignInClient.signInIntent
                startActivityForResult(signIntent,googleSignInRequestCode)
            }else{
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            googleSignInRequestCode -> {
                try{
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account)
                }catch(e: ApiException){
                    e.printStackTrace()
                    Toast.makeText(this, "Google sign in failed: ${e.statusCode}", Toast.LENGTH_LONG).show() // More specific error
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val email = account.email

        // REGEX to check for "vitstudent.ac.in" domain
        val vitEmailRegex = Regex("^[a-zA-Z0-9._%+-]+@vitstudent\\.ac\\.in$")

        if (email != null && vitEmailRegex.matches(email)) {
            val credential = GoogleAuthProvider.getCredential(account.idToken,null)
            // Use the 'auth' member variable here
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task -> // Pass 'this' as the Activity for lifecycle management
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        val mainIntent = Intent(this, MainActivity::class.java)
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(mainIntent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Authentication Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        task.exception?.printStackTrace()
                    }
                }
        } else {
            // Email does not match the required domain
            Toast.makeText(this, "Only VIT student Email ID is allowed.", Toast.LENGTH_LONG).show()
            GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // User is not signed in, stay on Login activity.
            // No action needed here, as the login button is visible.
        }
    }
}

private fun isConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}