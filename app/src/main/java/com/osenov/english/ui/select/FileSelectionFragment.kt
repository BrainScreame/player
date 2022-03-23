package com.osenov.english.ui.select

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.osenov.english.R
import com.osenov.english.databinding.FileSelectionFragmentBinding
import com.osenov.english.ui.select.list.FileAdapter
import com.osenov.english.ui.select.list.VideoClickListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.core.os.bundleOf
import com.osenov.english.appComponent
import com.osenov.english.ui.main.MainActivity
import com.osenov.english.ui.player.PlayerFragment
import javax.inject.Inject
import javax.inject.Provider

class FileSelectionFragment : Fragment() {

    companion object {
        private const val STORAGE_PERMISSION_CODE = 100
    }

    private val viewModel: FileSelectionViewModel by viewModels() {
        requireContext().appComponent.viewModelsFactory()
    }

    private lateinit var viewBinding: FileSelectionFragmentBinding

    private val adapter = FileAdapter(VideoClickListener {
        findNavController().navigate(
            R.id.action_fileSelection_to_playerFragment,
            bundleOf(PlayerFragment.videoModel to it)
        )
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FileSelectionFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        show()

        Toast.makeText(requireContext(), viewModel.abc, Toast.LENGTH_LONG).show()

    }

    fun show() {
        viewBinding.recyclerViewFiles.adapter = adapter
        lifecycleScope.launch {
            viewModel.flow.collectLatest(adapter::submitData)
        }
    }




}