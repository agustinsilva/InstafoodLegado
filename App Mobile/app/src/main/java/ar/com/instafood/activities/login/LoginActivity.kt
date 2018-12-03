package ar.com.instafood.activities.login

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import ar.com.instafood.activities.MainActivity
import ar.com.instafood.activities.R
import ar.com.instafood.application.SocketApplication
import ar.com.instafood.fragments.MenuFragment
import ar.com.instafood.fragments.menuFragments.ProductDetailFragment
import com.google.firebase.auth.FirebaseAuth
import io.socket.client.Socket
import kotlinx.android.synthetic.main.activity_menu.*

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.decorView.setBackgroundColor(Color.WHITE)
        txtUser = findViewById(R.id.txtUser)
        txtPassword = findViewById(R.id.txtPassword)
        progressBar = findViewById(R.id.progressBar)
        auth = FirebaseAuth.getInstance()
    }

    fun forgotPassword(view: View){
        startActivity(Intent(this, ForgotPass::class.java))
    }

    fun register(view: View){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun login(view: View){
        loginUser()
    }

    private fun loginUser(){
        var user:String = txtUser.text.toString()
        val password:String = txtPassword.text.toString()

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            progressBar.visibility=View.VISIBLE

            auth.signInWithEmailAndPassword(user,password)
                .addOnCompleteListener(this){
                    task ->
                    if(task.isSuccessful){
                        user = getUserWithoutMail(user)
                        ProductDetailFragment.userName = user
                        val app = application as SocketApplication
                        var intent_result = Intent()
                        intent_result.putExtra("username_result", user)
                        intent_result.putExtra("activity_id", 3)
                        this!!.setResult(Activity.RESULT_OK, intent_result)
                        app.socket?.emit("connectedSocket", user)
                        this.finish()
                        //action()
                    }
                else{
                        progressBar.visibility=View.GONE
                        Toast.makeText(this,"Error al logear", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun getUserWithoutMail(username: String): String
    {
        // Return substring containing all characters before a string.
        if (username.indexOf("@") == -1) {
            return ""
        }
        return username.substring(0, username.indexOf("@"))
    }

    private fun action(){

        val fragment = fragmentManager!!.findFragmentByTag("MENU_FRAGMENT") as MenuFragment
        //val transaction = fragmentManager!!.beginTransaction()
        //transaction.replace(R.id.fragment_container, fragment, "MENU_FRAGMENT");
        //transaction.addToBackStack(null);
        //transaction.commit();
        startActivityFromFragment(fragment, Intent(this, MainActivity::class.java),1)
        //startActivity(Intent(this, MainActivity::class.java))
    }
}
