package com.example.gsbsuser.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gsbsuser.MainActivity
import com.example.gsbsuser.databinding.ActivityAccountBinding
import com.example.gsbsuser.login.Account
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AccountActivity : AppCompatActivity() {
    lateinit var binding:ActivityAccountBinding
    var auth: FirebaseAuth?= null
    lateinit var currentUser: FirebaseUser
    private lateinit var accountdb: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        initBtn()
        initAccount()
    }

    private fun initAccount() {
        currentUser=auth!!.currentUser!!
        accountdb = Firebase.database.getReference("Accounts")
        val uid = currentUser?.uid!!

        if(uid != null){
            val query = accountdb.child(uid)
            query.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val data = snapshot.getValue(Account::class.java)
                        binding.accountEmail.setText(data!!.pId)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AccountActivity, "DB 조회 중 에러 발생", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun initBtn() {
        binding.apply {
            registerBack.setOnClickListener {
                val i= Intent(this@AccountActivity, MainActivity::class.java)
                startActivity(i)
            }
            accountinfoBtn.setOnClickListener {
                val dialog = EditInfoFragment()
                dialog.setButtonClickListener(object: EditInfoFragment.OnButtonClickListener{
                    override fun onButtonClicked() {

                    }

                })
            }
        }
    }
}