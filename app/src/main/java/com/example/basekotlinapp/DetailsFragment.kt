package com.example.basekotlinapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.basekotlinapp.databinding.FragmentDetailsBinding
import com.example.basekotlinapp.viewmodels.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment private constructor() : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding

    private var onAddOrUpdateClickListener: OnAddOrUpdateClickListener? = null

    private val modelItemId: String? by lazy {
        val id = arguments?.getString(ITEM_ID_KEY)
        Log.i(TAG, "lazyId: $id")
        id
    }

    private val detailViewModel: DetailViewModel by viewModel { parametersOf(modelItemId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAddOrUpdateClickListener = activity as? OnAddOrUpdateClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        initView()
    }

    private fun bindView() {
        binding?.viewModel = detailViewModel
        binding?.lifecycleOwner = requireActivity()
    }

    private fun initView() {
        detailViewModel.errorMessage.observe(viewLifecycleOwner, { msg ->
            if (msg != null) {
                showErrorSnackBar(msg)
            }
        })

        detailViewModel.buttonClickMarker.observe(viewLifecycleOwner, { clicked ->
            if (clicked) {
                onAddOrUpdateClickListener?.onAddOrUpdateClick()
            }
        })
    }

    private fun showErrorSnackBar(msg: String) {
        view?.let {
            Snackbar.make(it, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
            }.show()
        }
    }

    companion object {
        private const val ITEM_ID_KEY = "item_key"

        @JvmStatic
        fun newInstance(modelItemId: String?) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ITEM_ID_KEY, modelItemId)
                }
            }
    }

    interface OnAddOrUpdateClickListener {
        fun onAddOrUpdateClick()
    }
}