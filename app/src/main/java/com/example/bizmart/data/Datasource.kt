package com.example.bizmart.data

import com.example.bizmart.R
import com.example.bizmart.category_data

class Datasource {

    fun loadText() : List<category_data>{

        return listOf<category_data>(
            category_data(R.string.item1),
            category_data(R.string.item2),
            category_data(R.string.item3),
            category_data(R.string.item4),
            category_data(R.string.item5),
            category_data(R.string.item6),
            category_data(R.string.item7),
            category_data(R.string.item8),
            category_data(R.string.item9),
            category_data(R.string.item10)

            )
    }

}