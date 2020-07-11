package com.wind.batchuninstall.view

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.wind.batchuninstall.GenericAdapter
import com.wind.batchuninstall.R
import com.wind.batchuninstall.databinding.FragmentUninstallAppBinding
import com.wind.batchuninstall.model.AppInfo
import com.wind.batchuninstall.util.RcvUtil
import com.wind.batchuninstall.viewmodel.UninstallAppViewModel
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
        viewDataBinding.rcv.apply {
            val spaceSmall: Int = context.resources.getDimensionPixelOffset(R.dimen.space_small)
            addItemDecoration(RcvUtil.BaseItemDecoration(spaceSmall))
            adapter = GenericAdapter<AppInfo>(R.layout.item_uninstall_app)
                .apply {
                    setOnListItemClickListener(object: GenericAdapter.OnListItemClickListener {
                        override fun onClick(view: View, position: Int) {

                        }
                    })
                }
        }
    }
}