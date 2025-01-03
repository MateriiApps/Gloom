package com.materiiapps.gloom.ui.util

import com.materiiapps.gloom.Res
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

expect class AppIconSetter {

    var currentIcon: AppIcon

    fun setIcon(appIcon: AppIcon)

}

enum class AppIcon(
    val aliasName: String,
    val iconName: StringResource,
    val iconDescription: StringResource,
    val preview: ImageResource,
    val collection: AppIconCollection
) {

    // Classic

    Main(
        aliasName = "gloom.icons.classic.Main",
        iconName = Res.strings.app_icon_main,
        iconDescription = Res.strings.app_icon_main_description,
        preview = Res.images.gloom_icon_default,
        collection = AppIconCollection.Classic
    ),

    Sky(
        aliasName = "gloom.icons.classic.Sky",
        iconName = Res.strings.app_icon_sky,
        iconDescription = Res.strings.app_icon_sky_description,
        preview = Res.images.gloom_icon_sky,
        collection = AppIconCollection.Classic
    ),

    Light(
        aliasName = "gloom.icons.classic.Light",
        iconName = Res.strings.app_icon_light,
        iconDescription = Res.strings.app_icon_light_description,
        preview = Res.images.gloom_icon_light,
        collection = AppIconCollection.Classic
    ),

    // Pride

    Pride(
        aliasName = "gloom.icons.pride.LGBT",
        iconName = Res.strings.app_icon_pride,
        iconDescription = Res.strings.app_icon_pride_description,
        preview = Res.images.gloom_icon_pride,
        collection = AppIconCollection.Pride
    ),

    Trans(
        aliasName = "gloom.icons.pride.Trans",
        iconName = Res.strings.app_icon_trans,
        iconDescription = Res.strings.app_icon_trans_description,
        preview = Res.images.gloom_icon_trans,
        collection = AppIconCollection.Pride
    ),

    TransInverted(
        aliasName = "gloom.icons.pride.TransInverted",
        iconName = Res.strings.app_icon_trans_inverted,
        iconDescription = Res.strings.app_icon_trans_inverted_description,
        preview = Res.images.gloom_icon_trans_inverted,
        collection = AppIconCollection.Pride
    ),

    // Catppuccin

    Mocha(
        aliasName = "gloom.icons.catppuccin.Mocha",
        iconName = Res.strings.app_icon_mocha,
        iconDescription = Res.strings.app_icon_mocha_description,
        preview = Res.images.gloom_icon_mocha,
        collection = AppIconCollection.Catppuccin
    ),

    Macchiato(
        aliasName = "gloom.icons.catppuccin.Macchiato",
        iconName = Res.strings.app_icon_macchiato,
        iconDescription = Res.strings.app_icon_macchiato_description,
        preview = Res.images.gloom_icon_macchiato,
        collection = AppIconCollection.Catppuccin
    ),

    Frappe(
        aliasName = "gloom.icons.catppuccin.Frappe",
        iconName = Res.strings.app_icon_frappe,
        iconDescription = Res.strings.app_icon_frappe_description,
        preview = Res.images.gloom_icon_frappe,
        collection = AppIconCollection.Catppuccin
    ),

    Latte(
        aliasName = "gloom.icons.catppuccin.Latte",
        iconName = Res.strings.app_icon_latte,
        iconDescription = Res.strings.app_icon_latte_description,
        preview = Res.images.gloom_icon_latte,
        collection = AppIconCollection.Catppuccin
    )

}

enum class AppIconCollection(
    val nameRes: StringResource
) {
    Classic(Res.strings.app_icon_collection_classic),
    Pride(Res.strings.app_icon_collection_pride),
    Catppuccin(Res.strings.app_icon_collection_catppuccin)
}