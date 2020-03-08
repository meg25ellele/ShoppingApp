package com.example.shoppingappeagd.controlers

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappeagd.R
import com.example.shoppingappeagd.controlers.CategoriesListActivity.Companion.EXTRA_CHOSEN_CATEGORY_NAME
import com.example.shoppingappeagd.utils.Constants
import kotlinx.android.synthetic.main.activity_categories_list.*
import kotlinx.android.synthetic.main.category_layout.view.*

/**
 * Klasa okna wyboru kategorii.
 */
class CategoriesListActivity : AppCompatActivity() {

    companion object {
        val categList = Constants.categories
        const val EXTRA_CHOSEN_CATEGORY_NAME = "CHOSEN_CAT"
    }

    /**
     * Metoda wywoływana, podczas tworzenia się aktywności. Ustawia layout i wywołuje metody.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories_list)
        supportActionBar?.title = null


        recyclerView_categories.layoutManager = LinearLayoutManager(this)
        recyclerView_categories.adapter = CategoriesAdapter(categList, this)
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

/**
 * Klasa adaptera dla RecyclerView w oknie wyboru kategorii.
 */
class CategoriesAdapter(val list: List<String>, val context: Context) : RecyclerView.Adapter<CategoriesVH>() {

    /**
     * Metoda ustawia layout dla adaptera(elementu w RecyclerView) -> layout okna z nazwa kategorii.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
        return CategoriesVH(view)
    }

    /**
     * Metoda zwraca ilosc elementów w RecyclerView.
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * Metoda ustawia elementy layoutu w adapterze dla kategorii i działanie po wybraniu jedenj z kategorii.
     */
    override fun onBindViewHolder(holder: CategoriesVH, position: Int) {
        holder.itemView.tv_category.text = list[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(context, SingleCategoryActivity::class.java)
            val bundle = Bundle()


            bundle.putString(EXTRA_CHOSEN_CATEGORY_NAME, list[position])
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}

/**
 * ViewHolder dla kategorii.
 */
class CategoriesVH(itemView: View) : RecyclerView.ViewHolder(itemView)