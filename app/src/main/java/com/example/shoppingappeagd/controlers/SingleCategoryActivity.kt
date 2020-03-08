package com.example.shoppingappeagd.controlers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingappeagd.R
import com.example.shoppingappeagd.models.Product
import com.example.shoppingappeagd.models.ProductsAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_single_category.*


/**
 * Klasa okna z listą produktów wybranej kategorii.
 */
class SingleCategoryActivity : AppCompatActivity() {

    companion object{
        var categoryChosen: String? = null //wybrana kategoria w CategoriesListActivity
        const val EXTRA_CHOSEN_PROD_ID = "EXTRA,CHOSEN,ID,PROD"
        const val EXTRA_CHOSEN_PROD_CAT = "EXTRA,CHOSEN,ID,CAT"
    }

    /**
     * Lista z produktami z wybranej kategorii.
     */
    val productsList = mutableListOf<Product>()

    /**
     * Metoda wywoływana, podczas tworzenia się aktywności. Ustawia layout i wywołuje metody.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_category)


        val bundle = intent.extras
        categoryChosen = bundle!!.getString(CategoriesListActivity.EXTRA_CHOSEN_CATEGORY_NAME)

        supportActionBar!!.title = categoryChosen!!.toUpperCase()
        getProductsData()

    }

    /**
     * Metoda, która pobiera z bazy(FirebaseDatabase) produkty z wybranej kategorii i ładuje je.
     */
    private fun getProductsData(){
        var index = 0
        FirebaseDatabase.getInstance().reference.child("products").child(categoryChosen!!)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                    for(child in p0.children){
                        if(index!=p0.childrenCount.toInt()-1){
                            val product = child.getValue(Product::class.java)
                            productsList.add(product!!)
                            index++
                        }
                        else{
                            recyclerView_products.layoutManager = LinearLayoutManager(applicationContext)
                            recyclerView_products.adapter = ProductsAdapter(
                                productsList,
                                this@SingleCategoryActivity
                            )
                        }
                    }
                }

            })
    }





    /**
     * Metoda dołącza menu.xml do strony głównej aplikacji.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    /**
     * Metoda ustawia przyciski w menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.category -> {
                startActivity(Intent(this, CategoriesListActivity::class.java))
                return true
            }
            R.id.search ->{
                startActivity(Intent(this, SearchingActivity::class.java))
            }
            R.id.home ->{
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.basket_item ->{
                startActivity(Intent(this, ShoppingBasketActivity::class.java))
            }
        }
        return true
    }



}

