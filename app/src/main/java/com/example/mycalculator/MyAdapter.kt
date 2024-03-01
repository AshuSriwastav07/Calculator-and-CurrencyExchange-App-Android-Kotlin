import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycalculator.R
import com.squareup.picasso.Picasso

class MyAdapter(private val currencyPrice: Array<String>, private val ImagesUrl: Array<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.currency_exchage, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currencyPrice.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currencyPrice[position], ImagesUrl[position])
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val price: TextView = itemView.findViewById(R.id.currency_rate)
        private val image: ImageView = itemView.findViewById(R.id.FlagImage)

        fun bind(priceText: String, imageUrl: String) {
            price.text = priceText
            Picasso.get()
                .load(imageUrl)
                .fit()
                .centerInside()
                .into(image)
        }
    }
}
