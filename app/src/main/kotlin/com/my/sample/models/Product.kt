package com.my.sample.models

import com.google.gson.annotations.SerializedName


data class Product (
    @SerializedName("id"                 ) var id                 : Int              = -1,
    @SerializedName("title"              ) var title              : String           = "",
    @SerializedName("description"        ) var description        : String           = "",
    @SerializedName("price"              ) var price              : Int              = -1,
    @SerializedName("discountPercentage" ) var discountPercentage : Double           = 0.0,
    @SerializedName("rating"             ) var rating             : Double           = 0.0,
    @SerializedName("stock"              ) var stock              : Int              = -1,
    @SerializedName("brand"              ) var brand              : String           = "",
    @SerializedName("category"           ) var category           : String           = "",
    @SerializedName("thumbnail"          ) var thumbnail          : String           = "",
    @SerializedName("images"             ) var images             : ArrayList<String> = arrayListOf()
){
    override fun equals(other: Any?): Boolean {
        return (other is Product) && (this.id == other.id)
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + price
        result = 31 * result + description.hashCode()
        result = 31 * result + images.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + discountPercentage.hashCode()
        result = 31 * result + rating.hashCode()
        result = 31 * result + stock
        result = 31 * result + brand.hashCode()
        result = 31 * result + thumbnail.hashCode()
        return result
    }
}
