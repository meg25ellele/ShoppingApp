package com.example.shoppingappeagd.models

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappeagd.controlers.ProductActivity
import com.example.shoppingappeagd.R
import com.example.shoppingappeagd.controlers.SingleCategoryActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_layout.view.*

/**
 * Klasa adaptera produktu dla RecyclerView wyszukiwania produktów po kategorii i wpisanej frazie.
 *
 * @param list: Lista produktów.
 */
class ProductsAdapter(val list: List<Product>, val context: Context): RecyclerView.Adapter<ProductViewHolder>(){

    /**
     * Metoda ustawia layout dla adaptera(elementu w RecyclerView) -> layout okna z nazwa, cena i zdjeciem produktu.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_layout, parent, false)
        return ProductViewHolder(view)
    }
    /**
     * Metoda zwraca ilosc elementów w RecyclerView.
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * Metoda ustawia elementy layoutu w adapterze dla produktu i działanie po wybraniu jedenj z pruduktu.
     */
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val product = list[position]
        val view = holder.itemView
        view.tv_name_product.text = list[position].name
        view.tv_price_product.text = list[position].price.toString()
        Picasso.get().load(list[position].photo).into(view.iv_photo_product)

        view.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra(SingleCategoryActivity.EXTRA_CHOSEN_PROD_CAT, product.categoryPL)
            intent.putExtra(SingleCategoryActivity.EXTRA_CHOSEN_PROD_ID, product.id)
            context.startActivity(intent)
        }
    }
}
/**
 * ViewHolder dla produktu.
 */
class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
