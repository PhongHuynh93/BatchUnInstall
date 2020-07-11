package com.wind.batchuninstall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.wind.batchuninstall.databinding.FragmentUninstallAppBinding
import com.wind.batchuninstall.model.AppInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UninstallAppFragment : Fragment() {
    companion object {
        fun newInstance(): Fragment {
            return UninstallAppFragment()
        }
    }

    private lateinit var viewDataBinding: FragmentUninstallAppBinding
    private val viewModel by viewModels<UninstallAppViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentUninstallAppBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewDataBinding.rcv.adapter = GenericAdapter<AppInfo>(R.layout.item_uninstall_app).apply {
            setOnListItemClickListener(object: GenericAdapter.OnListItemClickListener {
                override fun onClick(view: View, position: Int) {

                }
            })
        }
    }
}