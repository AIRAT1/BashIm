package de.android.ayrathairullin.bashim

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import de.android.ayrathairullin.bashim.data.Quote
import de.android.ayrathairullin.bashim.data.SearchRepositoryProvider
import de.android.ayrathairullin.bashim.data.SearchRepositoty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val INTENT_NAME_NAME = "name"
const val INTENT_SITE_NAME = "site"

class QuotesActivity : AppCompatActivity(){

    @BindView(R.id.recyclerView)
    lateinit var recyclerView : RecyclerView
    val compositeDisposable : CompositeDisposable = CompositeDisposable()
    val repository : SearchRepositoty = SearchRepositoryProvider.provideSearchRepository()
    lateinit var adapter : SourceOfQuotesAdapter
    private val list: MutableList<Quote> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llm
        val site = intent.getStringExtra(INTENT_SITE_NAME)
        val name = intent.getStringExtra(INTENT_NAME_NAME)
        compositeDisposable.add(
                repository.searchQuotes(site, name)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                                list.addAll(result)
                            recyclerView.adapter = QuotesAdapter(list)
                            Log.d(tag, list.toString())
                        })
        )
    }
}