package com.example.gsbsuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.gsbsuser.databinding.FragmentLoginBinding
import com.example.gsbsuser.databinding.FragmentRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment() {
    var binding: FragmentLoginBinding?=null
    val model: MyViewModel by activityViewModels()
    var auth: FirebaseAuth?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        // google 로그인
        //GoogleSignInClient 객체 초기화
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) //기본 로그인 방식 사용
//            .requestIdToken(getString(R.string.))
//            //requestIdToken :필수사항이다. 사용자의 식별값(token)을 사용하겠다.
//            //(App이 구글에게 요청)
//            .requestEmail()
//            // 사용자의 이메일을 사용하겠다.(App이 구글에게 요청)
//            .build()
//
//// 내 앱에서 구글의 계정을 가져다 쓸거니 알고 있어라!
//        googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding!!.apply {
            registerBtn.setOnClickListener {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val registerFragment = RegisterFragment()
                fragment.replace(R.id.frameLayout, registerFragment)
                fragment.commit()
            }
            loginBtn.setOnClickListener {
                signInUser()
            }
            googleBtn.setOnClickListener {

            }
        }
    }

    private fun signInUser() {
        auth?.signInWithEmailAndPassword(binding!!.loginEmail.text.toString(), binding!!.loginPassword.text.toString())
            ?.addOnCompleteListener {
                task ->
                if(task.isSuccessful){
                    // Creating a user account
                    moveMainPage(task.result?.user)
                }
                else{
                    // Show the error message
                    Toast.makeText(activity, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    // 로그인이 성공하면 다음 페이지로 넘어가는 함수
    private fun moveMainPage(user: FirebaseUser?) {
        // 파이어베이스 유저 상태가 있을 경우 다음 페이지로 넘어갈 수 있음
        if(user != null){
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}