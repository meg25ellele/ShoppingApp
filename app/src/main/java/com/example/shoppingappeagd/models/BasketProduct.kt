package com.example.shoppingappeagd.models

/**
 * Klasa produktu znajdującego się w koszyku.
 *
 * @param count: Ilość danego produktu w koszyku.
 *
 * @param product: Produkt w koszyku.
 */
class BasketProduct(val product: Product, var count: Int) {

     fun getAmount()  : Float{
        return this.product.price*this.count

    }
}