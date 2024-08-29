package com.kotlin_jetpack_compose.happyfashion.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kotlin_jetpack_compose.happyfashion.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val instagramFont = FontFamily(
    Font(R.font.instagram_bold, FontWeight.Bold),
    Font(R.font.instagram_regular, FontWeight.Normal)
)


val akrobatFont = FontFamily(
    Font(R.font.akrobat, FontWeight.Normal),
    Font(R.font.akrobat_bold, FontWeight.Bold)
)

val baskeFont = FontFamily(
    Font(R.font.baske, FontWeight.Normal),
    Font(R.font.baske_bold, FontWeight.Bold)
)

val baskvlFont = FontFamily(
    Font(R.font.baskvl, FontWeight.Normal),
    Font(R.font.baskvl_bold, FontWeight.Bold)
)

val borgenFont = FontFamily(
    Font(R.font.borgen, FontWeight.Normal),
    Font(R.font.borgen_bold, FontWeight.Bold)
)

val celestineFont = FontFamily(
    Font(R.font.celestine, FontWeight.Normal),
    Font(R.font.celestine_bold, FontWeight.Bold)
)

val didotFont = FontFamily(
    Font(R.font.didot, FontWeight.Normal),
    Font(R.font.didot_bold, FontWeight.Bold)
)

val georgiaFont = FontFamily(
    Font(R.font.georgia, FontWeight.Normal),
    Font(R.font.georgia_bold, FontWeight.Bold)
)

val latoFont = FontFamily(
    Font(R.font.lato, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Bold)
)

val lt_binary_neueFont = FontFamily(
    Font(R.font.lt_binary_neue, FontWeight.Normal),
    Font(R.font.lt_binary_neue_bold, FontWeight.Bold)
)

val maliFont = FontFamily(
    Font(R.font.mali, FontWeight.Normal),
    Font(R.font.mali_bold, FontWeight.Bold)
)

val merriweatherFont = FontFamily(
    Font(R.font.merriweather, FontWeight.Normal),
    Font(R.font.merriweather_bold, FontWeight.Bold)
)

val montserratFont = FontFamily(
    Font(R.font.montserrat, FontWeight.Normal),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

val opensansFont = FontFamily(
    Font(R.font.opensans, FontWeight.Normal),
    Font(R.font.opensans_bold, FontWeight.Bold)
)

val poppinsFont = FontFamily(
    Font(R.font.poppins, FontWeight.Normal),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

val ralewayFont = FontFamily(
    Font(R.font.raleway, FontWeight.Normal),
    Font(R.font.raleway_bold, FontWeight.Bold)
)

val robotoFont = FontFamily(
    Font(R.font.roboto, FontWeight.Normal),
    Font(R.font.roboto_bold, FontWeight.Bold)
)

val roboto_monoFont = FontFamily(
    Font(R.font.roboto_mono, FontWeight.Normal),
    Font(R.font.roboto_mono_bold, FontWeight.Bold)
)

val sf_scribbled_sansFont = FontFamily(
    Font(R.font.sf_scribbled_sans, FontWeight.Normal),
    Font(R.font.sf_scribbled_sans_bold, FontWeight.Bold)
)

val stashFont = FontFamily(
    Font(R.font.stash, FontWeight.Normal),
    Font(R.font.stash_bold, FontWeight.Bold)
)

val ubuntu_monoFont = FontFamily(
    Font(R.font.ubuntu_mono, FontWeight.Normal),
    Font(R.font.ubuntu_mono_bold, FontWeight.Bold)
)

val listFont = listOf(
    Pair("roboto", robotoFont),
    Pair("akrobat", akrobatFont),
    Pair("baske", baskeFont),
    Pair("baskvl", baskvlFont),
    Pair("borgen", borgenFont),
    Pair("celestine", celestineFont),
    Pair("didot", didotFont),
    Pair("georgia", georgiaFont),
    Pair("lato", latoFont),
    Pair("lt_binary_neue", lt_binary_neueFont),
    Pair("mali", maliFont),
    Pair("merriweather", merriweatherFont),
    Pair("montserrat", montserratFont),
    Pair("opensans", opensansFont),
    Pair("poppins", poppinsFont),
    Pair("raleway", ralewayFont),
    Pair("roboto_mono", roboto_monoFont),
    Pair("sf_scribbled_sans", sf_scribbled_sansFont),
    Pair("stash", stashFont),
    Pair("ubuntu_mono", ubuntu_monoFont)
)