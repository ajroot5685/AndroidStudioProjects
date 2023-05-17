package com.example.gsbsuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.gsbsuser.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    var binding: FragmentRegisterBinding?=null
    val model: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply {
            registerBack.setOnClickListener {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val loginFragment = LoginFragment()
                fragment.replace(R.id.frameLayout, loginFragment)
                fragment.commit()
            }
            registerComplete.setOnClickListener {
                val i= Intent(activity, MainActivity::class.java)
                startActivity(i)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}