package com.example.shoppingappeagd.controlers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappeagd.R
import com.example.shoppingappeagd.models.BasketProduct
import com.example.shoppingappeagd.utils.Constants
import com.squareup.picasso.Picasso
import io.paperdb.Paper
import kotlinx.android.synthetic.main.card_product_row.view.*
import org.fabiomsr.moneytextview.MoneyTextView


/**
 * Klasa adaptera elementu koszyka dla RecyclerView koszyka.
 *
 * @param list: Lista produktów w koszyku.
 */
class ShoppingCartProductAdapter(
    val list: MutableList<BasketProduct>,
    val context: Context,
    val tvTotalPrice: MoneyTextView
) :
    RecyclerView.Adapter<CartProductVH>() {


    companion object {
        /**
         * Metoda zlicza ilość produktów w koszyku.
         */
         fun getProductsCount(list : MutableList<BasketProduct>): Int {
            var count = 0
            for (prod in list) {
                count += prod.count

            }
            return count
        }

        /**
         * Metoda sprawdza, czy można koszyk jest pełny.
         */
          fun checkFullCondition(sum : Int) : Boolean {
            if(sum >=Constants.MAX_BASKET_CAPACITY) return true

            return false

        }

        /**
         * Metoda zmniejsza ilość wybranego elementu w koszyku.
         */
        fun decrementBasket (list : MutableList<BasketProduct>, pos:Int) : MutableList<BasketProduct> {

            val product = list[pos]


            if(product.count>1){
                product.count--
            } else
            {
                list.removeAt(pos)
            }

            return list
        }
    }
    /**
     * Metoda ustawia layout dla adaptera(elementu w RecyclerView) -> layout okna z prduktem w koszyku.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_product_row, parent, false)
        return CartProductVH(view)
    }

    /**
     * Metoda zwraca ilosc elementów w RecyclerView.
     */
    override fun getItemCount(): Int {
        return list.size
    }


    /**
     * Metoda ustawia elementy layoutu w adapterze dla produktu w koszyku i wysołuje metody ustawiające działanie przycisków.
     */
    override fun onBindViewHolder(holder: CartProductVH, position: Int) {
        val view = holder.itemView
        val item = list[position]
        Picasso.get().load(item.product.photo).into(view.cart_image)
        view.cart_name.text = item.product.name
        view.cart_prod_count.text = item.count.toString()
        view.cart_money.amount = item.product.price * item.count

        setIncrementing(view.bt_add_basket, position, view.cart_money)
        setDecrementing(view.bt_minus_basket, position, view.cart_money)
        setDeleting(view.bt_bin_basket, position, tvTotalPrice)

    }

    /**
     * Metoda oblicza całkowitą wartość koszyka i ustawia ją w layoucie.
     */
    private fun updateTotalPrice() {
        val listPaper = Paper.book().read(Constants.PAPER_CART_NAME) as MutableList<BasketProduct>
        var totalCost = 0f
        for (prod in listPaper) {
            totalCost += prod.getAmount()
        }

        if(totalCost == 0f) {
            Toast.makeText(context, Constants.PROMPT_EMPTY_BASKET, Toast.LENGTH_LONG).show()
        }

        tvTotalPrice.amount = totalCost
    }



    /**
     * Metoda wykonywana podczas zwiększenia ilości danego produktu w koszyku.
     *
     * @param bt_add : Przycisk +.
     * @param pos : Pozycja produktu w RecyclerView, który ma zwiększyć swoją ilość.
     * @param productValue : TextView wyświetlający sumę do zapłaty za wybrany produkt.
     */
    private fun setIncrementing(bt_add: Button, pos: Int, productValue: MoneyTextView) {

        bt_add.setOnClickListener {
            if (!checkFullCondition(
                    getProductsCount(
                        list
                    )
                )
            ) {
                val product = list[pos]
                product.count++
                Paper.book().write(Constants.PAPER_CART_NAME, list)
                productValue.amount = list[pos].getAmount()
                notifyDataSetChanged()
            } else {
                Toast.makeText(context, Constants.PROMPT_BASKET_FULL, Toast.LENGTH_LONG).show()
            }
            updateTotalPrice()
        }
    }



    /**
     * Metoda wykonywana podczas zmniejszania ilości danego produktu w koszyku.
     *
     * @param bt_minus : Przycisk -.
     * @param pos : Pozycja produktu w RecyclerView, który ma zmiejszyć swoją ilość.
     * @param productValue : TextView wyświetlający sumę do zapłaty za wybrany produkt.
     */
    private fun setDecrementing(bt_minus: Button, pos: Int, productValue: MoneyTextView) {

        bt_minus.setOnClickListener {

              decrementBasket(list, pos)

            Paper.book().write(Constants.PAPER_CART_NAME, list)
            notifyDataSetChanged()

            updateTotalPrice()

            if (list.size > 0) {
                productValue.amount = list[pos].count * list[pos].product.price
            } else {
                productValue.amount = 0f
            }
        }
    }
    /**
     * Metoda wykonywana podczas usuwania danego produktu z koszyka.
     * @param bt_bin : Przycisk usuwania.
     * @param pos : Pozycja produktu w RecyclerView, który ma zwiększyć swoją ilość.
     * @param basketValue : TextView wyświetlający sumę do zapłaty za wybrany produkt.
     */
    private fun setDeleting(bt_bin: Button, pos: Int, basketValue: MoneyTextView) {

        bt_bin.setOnClickListener {
            list.removeAt(pos)
            if (list.size > 0) {
                basketValue.amount = list[pos].getAmount()
            } else {
                basketValue.amount = 0f
            }

            Paper.book().write(Constants.PAPER_CART_NAME, list)
            notifyDataSetChanged()

        }
    }
}
/**
 * ViewHolder dla elementu w koszyku.
 */
class CartProductVH(itemView: View) : RecyclerView.ViewHolder(itemView)