package de.android.ayrathairullin.bashim

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.android.ayrathairullin.bashim.data.Quote
import kotlinx.android.synthetic.main.source_item.view.*


class QuotesAdapter(list: MutableList<Quote>) : RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {
    private val mItems : MutableList<Quote> = list

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItems[position]
        holder.title.text = Html.fromHtml(item.htmlText)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
        val layoutInflater = LayoutInflater.from(parent!!.context)
        val view = layoutInflater.inflate(R.layout.quotes_item, parent, false)
        return ViewHolder(view).listen{ position, type ->
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val title = view.textView!!
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event : (position : Int, type : Int) -> Unit) : T{
        itemView.setOnClickListener {
            event.invoke(adapterPosition, getItemViewType())
        }
        return this
    }

    operator fun get(position: Int) : Quote {
        return mItems[position]
    }
}