package com.picpay.desafio.android.factory

import com.picpay.desafio.android.domain.model.User

object UserFactory {
    val users = listOf(
        User(
            idKey = 0,
            id = 1,
            name = "Sandrine Spinka",
            img = "https://randomuser.me/api/portraits/men/1.jpg",
            username = "Tod86"
        ),
        User(
            idKey = 1,
            id = 2,
            name = "Carli Carroll",
            img = "https://randomuser.me/api/portraits/men/2.jpg",
            username = "Constantin_Sawayn"
        ),
        User(
            idKey = 2,
            id = 3,
            name = "Annabelle Reilly",
            img = "https://randomuser.me/api/portraits/men/3.jpg",
            username = "Lawrence_Nader62"
        ),
        User(
            idKey = 3,
            id = 4,
            name = "Mrs. Hilton Welch",
            img = "https://randomuser.me/api/portraits/men/4.jpg",
            username = "Tatyana_Ullrich"
        ),
    )
}