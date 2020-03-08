package com.example.shoppingappeagd.controlers

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingappeagd.R
import com.example.shoppingappeagd.models.BasketProduct
import com.example.shoppingappeagd.utils.Constants
import io.paperdb.Paper
/**
* Klasa okna głównego aplikacji.
*/
class MainActivity : AppCompatActivity() {

    /**
    * Metoda wywoływana, podczas tworzenia się aktywności. Ustawia layout i wywołuje metody.
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = null

        if(!Constants.APP_ALREADY_OPEN) {
            initializeCartBasket()
            Constants.APP_ALREADY_OPEN = true
        }
    }

    /**
    * Metoda, która tworzy nowy koszyk z produktami za każdym razem, gdy aplikacja jest uruchamiana od nowa.
    * Dane o produktach w koszyku nie są przechowywane, kiedy aplikacja zostaje wyłączona.
    */
    private fun initializeCartBasket() {
        val emptyList = mutableListOf<BasketProduct>()
        Paper.init(this)
        Paper.book().write(Constants.PAPER_CART_NAME, emptyList)
    }

    /**
    * Metoda dołącza menu.xml do strony głównej aplikacji.
    */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    /**
    * Metoda ustawia przyciski w menu.
    */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.category -> {
                startActivity(Intent(this, CategoriesListActivity::class.java))
                return true
            }
            R.id.search -> {
                startActivity(Intent(this, SearchingActivity::class.java))
            }
            R.id.basket_item -> {
                startActivity(Intent(this, ShoppingBasketActivity::class.java))
            }
        }
        return true
    }
}
