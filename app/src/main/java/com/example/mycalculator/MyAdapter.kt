package com.tlc.mycalculator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

/*class MyAdapter(private val currencyPrice: Array<String>, private val ImagesUrl: Array<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context) // Inflates layout XML into corresponding View objects
        val view = inflater.inflate(R.layout.currency_exchage, parent, false) // Inflates the item layout
        return MyViewHolder(view) // Returns a new ViewHolder
    }

    override fun getItemCount(): Int {
        return currencyPrice.size // Returns the total number of items in the data set
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currencyPrice[position], ImagesUrl[position]) // Binds data to the views
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { // Defines the ViewHolder class
        private val price: TextView = itemView.findViewById(R.id.currency_rate) // Initializes TextView for currency rate
        private val image: ImageView = itemView.findViewById(R.id.FlagImage) // Initializes ImageView for flag image

        fun bind(priceText: String, imageUrl: String) { // Defines a function to bind data to views
            price.text = priceText // Sets the currency rate text
            Picasso.get() // Initializes Picasso library for image loading and caching
                .load(imageUrl) // Loads the image from the specified URL
                .fit() // Resizes the image to fit the ImageView
                .centerInside() // Centers the image inside the ImageView
                .into(image) // Sets the loaded image to the ImageView
        }
    }*/

class MyAdapter(context: Context, private val rates: List<String>, private val ImageLink: List<String>) :
    ArrayAdapter<String>(context, R.layout.currency_exchage, rates) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.currency_exchage, parent, false)

        val textView = rowView.findViewById<TextView>(R.id.currency_rate)
        val imageView=rowView.findViewById<ImageView>(R.id.FlagImage)
        textView.text = rates[position]

        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(ImageLink[position]).into(imageView)

/*
        Picasso.get()
            .load(ImageLink[position])
            .into(imageView)

*/

        return rowView
    }
}