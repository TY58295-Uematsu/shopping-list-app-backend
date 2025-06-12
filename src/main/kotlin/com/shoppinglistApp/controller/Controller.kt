package com.shoppinglistApp.controller

import com.shoppinglistApp.domain.ItemRequest
import com.shoppinglistApp.domain.ItemUpdateRequest
import com.shoppinglistApp.domain.Shops
import com.shoppinglistApp.domain.ShopRepository
import com.shoppinglistApp.domain.ShoppingItem
import com.shoppinglistApp.domain.ShoppingItemRepository
import com.shoppinglistApp.domain.Users
import com.shoppinglistApp.domain.UserRepository
import com.shoppinglistApp.domain.UserRequest
import jakarta.persistence.Column
import org.apache.catalina.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin(origins = ["*"])
class Controller(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val shopRepository: ShopRepository,
    @Autowired private val shoppingItemRepository: ShoppingItemRepository,
) {
    @GetMapping("/users")
    fun getAllUser(): List<Users> {
        val user = userRepository.findAll()
        return user.toList()
    }

    @GetMapping("/users/{userId}")
    fun getUser(@PathVariable userId: Long): Users {
        var user: Users = Users(id = 0, userName = "", mail = "", rootShop = 0)
        try {
            user = userRepository.findById(userId).get()
        } catch (e: NoSuchElementException) {
        }
        return user
    }

    @PostMapping("/users")
    fun postUser(@RequestBody user: UserRequest) {
        try {
            val entity = Users(userName = user.userName, mail = user.mail, rootShop = user.rootShop)
            userRepository.save(entity)
        } catch (e: Exception) {
            println(e)
        }
    }

    @GetMapping("/shops")
    fun getShops(): List<Shops> {
        val lists = shopRepository.findAll()
        return lists.toList()
    }

    @GetMapping("/lists/all/{userId}")
    fun getLists(@PathVariable userId: Long): List<ShoppingItem> {
        val user = userRepository.findById(userId).get()
        var lists = listOf<ShoppingItem>(
            ShoppingItem(
                id = 0,
                item = "",
                buy = false,
                shopId = Shops(id = 1, shopName = "スーパー"),
                user = user
            )
        )
        try {
            lists = shoppingItemRepository.findAllByUser_Id(userId)
        } catch (e: Exception) {
        }
        return lists
    }

    @CrossOrigin(origins = ["*"])
    @PostMapping("/lists/items")
    fun postItem(@RequestBody item: ItemRequest) {
        try {
            val user = userRepository.findById(item.userId).get()
            val shop = shopRepository.findById(item.shopId).get()
            val entity = ShoppingItem(
                item = item.item, buy = item.buy, shopId = shop, user = user
            )
            println(entity)
            shoppingItemRepository.save(entity)
        } catch (e: Exception) {
            println(e)
        }
    }

    @PatchMapping("/lists/items/{id}")
    fun patchItem(@PathVariable id: Long, @RequestBody itemUpdateRequest: ItemUpdateRequest) {
        val existingItem = shoppingItemRepository.findById(id)
            .orElseThrow { NoSuchElementException("ShoppingItem with ID $id not found") }

        // ItemUpdateRequest の各フィールドが null でない場合のみ更新
        itemUpdateRequest.item?.let { existingItem.item = it } // item が提供されていれば更新
        itemUpdateRequest.buy?.let { existingItem.buy = it }   // buy が提供されていれば更新

        //save:idを持っていたら更新になる
        shoppingItemRepository.save(existingItem)
    }

    @DeleteMapping("/lists/items/{id}")
    fun deleteItem(@PathVariable id: Long) {
        shoppingItemRepository.deleteById(id)
    }


}
