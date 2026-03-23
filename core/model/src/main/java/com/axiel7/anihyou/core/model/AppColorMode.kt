package com.axiel7.anihyou.core.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.axiel7.anihyou.core.model.base.Localizable
import com.axiel7.anihyou.core.resources.R

enum class AppColorMode : Localizable {
    DEFAULT,
    PROFILE,
    CUSTOM,
    CATPPUCCIN_MOCHA;

    @Composable
    override fun localized() = stringResource(stringRes)

    val stringRes
        get() = when (this) {
            DEFAULT -> R.string.default_setting
            PROFILE -> R.string.profile
            CUSTOM -> R.string.custom_color
            CATPPUCCIN_MOCHA -> R.string.catppuccin_mocha
        }

    companion object {
        val entriesLocalized = entries.associateWith { it.stringRes }
    }
}