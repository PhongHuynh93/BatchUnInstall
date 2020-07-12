package com.wind.batchuninstall.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.wind.batchuninstall.GenericAdapter
import com.wind.batchuninstall.R
import com.wind.batchuninstall.databinding.FragmentUninstallAppBinding
import com.wind.batchuninstall.model.AppInfo
import com.wind.batchuninstall.util.RcvUtil
import com.wind.batchuninstall.viewmodel.UninstallAppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_uninstall_app.*

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
        // register start activity for result
        val uninstallAppRegisterForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Toast.makeText(requireContext(), "remove app successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "remove app fail", Toast.LENGTH_SHORT).show()
            }
            // update list
            viewModel.getInstalledApps()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val uninstallAppPermissionRegisterForResult = registerForActivityResult(ActivityResultContracts.RequestPermission()){}
            uninstallAppPermissionRegisterForResult.launch(Manifest.permission.REQUEST_DELETE_PACKAGES)
        }

        // handle rcv
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewDataBinding.rcv.apply {
            val space: Int = context.resources.getDimensionPixelOffset(R.dimen.space_small)
            addItemDecoration(RcvUtil.BaseItemDecoration(space))
            adapter = GenericAdapter<AppInfo>(R.layout.item_uninstall_app)
                .apply {
                    setOnListItemClickListener(object: GenericAdapter.OnListItemClickListener<AppInfo> {
                        override fun onClick(view: View, position: Int, item: AppInfo) {
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
                    })
                }
        }
    }
}