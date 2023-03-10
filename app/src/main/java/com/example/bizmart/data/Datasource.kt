package com.example.bizmart.data

import com.example.bizmart.R
import com.example.bizmart.category_data

class Datasource {

    fun loadText() : List<category_data>{

        return listOf<category_data>(
            category_data(R.string.item1,R.drawable.foodicon),
            category_data(R.string.item2,R.drawable.travelicon),
            category_data(R.string.item3,R.drawable.techicon),
            category_data(R.string.item4,R.drawable.agricutureicon),
            category_data(R.string.item5,R.drawable.photograghyicon),
            category_data(R.string.item6,R.drawable.houseicon),
            category_data(R.string.item7,R.drawable.bankicon),
            category_data(R.string.item8,R.drawable.agricutureicon),
            category_data(R.string.item9,R.drawable.travelicon),
            category_data(R.string.item10,R.drawable.bankicon)

            )
    }

}