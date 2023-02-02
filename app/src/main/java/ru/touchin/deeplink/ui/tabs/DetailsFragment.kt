package ru.touchin.deeplink.ui.tabs

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.parcelize.Parcelize
import ru.touchin.deeplink.R
import ru.touchin.deeplink.databinding.FragmentDetailsBinding
import ru.touchin.deeplink.navigation.DEFAULT_FRAGMENT_ARGS_KEY
import ru.touchin.deeplink.navigation.TabType
import ru.touchin.extensions.args
import ru.touchin.extensions.withParcelable
import ru.touchin.roboswag.navigation_base.fragments.viewBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    @Parcelize
    data class NavArgs(val tabType: TabType, val index: Int) : Parcelable

    companion object {
        fun newInstance(args: NavArgs) = DetailsFragment()
            .withParcelable(DEFAULT_FRAGMENT_ARGS_KEY, args)
    }

    private val args by args<NavArgs>(DEFAULT_FRAGMENT_ARGS_KEY)
    private val binding by viewBinding(FragmentDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailsText.text = getString(
            R.string.details_template,
            getString(args.tabType.nameId),
            args.index
        )
    }
}
