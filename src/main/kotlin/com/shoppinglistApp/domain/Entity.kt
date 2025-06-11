package com.shoppinglistApp.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name="user_name", nullable = false)
    var userName: String,       //kotolinはcamelCase
    @Column(nullable = false)
    var mail: String,
    @Column(name="root_shop")
    var rootShop: Long? = null //?はnullrable
)

@Entity
data class Shops(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name="shop_name",  nullable = false)
    var shopName: String,
)

@Entity
@Table(name = "shopping_items")
data class ShoppingItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column( nullable = false)
    var item: String,
    @Column( nullable = false)
    var buy: Boolean? = false,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id",  nullable=false)
    var shopId: Shops,
    @ManyToOne(fetch = FetchType.LAZY) // LAZYは必要になった時にロードする設定
    @JoinColumn(name = "user_id", nullable = false)
    val user: Users,
)