package com.example.basekotlinapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.basekotlinapp.adapter.PostRecyclerAdapter
import com.example.basekotlinapp.adapter.bindAdapter
import com.example.basekotlinapp.data.DummyData
import com.example.basekotlinapp.databinding.FragmentListBinding
import com.example.basekotlinapp.model.Post
import com.example.basekotlinapp.viewmodels.ListViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class ListFragment : Fragment(), PostRecyclerAdapter.OnPostClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    private val viewModel: ListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        initView()
    }

    private fun bindView() {
        binding?.listViewModel = viewModel
    }

    private fun initView() {
        val postAdapter = PostRecyclerAdapter()
        postAdapter.setOnPostClickListener(this)
        binding?.rvList?.bindAdapter(postAdapter)

        viewModel.posts.observe(viewLifecycleOwner, { posts ->
            posts?.let { postAdapter.setPosts(it) }

        })

        viewModel.errorMessage.observe(viewLifecycleOwner, { msg ->
            if (msg != null) {
                showErrorMessage(msg)
            }
        })
    }

    private fun showErrorMessage(msg: String) {
        view?.let {
            Snackbar.make(it, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
            }.show()
        }
    }

    override fun onPostClick(post: Post) {
        parentFragmentManager.commit {
            val detailFragment = DetailsFragment.newInstance(post)
            setReorderingAllowed(true)
            replace(R.id.fcv_container, detailFragment)
            addToBackStack(null)
        }
    }

    companion object {
        const val TAG = "ListFragment"
    }
}