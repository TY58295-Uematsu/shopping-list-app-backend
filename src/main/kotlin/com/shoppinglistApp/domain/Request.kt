package com.shoppinglistApp.domain

import com.sun.tools.javac.Main
import javax.print.attribute.standard.RequestingUserName

data class UserRequest(val userName: String, val mail: String, val rootShop: Long )
data class ItemRequest(val item: String, val buy: Boolean, val shopId: Long, val userId:Long )
