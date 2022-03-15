package com.osenov.english.ui.select

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.osenov.english.ui.player.PlayerFragment

class FileSelectionFragment : Fragment() {

    private val adapter = FileAdapter(VideoClickListener {
        findNavController().navigate(
            R.id.action_fileSelection_to_playerFragment,
            bundleOf(PlayerFragment.videoModel to it)
        )
    })

    private val viewModel: FileSelectionViewModel by viewModels()

    private lateinit var viewBinding: FileSelectionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FileSelectionFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewBinding.recyclerViewFiles.adapter = adapter
        lifecycleScope.launch {
            viewModel.flow.collectLatest(adapter::submitData)
        }
    }


}