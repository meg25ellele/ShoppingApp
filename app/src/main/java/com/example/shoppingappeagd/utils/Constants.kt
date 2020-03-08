package com.example.shoppingappeagd.utils

/**
 * Klasa ze stałymi używanymi w kodzie aplikacji.
 */
class Constants {
    companion object{
        /**
         * nazwa bazy danych
         */
        val DB_CHILD1_NAME = "products"
        /**
         * lista kategorii
         */
        val categories = listOf("lodówki","zmywarki")
        /**
         * nazwa tymczasowej bazy koszyka
         */
        val PAPER_CART_NAME="NSKJFNSEFNN"
        /**
         * maksymalna liczba elementów w koszyku
         */
        val MAX_BASKET_CAPACITY = 100
        /**
         * zmienna określa czy aplikacja już działa
         */
        var APP_ALREADY_OPEN = false
        /**
         * wiadomość o pustym koszyku
         */
        val PROMPT_EMPTY_BASKET = "Koszyk jest pusty!"
        /**
         * wiadomość dodania produktu do koszyka
         */
        val PROMPT_ADDED = "Dodano do koszyka."
        /**
         * błąd wyszukiwania
         */
        val PROMPT_NO_SEARCH_RESULT = "Brak produktów o podanej frazie!"
        /**
         * wiadomość o osiągnięciu maksymalnej liczbie elemetów w koszyku
         */
        val PROMPT_BASKET_FULL = "Nie można dodać kolejnego produktu, maksymalna liczba artykułów w koszyku wynosi $MAX_BASKET_CAPACITY!"
    }
}