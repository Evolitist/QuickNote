package com.evolitist.quicknote.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/** Returns a new [PaddingValues] with the sum of this and [other] */
@Stable
operator fun PaddingValues.plus(other: PaddingValues): PaddingValues = AddedPaddingValues(this, other)

/**
 * Returns a new [PaddingValues] with the difference of this and [other], coercing negative
 * values to `0.dp`.
 */
@Stable
operator fun PaddingValues.minus(other: PaddingValues): PaddingValues = SubtractedPaddingValues(this, other)

/**
 * A [PaddingValues] that is the sum of [first] and [second] as returned after [PaddingValues.plus].
 */
@Immutable
private class AddedPaddingValues(
    val first: PaddingValues,
    val second: PaddingValues,
) : PaddingValues {

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp {
        return first.calculateLeftPadding(layoutDirection) + second.calculateLeftPadding(layoutDirection)
    }

    override fun calculateTopPadding(): Dp {
        return first.calculateTopPadding() + second.calculateTopPadding()
    }

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp {
        return first.calculateRightPadding(layoutDirection) + second.calculateRightPadding(layoutDirection)
    }

    override fun calculateBottomPadding(): Dp {
        return first.calculateBottomPadding() + second.calculateBottomPadding()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is AddedPaddingValues) return false
        return first == other.first && second == other.second
    }

    override fun hashCode() = first.hashCode() * 31 + second.hashCode()

    override fun toString() = "($first + $second)"
}

/**
 * A [PaddingValues] that is the difference of [first] and [second] as returned after
 * [PaddingValues.minus]. Paddings will be coerced to 0 if the result is negative.
 */
@Immutable
private class SubtractedPaddingValues(
    val first: PaddingValues,
    val second: PaddingValues,
) : PaddingValues {

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp {
        return (first.calculateLeftPadding(layoutDirection) - second.calculateLeftPadding(layoutDirection))
            .coerceAtLeast(0.dp)
    }

    override fun calculateTopPadding(): Dp {
        return (first.calculateTopPadding() - second.calculateTopPadding()).coerceAtLeast(0.dp)
    }

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp {
        return (first.calculateRightPadding(layoutDirection) - second.calculateRightPadding(layoutDirection))
            .coerceAtLeast(0.dp)
    }

    override fun calculateBottomPadding(): Dp {
        return (first.calculateBottomPadding() - second.calculateBottomPadding()).coerceAtLeast(0.dp)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is SubtractedPaddingValues) return false
        return first == other.first && second == other.second
    }

    override fun hashCode() = first.hashCode() * 31 + second.hashCode()

    override fun toString() = "($first - $second)"
}
