package com.example.applyfestox.uii.screen.manajemen.component

import java.text.DecimalFormat

private const val MONEY_FORMAT = "###,###,##0.00"
private const val MONEY_FORMAT_NO_CENTS = "###,###,###"

fun Float.toMoneyFormat(
    removeTrailingZeroes: Boolean = false,
): String {
    // Choose the format based on whether to remove trailing zeroes
    val format = if (removeTrailingZeroes && (this % 1 == 0.0f)) DecimalFormat(MONEY_FORMAT_NO_CENTS)
    else DecimalFormat(MONEY_FORMAT)

    // Format the amount and prepend "Rp" symbol for Rupiah
    return "Rp ${format.format(this)}"
}