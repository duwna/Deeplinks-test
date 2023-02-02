package ru.touchin.deeplink.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.parcelize.Parcelize
import ru.touchin.deeplink.R
import ru.touchin.deeplink.databinding.FragmentWebviewBinding
import ru.touchin.deeplink.navigation.DEFAULT_FRAGMENT_ARGS_KEY
import ru.touchin.extensions.args
import ru.touchin.extensions.withParcelable
import ru.touchin.roboswag.navigation_base.fragments.viewBinding

class WebViewFragment : Fragment(R.layout.fragment_webview) {

    @Parcelize
    data class NavArgs(val url: String) : Parcelable

    companion object {
        fun newInstance(args: NavArgs) = WebViewFragment()
            .withParcelable(DEFAULT_FRAGMENT_ARGS_KEY, args)
    }

    private val args by args<NavArgs>(DEFAULT_FRAGMENT_ARGS_KEY)
    private val binding by viewBinding(FragmentWebviewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.loadUrl(args.url)
    }
}
