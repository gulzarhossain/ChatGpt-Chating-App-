package com.project.chatgpt

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.chatgpt.Utils.AppUtility.APP_VERSION
import com.project.chatgpt.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    val dbref=Firebase.database.getReferenceFromUrl("https://chatgpt-5941d-default-rtdb.firebaseio.com/APP_VERSION")
    var open:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_splash)
        open=1
        dbref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("version").value.toString().toInt()==APP_VERSION){
                    if (open==1){
                    startActivity(Intent(this@Splash,Login::class.java))
                    finish()
                        open=0
                    }
                }else if(snapshot.child("version").value.toString().toInt()==0){
                    val alertDialog=AlertDialog.Builder(this@Splash).create()
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Maintenance",object :DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            Log.e("Exit","")
                        }
                    })
                    alertDialog.show()
                }else{
                    val alertDialog=AlertDialog.Builder(this@Splash).create()
                    alertDialog.setIcon(R.mipmap.ic_launcher_round)
                    alertDialog.setTitle("ChatGPT")
                    alertDialog.setMessage("An updated version of this application is available now.")
                    alertDialog.setCancelable(false)
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Update",object :DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(snapshot.child("link").value.toString())
                            startActivity(i)
                        }
                    })
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancel",object :DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            alertDialog.dismiss()
                        }
                    })
                    alertDialog.show()
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}