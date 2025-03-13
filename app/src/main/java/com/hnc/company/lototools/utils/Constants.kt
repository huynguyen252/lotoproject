package com.hnc.company.lototools.utils

const val MAX_SIZE_QUANTITY_PRODUCT = 50
const val MAX_SIZE_PRODUCT = 150
const val MAX_WEIGHT_PRODUCT = 20
const val KEY_SELECTED_PRODUCT_RESULT = "KEY_SELECTED_PRODUCT_RESULT"

enum class ValueSelectionType(val title: String){
    QUANTITY("Chọn số lượng"),
    WEIGHT("Chọn khối lượng"),
    PACKAGE("Chọn số kiện"),
}
