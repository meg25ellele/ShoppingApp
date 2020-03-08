package com.example.shoppingappeagd

import com.example.shoppingappeagd.controlers.SearchingActivity
import com.example.shoppingappeagd.models.BasketProduct
import com.example.shoppingappeagd.models.Product
import com.example.shoppingappeagd.controlers.ShoppingCartProductAdapter
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    //Test sprawdzający czy można dodać więcej niż 100 produktów do koszyka
    //ShoppingCartProductAdapter.checkFullCondition() + ShoppingCartProductAdapter.getProductsCount()
    @Test
    fun addToManyElementsToBasket() {
       val test_list  = mutableListOf<BasketProduct>()
        test_list.add(BasketProduct(Product("","","","","","",0f),101))
        assertEquals(true, ShoppingCartProductAdapter.checkFullCondition(ShoppingCartProductAdapter.getProductsCount(test_list)))
    }

    //Test sprawdzający, czy jak zmniejszymy ilość elementu w koszyku do 0, to się usunie z listy
    //ShoppingCartProductAdapter.decrementBasket
    @Test
    fun deleteLastElementFromBasket(){
        val test_list = mutableListOf<BasketProduct>()
        test_list.add(BasketProduct(Product("","","","","","",0f),1))
        test_list.add(BasketProduct(Product("","","","","","",0f),3))

        val countBefore = ShoppingCartProductAdapter.getProductsCount(test_list)
        val test_new_list = ShoppingCartProductAdapter.decrementBasket(test_list,0)

        assertEquals(countBefore-1, ShoppingCartProductAdapter.getProductsCount(test_new_list))
    }

    //Test sprawdza czy jak użytkownik wpisze pusta frazę to wyszukiwanie się nie zacznie nawet
    //SearchingActivity.checkIfInputNotEmpty
    @Test
    fun checkIfInputEmpty() {
       val empty = "        "
        assertEquals(false, SearchingActivity.checkIfInputNotEmpty(empty))
    }

    //Test sprawdza, że wyszukiwany produkt nie musi mieć wszytskich fraz wyszukiwanych
    //Product.containsWords
    @Test
    fun findMatchingButNotAll() {
        val test_list = mutableListOf<Product>()
        test_list.add(Product("fridge","lodówka", "1","oto lodówka", "lodówka Elextrolux","",0f))
        test_list.add(Product("dishwasher","zmywarka", "2","oto zmywarka", "zmywarka Elextrolux","",0f))
        test_list.add(Product("vacuum","odkurzacz", "3","oto odkurzacz", "odkurzacz Elextrolux","",0f))

        assertEquals(3,SearchingActivity.getMatchingProducts(test_list,"Elextro lodówka").size)

    }


}