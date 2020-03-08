package com.example.shoppingappeagd.controlers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.shoppingappeagd.R
import com.example.shoppingappeagd.models.BasketProduct
import com.example.shoppingappeagd.models.Product
import com.example.shoppingappeagd.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_product.*

/**
 * Klasa okna produktu.
 */
class ProductActivity : AppCompatActivity() {


    companion object {
        var prodId: String? = null
        var categ: String? = null
        var chosenProduct: Product? = null
    }

    /**
     * Metoda wywoływana, podczas tworzenia się aktywności. Ustawia layout i wywołuje metody.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        supportActionBar?.title = null

        prodId = intent.extras!!.getString(SingleCategoryActivity.EXTRA_CHOSEN_PROD_ID)
        categ = intent.extras!!.getString(SingleCategoryActivity.EXTRA_CHOSEN_PROD_CAT)
        loadChosenProduct()
    }

    /**
     * Metoda pobiera z bazy(FirebaseDatabase) wybrany produkt
     * i dopiero potem wywołuje metody ustawienia przycisku Dodania do koszyka i załadowania danych do layout,
     * ponieważ pobranie danych z bazy(FirebaseDatabase) działa asynchronicznie.
     */
    private fun loadChosenProduct() {
        FirebaseDatabase.getInstance().reference.child(Constants.DB_CHILD1_NAME).child(categ!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (child in p0.children) {
                        val product = child.getValue(Product::class.java)
                        if (prodId == product!!.id) {
                            chosenProduct = product
                            loadDataIntoView()
                            setListeners()
                        }
                    }
                }
            })
    }
    /**
     * Metoda wstawia zmienne(zdjęcie, nazwe, opis i cene produktu) do layoutu.
     */
    private fun loadDataIntoView() {
        Picasso.get().load(chosenProduct!!.photo).into(iv_image_product)
        tv_desc_prod.text = chosenProduct!!.description
        tv_price_prod.amount = chosenProduct!!.price
        tv_name_prod.text = chosenProduct!!.name
    }

    /**
     * Metoda zlicza ilość produktów w koszyku.
     */
    private fun getProductsCount(): Int {
        val list = Paper.book().read(Constants.PAPER_CART_NAME) as MutableList<BasketProduct>
        var count = 0
        for (prod in list) {
            count += prod.count
            Paper.book().write(Constants.PAPER_CART_NAME, list)
        }
        return count
    }

    /**
     * Metoda ustawia działanie przycisku DODAJ DO KOSZYKA, który dodaje produkt do koszyka.
     */
    private fun setListeners() {

        product_bt_add.setOnClickListener {
            if (getProductsCount() < Constants.MAX_BASKET_CAPACITY) {

                val list = Paper.book().read(Constants.PAPER_CART_NAME) as MutableList<BasketProduct>
                var index = 0
                var isProductInBasket = false
                for (prod in list) {
                    if (prod.product.id == chosenProduct!!.id) { //prod is in basket already
                        prod.count++
                        isProductInBasket = true
                    }
                    index++
                }
                Paper.book().write(Constants.PAPER_CART_NAME, list)

                if (!isProductInBasket) {
                    list.add(BasketProduct(chosenProduct!!, 1))
                    Paper.book().write(Constants.PAPER_CART_NAME, list)
                }

                Toast.makeText(this, Constants.PROMPT_ADDED, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, Constants.PROMPT_BASKET_FULL, Toast.LENGTH_LONG).show()
            }
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
                startActivity(Intent(this, CategoriesListActivity::class.java))
                return true
            }
            R.id.search -> {
                startActivity(Intent(this, SearchingActivity::class.java))
            }
            R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.basket_item -> {
                startActivity(Intent(this, ShoppingBasketActivity::class.java))
            }
        }
        return true
    }
}
