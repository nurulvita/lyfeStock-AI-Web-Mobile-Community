package com.example.applyfestox.data.dummy

import com.example.applyfestox.R
import com.example.applyfestox.data.model.KandangData

object KandangDummyData {

    val aktifKandangList = listOf(
        KandangData(
            id = 1,
            name = "Kandang A",
            period = "Periode 2",
            location = "Kota Samarinda, Kalimantan Timur",
            duration = "111 Hari",
            chickenCount = "10",
            weight = "200 g",
            imageResId = R.drawable.bgform,
            feedingSchedule = "Pagi: 07:00, Siang: 12:00, Malam: 18:00"
        ),
        KandangData(
            id = 2,
            name = "Kandang B",
            period = "Periode 1",
            location = "Kota Samarinda, Kalimantan Timur",
            duration = "150 Hari",
            chickenCount = "20",
            weight = "250 g",
            imageResId = R.drawable.bgform,
            feedingSchedule = "Pagi: 08:00, Siang: 13:00, Malam: 19:00"
        )
    )

    val rehatKandangList = emptyList<KandangData>()

}