package dev.materii.gloom.domain.manager.enums

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.materii.gloom.Res

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

    // Stylized

    Blueprint(
        aliasName = "gloom.icons.stylized.Blueprint",
        iconName = Res.strings.app_icon_blueprint,
        iconDescription = Res.strings.app_icon_blueprint_description,
        preview = Res.images.gloom_icon_blueprint,
        collection = AppIconCollection.Stylized
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
    ),

    // Holiday 2025

    Valentines25(
        aliasName = "gloom.icons.holiday25.Valentines",
        iconName = Res.strings.app_icon_valentines25,
        iconDescription = Res.strings.app_icon_valentines25_description,
        preview = Res.images.gloom_icon_valentines25,
        collection = AppIconCollection.Holiday25
    )

}

enum class AppIconCollection(
    val nameRes: StringResource
) {

    Classic(Res.strings.app_icon_collection_classic),
    Stylized(Res.strings.app_icon_collection_stylized),
    Pride(Res.strings.app_icon_collection_pride),
    Catppuccin(Res.strings.app_icon_collection_catppuccin),
    Holiday25(Res.strings.app_icon_collection_holiday25)
}