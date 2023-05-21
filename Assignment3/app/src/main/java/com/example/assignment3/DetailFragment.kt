package com.example.assignment3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Telephony
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment3.databinding.FragmentDetailBinding
import com.example.assignment3.databinding.FragmentListBinding
import java.net.URL

class DetailFragment : Fragment() {
    var binding: FragmentDetailBinding?=null
    val model: MyViewModel by activityViewModels()
    lateinit var item:MyContent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item=model.getData()
        binding!!.apply {
            Name.text=item.title
            Url.text=item.url
            Rank.text=item.rank.toString()

            urlBtn.setOnClickListener {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                startActivity(intent)
            }
            backBtn.setOnClickListener {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val listFragment = ListFragment()
                fragment.replace(R.id.frameLayout, listFragment)
                fragment.commit()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}