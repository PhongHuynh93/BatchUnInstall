package com.wind.batchuninstall.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.os.bundleOf
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wind.batchuninstall.R
import com.wind.batchuninstall.databinding.FragmentItemUninstallAppBinding
import com.wind.batchuninstall.databinding.FragmentUninstallAppBinding
import com.wind.batchuninstall.databinding.ItemUninstallAppBinding
import com.wind.batchuninstall.model.AppInfo
import com.wind.batchuninstall.util.RcvUtil
import com.wind.batchuninstall.viewmodel.UninstallAppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_uninstall_app.*
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@BindingAdapter(value = ["bind:pageItems", "bind:tab"])
fun setViewPagerData(viewPager: ViewPager2, data: List<AppInfo>?, tabLayout: TabLayout) {
    data?.let {
        (viewPager.adapter as UninstallAppPagerAdapter).let { adapter ->
            adapter.setData(it)
            if (adapter.titleInited.compareAndSet(false, true)) {
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = adapter.getTitle(position)
                }.attach()
            }
        }
    }
}

@AndroidEntryPoint
class UninstallAppFragment : Fragment() {
    companion object {
        fun newInstance(): Fragment {
            return UninstallAppFragment()
        }
    }

    private lateinit var viewDataBinding: FragmentUninstallAppBinding
    private val viewModel by activityViewModels<UninstallAppViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentUninstallAppBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.choose_app_uninstall)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val uninstallAppPermissionRegisterForResult = registerForActivityResult(ActivityResultContracts.RequestPermission()){}
            uninstallAppPermissionRegisterForResult.launch(Manifest.permission.REQUEST_DELETE_PACKAGES)
        }

        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewDataBinding.viewPager.adapter = UninstallAppPagerAdapter(this)
    }
}

private const val SYSTEM_APP_POS = 1
private const val NORMAL_APP_POS = 0
class UninstallAppPagerAdapter(frag: Fragment) : FragmentStateAdapter(frag) {
    private val fragManager = frag.childFragmentManager
    var titleInited = AtomicBoolean()
    private var mapData = mapOf<Int, List<AppInfo>>()

    // system apps and normal apps
    override fun getItemCount(): Int {
        return mapData.size
    }

    override fun createFragment(position: Int): Fragment {
        return UninstallItemFragment.newInstance(mapData[position])
    }

    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        val fragment: UninstallItemFragment? = fragManager.findFragmentByTag("f$position") as UninstallItemFragment?
        fragment?.update(mapData[position])
    }

    fun setData(data: List<AppInfo>) {
        val appBySystemMap = mutableMapOf<Int, MutableList<AppInfo>>()
        for (app in data) {
            val key = if (app.isSystemApp) {
                SYSTEM_APP_POS
            } else {
                NORMAL_APP_POS
            }
            if (appBySystemMap.containsKey(key)) {
                appBySystemMap[key]!!.add(app)
            } else {
                val listSystemApp = mutableListOf<AppInfo>()
                listSystemApp.add(app)
                appBySystemMap[key] = listSystemApp
            }
        }
        this.mapData = appBySystemMap
        notifyDataSetChanged()
    }

    fun getTitle(pos: Int) = when (pos) {
        NORMAL_APP_POS -> "Normal App"
        else -> "System App"
    }
}


private const val EXTRA_DATA = "xData"
@AndroidEntryPoint
class UninstallItemFragment(): Fragment() {
    private lateinit var viewDataBinding: FragmentItemUninstallAppBinding
    @Inject
    lateinit var uninstallAdapter: UninstallAppAdapter

    private val vmUninstallApp by activityViewModels<UninstallAppViewModel>()

    companion object {
        fun newInstance(data: List<AppInfo>?): Fragment {
            return UninstallItemFragment().apply {
                arguments = bundleOf(EXTRA_DATA to data)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentItemUninstallAppBinding.inflate(inflater, container, false).apply {
            val data = requireArguments().getParcelableArrayList<AppInfo>(EXTRA_DATA)
            setData(data)
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // register start activity for result
        val uninstallAppRegisterForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // TODO: 7/14/2020 scan again
                Toast.makeText(requireContext(), "remove app successful", Toast.LENGTH_SHORT).show()
                vmUninstallApp.scanApp()
            } else {
                Toast.makeText(requireContext(), "remove app fail", Toast.LENGTH_SHORT).show()
            }
        }
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewDataBinding.rcv.apply {
            val space: Int = context.resources.getDimensionPixelOffset(R.dimen.space_small)
            addItemDecoration(RcvUtil.BaseItemDecoration(space))
            adapter = uninstallAdapter
                .apply {
                    callback = object: UninstallAppAdapter.Callback {
                        override fun onClick(pos: Int, item: AppInfo) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && ContextCompat.checkSelfPermission(requireContext(), Manifest
                                    .permission.REQUEST_DELETE_PACKAGES) != PERMISSION_GRANTED) {
                                Toast.makeText(requireContext(), "Please allow delete package permission", Toast.LENGTH_SHORT).show()
                                return
                            }
                            MaterialDialog(requireContext()).show {
                                message(R.string.uninstall_message)
                                positiveButton(R.string.yes)
                                negativeButton(R.string.no)
                                positiveButton {
                                    val packageUri = Uri.parse("package:" + item.packageName)
                                    val uninstallIntent = Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri)
                                    uninstallIntent.putExtra(Intent.EXTRA_RETURN_RESULT, true)
                                    uninstallAppRegisterForResult.launch(uninstallIntent)
                                }
                            }
                        }

                    }
                }
        }
    }

    fun update(list: List<AppInfo>?) {
        Log.e("TAG", "update")
        list?.let {
            uninstallAdapter.setData(list)
        }
    }
}

class UninstallAppAdapter @Inject constructor(private val pk: PackageManager): RecyclerView.Adapter<ViewHolder>() {
    private var data: List<AppInfo> = emptyList()
    var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUninstallAppBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            packageManager = pk
        }).apply {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos >= 0) {
                    callback?.onClick(pos, data[pos])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = data[position]
        holder.binding.executePendingBindings()
    }

    fun setData(data: List<AppInfo>) {
        this.data = data
        notifyDataSetChanged()
    }

    interface Callback {
        fun onClick(pos: Int, appInfo: AppInfo)
    }
}

class ViewHolder(val binding: ItemUninstallAppBinding): RecyclerView.ViewHolder(binding.root) {

}
