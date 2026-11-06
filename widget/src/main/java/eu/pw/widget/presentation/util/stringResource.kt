@file:Suppress("ktlint:standard:filename")

package eu.pw.widget.presentation.util

import androidx.compose.runtime.Composable
import androidx.glance.LocalContext

@Composable
internal fun stringResource(id: Int): String = LocalContext.current.getString(id)