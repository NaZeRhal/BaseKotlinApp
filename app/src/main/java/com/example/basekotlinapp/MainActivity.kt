package com.example.basekotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.basekotlinapp.databinding.ActivityMainBinding

const val TAG = "DBG"

class MainActivity : AppCompatActivity(), DetailsFragment.OnAddOrUpdateClickListener,
    ListFragment.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listFragment =
            supportFragmentManager.findFragmentByTag(ListFragment.TAG) ?: ListFragment()

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fcv_container, listFragment)
            }
        }
    }

    override fun onAddOrUpdateClick() {
        supportFragmentManager.commit {
            val listFragment =
                supportFragmentManager.findFragmentByTag(ListFragment.TAG) ?: ListFragment()
            setReorderingAllowed(true)
            replace(R.id.fcv_container, listFragment)
        }
    }

    override fun onAddNewItemClick() {
        supportFragmentManager.commit {
            val detailFragment = DetailsFragment.newInstance(null)
            setReorderingAllowed(true)
            replace(R.id.fcv_container, detailFragment)
            addToBackStack(null)
        }
    }

    override fun onViewDetailsClick(modelItemId: String?) {
        supportFragmentManager.commit {
            val detailFragment = DetailsFragment.newInstance(modelItemId)
            setReorderingAllowed(true)
            replace(R.id.fcv_container, detailFragment)
            addToBackStack(null)
        }
    }
}