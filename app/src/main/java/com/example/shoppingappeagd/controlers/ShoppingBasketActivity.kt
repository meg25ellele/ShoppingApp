package com.example.shoppingappeagd.controlers

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingappeagd.R
import com.example.shoppingappeagd.models.BasketProduct
import com.example.shoppingappeagd.utils.Constants
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_shopping_basket.*


/**
 * Klasa okna koszyka z produktami do zakupu.
 */
class ShoppingBasketActivity : AppCompatActivity() {


    /**
     * Metoda wywoływana, podczas tworzenia się aktywności. Ustawia layout i wywołuje metody.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_basket)
        supportActionBar?.title = null


        val list = Paper.book().read(Constants.PAPER_CART_NAME) as MutableList<BasketProduct>

        loadActualBasketPrice(list)

        setAdapter(list)

    }


    private fun setAdapter(basket_list :MutableList<BasketProduct>){
        recyclerView_basket.layoutManager = LinearLayoutManager(this)
        recyclerView_basket.adapter =
            ShoppingCartProductAdapter(basket_list, this, sum_money_tv)
    }
    /**
     * Metoda oblicza sumę cen produktów w koszyku i wstawia ją do layoutu.
     *
     * @param basket_list: Lista produktów w koszyku.
     */
    private fun loadActualBasketPrice(basket_list :MutableList<BasketProduct>) {
        var totalPrice = 0f
        for (prod in basket_list) {
            totalPrice += prod.product.price
        }
        sum_money_tv.amount = totalPrice

        if(totalPrice == 0f) {
            Toast.makeText(this, Constants.PROMPT_EMPTY_BASKET, Toast.LENGTH_LONG).show()
        }
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
                finish()
                startActivity(Intent(this, CategoriesListActivity::class.java))
                return true
            }
            R.id.search -> {
                finish()
                startActivity(Intent(this, SearchingActivity::class.java))
            }
            R.id.home -> {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        return true
    }
}
