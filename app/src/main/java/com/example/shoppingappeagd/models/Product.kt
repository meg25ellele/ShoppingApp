package com.example.shoppingappeagd.models

/**
 * Klasa produktu.
 *
 * @param category : Nazwa kategorii w języku angielski.
 * @param categoryPL : Nazwa kategorii w języku polskim.
 * @param id : Id produktu.
 * @param description : Opis produktu.
 * @param name : Nazwa produktu.
 * @param photo : Adres url do zdjęcia.
 * @param price : Cena produktu.
 */
class Product(
    val category: String, val categoryPL: String, val id: String, val description: String,
    val name: String, val photo: String, val price: Float
) {
    constructor() : this("", "", "", "", "", "", 0f)

    /**
     * Metoda używana podczas wyszukiwania produktów. Sprawdza, czy w danych o produkcie znajduje się szukana fraza.
     */
    fun containsWords(list: List<String>): Boolean {
        for (word in list) {
            if (categoryPL.toUpperCase().contains(word.toUpperCase()) || description.toUpperCase().contains(word.toUpperCase())
                || name.toUpperCase().contains(word.toUpperCase())) {
                return true
            }
        }
        return false
    }


}