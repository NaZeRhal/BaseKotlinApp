package com.example.basekotlinapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.basekotlinapp.databinding.FragmentDetailsBinding
import com.example.basekotlinapp.model.Post

class DetailsFragment private constructor() : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding

    private var post: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            post = it.getParcelable(POST_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding?.apply {
            tvIdDetails.text = post?.id
            tvTitleDetails.text = post?.title
            tvNameDetails.text = post?.name
        }

        return binding?.root
    }

    companion object {
        private const val POST_KEY = "post_key"

        @JvmStatic
        fun newInstance(post: Post) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(POST_KEY, post)
                }
            }
    }
}