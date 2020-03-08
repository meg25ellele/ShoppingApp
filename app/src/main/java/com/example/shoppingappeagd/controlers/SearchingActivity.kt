package com.example.shoppingappeagd.controlers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingappeagd.R
import com.example.shoppingappeagd.models.Product
import com.example.shoppingappeagd.models.ProductsAdapter
import com.example.shoppingappeagd.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_searching.*
/**
* Klasa okna wyszukiwania produktów.
*
 */

class SearchingActivity : AppCompatActivity() {


    companion object{
        /**
         * Metoda sprawdza czy użytkownik wpisał coś w wyszukiwarke
         */
        fun checkIfInputNotEmpty(input :String) : Boolean {
            if(input.trim()!="") return true
            return false
        }

        /**
         * Metoda zwraca listę produktów, które zawierają daną frazę
         */
        fun getMatchingProducts(products : MutableList<Product>, input: String) :MutableList<Product> {

            val input_list = input.split(" ")
            val matchingList = mutableListOf<Product>()

            for(product in products) {
                if(product.containsWords(input_list)) matchingList.add(product)
            }

            return matchingList
        }
    }
    /**
     * Lista produtów, które pasują do wyszukiwanej frazy.
     */
    val matchingProductsList = mutableListOf<Product>()

    /**
     * Metoda wywoływana, podczas tworzenia się aktywności. Ustawia layout i wywołuje metody.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searching)

        supportActionBar?.
            title = null

        getMatchingProducts(mutableListOf(),"")

        bt_search.setOnClickListener {
            performSearch()
        }
    }

    /**
     * Metoda wywoływana podczas kliknięcia przycisku SZUKAJ.
     * Wyszukuje, czy w bazie (FirebaseDatabase) znajduja się produkty spełniające daną frazę.
     */
    private fun performSearch(){
        matchingProductsList.clear()
        val input_edit_text = et_input.text.toString()


        if(checkIfInputNotEmpty(input_edit_text)) {

            val input = input_edit_text.split(" ")
            var actualCount = 1

            for (categ in Constants.categories) {
                FirebaseDatabase.getInstance().reference.child(Constants.DB_CHILD1_NAME).child(categ)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            for (child in p0.children) {
                                val newProduct = child.getValue(Product::class.java)
                                if (newProduct!!.containsWords(input)) {
                                    matchingProductsList.add(newProduct)
                                }
                                if (actualCount == p0.childrenCount.toInt()) {
                                    setAdapterAndManager()
                                }
                                actualCount++
                            }
                        }

                    })
            }
        }
    }
    /**
     * Metoda, która ładuje znalezione produkty lub wyświetla informacje o braku produktów.
     */
    private fun setAdapterAndManager(){

        if(matchingProductsList.isEmpty()) {
            Toast.makeText(this, Constants.PROMPT_NO_SEARCH_RESULT, Toast.LENGTH_LONG).show()

        }
         else {
            recyclerView_search.layoutManager = LinearLayoutManager(this)
            recyclerView_search.adapter = ProductsAdapter(matchingProductsList, this)
        }
    }

    /**
     * Metoda dołącza menu.xml do strony głównej aplikacji.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    /**
     *
     * Metoda ustawia przyciski w menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.category -> {
                startActivity(Intent(this, CategoriesListActivity::class.java))
                return true
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
