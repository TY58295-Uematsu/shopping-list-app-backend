package com.shoppinglistApp.domain

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<Users, Long>
interface ShopRepository : CrudRepository<Shops, Long>
interface ShoppingItemRepository : CrudRepository<ShoppingItem, Long>{
    fun findAllByUser_Id(id: Long): List<ShoppingItem>
}