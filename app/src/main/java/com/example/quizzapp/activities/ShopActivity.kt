package com.example.quizzapp.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.quizzapp.R
import com.example.quizzapp.R.drawable
import com.example.quizzapp.objects.Responses.AllItemsResponse
import com.example.quizzapp.objects.ShopItem
import com.example.quizzapp.vm.ShopViewModel
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.activity_shop.societiesList

class ShopActivity : AppCompatActivity(){

    lateinit var viewModel : ShopViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_shop)

        viewModel = ViewModelProviders.of(this).get(ShopViewModel::class.java)

        var items: ArrayList<ShopItem> = arrayListOf(

            ShopItem(drawable.a1l, "100",1),
            ShopItem(drawable.a2l, "100",2),
            ShopItem(drawable.a3l, "100",3),
            ShopItem(drawable.a4l, "100",4),
            ShopItem(drawable.a5l, "100",5),
            ShopItem(drawable.a6l, "100",6),

            ShopItem(drawable.a1y, "200",7),
            ShopItem(drawable.a2y, "200",8),
            ShopItem(drawable.a3y, "200",9),
            ShopItem(drawable.a4y, "200",10),
            ShopItem(drawable.a5y, "200",11),
            ShopItem(drawable.a6y, "200",12),

            ShopItem(drawable.a1z, "400",13),
            ShopItem(drawable.a2z, "400",14),
            ShopItem(drawable.a3z, "400",15),
            ShopItem(drawable.a4z, "400",16),
            ShopItem(drawable.a5z, "400",17),
            ShopItem(drawable.a6z, "400",18),
            ShopItem(drawable.a7z, "400",19)

        )


        societiesList.adapter = ShopListAdapter(this,items)


        societiesList.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                run {
                   // playerImage.setImageResource((societiesList.adapter.getItem(position) as ShopItem).image)
                    viewModel.currentItem.value = societiesList.adapter.getItem(position) as ShopItem
                    viewModel.currentItemPosition = position

                }
            }



        buyButton.setOnClickListener {
            if(viewModel.currentItem.value != null)
                viewModel.buyItem() }

        val playerItemsObserver =  Observer<AllItemsResponse>{ playersItems ->

            var filteredMap = items.stream().filter { e -> e.id.let { playersItems.ownedItems?.contains(it) }!! }
            filteredMap.forEach { it.price = "owned" }

            societiesList.adapter = ShopListAdapter(this,items)

            playerPoints.text = playersItems.money.toString()

            playerImage.setImageResource(items.first{it.id == playersItems.selectedItem}.image)


        }

        val isSuccessfullObserver = Observer<Boolean> {

            if(it)
            {
                Toast.makeText(this,"purchase completed successfully",Toast.LENGTH_LONG).show()
                viewModel.getPlayerItems()
            }else
            {
                Toast.makeText(this,"purchase failed. Item already owned or not enough points",Toast.LENGTH_LONG).show()

            }
        }


        selectButton.setOnClickListener {

            var item = (societiesList.adapter.getItem(viewModel.currentItemPosition) as ShopItem)

            var condition = (viewModel.playersItems.value?.ownedItems as ArrayList<Int>).contains(item.id)

            if(condition){
                playerImage.setImageResource((societiesList.adapter.getItem(viewModel.currentItemPosition) as ShopItem).image)
                viewModel.setAvatar(item.id)
            }




        }

        viewModel.playersItems.observe(this,playerItemsObserver)
        viewModel.succesful.observe(this,isSuccessfullObserver)

        viewModel.getPlayerItems()
    }

}


class ShopListAdapter(private val context: Activity,
                           private val dataSource: ArrayList<ShopItem>) : ArrayAdapter<ShopItem>(context,R.layout.shop_item) {


        override fun getCount(): Int {
            return dataSource.count()
        }


        override fun getItem(position: Int): ShopItem? {
            return dataSource[position]
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


            val inflater = context.layoutInflater
            val rowView = inflater.inflate(R.layout.shop_item, null, true)


            val desc:TextView = rowView.findViewById(R.id.price) as TextView
            val img : ImageView = rowView.findViewById(com.example.quizzapp.R.id.itemImage) as ImageView



            img.setImageResource(dataSource[position].image)
            desc.text = dataSource[position].price

            return rowView

        }


}