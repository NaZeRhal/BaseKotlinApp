package com.example.basekotlinapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.basekotlinapp.databinding.FragmentListBinding
import com.example.basekotlinapp.presentation.adapter.ItemRecyclerAdapter
import com.example.basekotlinapp.presentation.adapter.bindAdapter
import com.example.basekotlinapp.presentation.viewmodels.ListViewModel
import com.example.basekotlinapp.utils.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class ListFragment : Fragment(), ItemRecyclerAdapter.OnItemClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    private val listViewModel: ListViewModel by viewModel()

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onItemClickListener = activity as? OnItemClickListener
    }

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
        binding?.apply {
            viewModel = listViewModel
            lifecycleOwner = requireActivity()
        }
    }

    private fun initView() {
        val itemAdapter = ItemRecyclerAdapter()
        itemAdapter.setOnItemClickListener(this)
        binding?.apply {
            rvList.bindAdapter(itemAdapter)
            fabAdd.setOnClickListener { onItemClickListener?.onAddNewItemClick() }
        }

        val swipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                itemAdapter.getItemAt(position).let {
                    listViewModel.delete(it)
                }
            }
        }

        ItemTouchHelper(swipeToDeleteCallback).apply {
            attachToRecyclerView(binding?.rvList)
        }


        listViewModel.items.observe(viewLifecycleOwner, { items ->
            items?.let { itemAdapter.setItems(it) }

        })

        listViewModel.errorMessage.observe(viewLifecycleOwner, { msg ->
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

    override fun onItemClick(modelItemId: String?) {
        onItemClickListener?.onViewDetailsClick(modelItemId)
    }

    companion object {
        const val TAG = "ListFragment"
    }

    interface OnItemClickListener {
        fun onAddNewItemClick()
        fun onViewDetailsClick(modelItemId: String?)
    }

}