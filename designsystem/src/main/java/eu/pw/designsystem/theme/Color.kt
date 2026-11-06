package eu.pw.designsystem.theme

import android.content.Context
import androidx.compose.ui.graphics.Color
import eu.pw.designsystem.R

internal val primaryLight = R.color.design_primary_light
internal val onPrimaryLight = R.color.design_on_primary_light
internal val primaryContainerLight = R.color.design_primary_container_light
internal val onPrimaryContainerLight = R.color.design_on_primary_container_light
internal val secondaryLight = R.color.design_secondary_light
internal val onSecondaryLight = R.color.design_on_secondary_light
internal val secondaryContainerLight = R.color.design_secondary_container_light
internal val onSecondaryContainerLight = R.color.design_on_secondary_container_light
internal val tertiaryLight = R.color.design_tertiary_light
internal val onTertiaryLight = R.color.design_on_tertiary_light
internal val tertiaryContainerLight = R.color.design_tertiary_container_light
internal val onTertiaryContainerLight = R.color.design_on_tertiary_container_light
internal val errorLight = R.color.design_error_light
internal val onErrorLight = R.color.design_on_error_light
internal val errorContainerLight = R.color.design_error_container_light
internal val onErrorContainerLight = R.color.design_on_error_container_light
internal val backgroundLight = R.color.design_background_light
internal val onBackgroundLight = R.color.design_on_background_light
internal val surfaceLight = R.color.design_surface_light
internal val onSurfaceLight = R.color.design_on_surface_light
internal val surfaceVariantLight = R.color.design_surface_variant_light
internal val onSurfaceVariantLight = R.color.design_on_surface_variant_light
internal val outlineLight = R.color.design_outline_light
internal val outlineVariantLight = R.color.design_outline_variant_light
internal val scrimLight = R.color.design_scrim_light
internal val inverseSurfaceLight = R.color.design_inverse_surface_light
internal val inverseOnSurfaceLight = R.color.design_inverse_on_surface_light
internal val inversePrimaryLight = R.color.design_inverse_primary_light
internal val surfaceDimLight = R.color.design_surface_dim_light
internal val surfaceBrightLight = R.color.design_surface_bright_light
internal val surfaceContainerLowestLight = R.color.design_surface_container_lowest_light
internal val surfaceContainerLowLight = R.color.design_surface_container_low_light
internal val surfaceContainerLight = R.color.design_surface_container_light
internal val surfaceContainerHighLight = R.color.design_surface_container_high_light
internal val surfaceContainerHighestLight = R.color.design_surface_container_highest_light

internal val primaryDark = R.color.design_primary_dark
internal val onPrimaryDark = R.color.design_on_primary_dark
internal val primaryContainerDark = R.color.design_primary_container_dark
internal val onPrimaryContainerDark = R.color.design_on_primary_container_dark
internal val secondaryDark = R.color.design_secondary_dark
internal val onSecondaryDark = R.color.design_on_secondary_dark
internal val secondaryContainerDark = R.color.design_secondary_container_dark
internal val onSecondaryContainerDark = R.color.design_on_secondary_container_dark
internal val tertiaryDark = R.color.design_tertiary_dark
internal val onTertiaryDark = R.color.design_on_tertiary_dark
internal val tertiaryContainerDark = R.color.design_tertiary_container_dark
internal val onTertiaryContainerDark = R.color.design_on_tertiary_container_dark
internal val errorDark = R.color.design_error_dark
internal val onErrorDark = R.color.design_on_error_dark
internal val errorContainerDark = R.color.design_error_container_dark
internal val onErrorContainerDark = R.color.design_on_error_container_dark
internal val backgroundDark = R.color.design_background_dark
internal val onBackgroundDark = R.color.design_on_background_dark
internal val surfaceDark = R.color.design_surface_dark
internal val onSurfaceDark = R.color.design_on_surface_dark
internal val surfaceVariantDark = R.color.design_surface_variant_dark
internal val onSurfaceVariantDark = R.color.design_on_surface_variant_dark
internal val outlineDark = R.color.design_outline_dark
internal val outlineVariantDark = R.color.design_outline_variant_dark
internal val scrimDark = R.color.design_scrim_dark
internal val inverseSurfaceDark = R.color.design_inverse_surface_dark
internal val inverseOnSurfaceDark = R.color.design_inverse_on_surface_dark
internal val inversePrimaryDark = R.color.design_inverse_primary_dark
internal val surfaceDimDark = R.color.design_surface_dim_dark
internal val surfaceBrightDark = R.color.design_surface_bright_dark
internal val surfaceContainerLowestDark = R.color.design_surface_container_lowest_dark
internal val surfaceContainerLowDark = R.color.design_surface_container_low_dark
internal val surfaceContainerDark = R.color.design_surface_container_dark
internal val surfaceContainerHighDark = R.color.design_surface_container_high_dark
internal val surfaceContainerHighestDark = R.color.design_surface_container_highest_dark

internal fun Context.resolveColor(resourceId: Int): Color = Color(getColor(resourceId))