package ru.touchin.deeplink.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.touchin.deeplink.R
import ru.touchin.deeplink.databinding.FragmentSearchDialogBinding
import ru.touchin.deeplink.databinding.ItemSearchSuggestBinding
import ru.touchin.deeplink.di.AppModule
import ru.touchin.deeplink.navigation.ExampleDeepLinkUrls
import ru.touchin.roboswag.navigation_base.fragments.viewBinding

class SearchDialogFragment : DialogFragment() {

    private val binding by viewBinding(FragmentSearchDialogBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_search_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ExampleDeepLinkUrls.forEach { suggest ->
            ItemSearchSuggestBinding
                .inflate(layoutInflater, binding.root, false)
                .root
                .apply {
                    binding.suggestsContainer.addView(this)
                    text = suggest
                    setOnClickListener {
                        binding.editText.setText(suggest)
                    }
                }
        }

        binding.navigateButton.setOnClickListener {
            AppModule.deepLinkHandler.sendDeeplink(binding.editText.text.toString())
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        requireDialog().window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
