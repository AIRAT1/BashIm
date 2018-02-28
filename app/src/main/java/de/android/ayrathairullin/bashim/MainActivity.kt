package de.android.ayrathairullin.bashim

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import de.android.ayrathairullin.bashim.data.SearchRepositoryProvider
import de.android.ayrathairullin.bashim.data.SearchRepositoty
import de.android.ayrathairullin.bashim.data.SourceOfQuotes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val tag: String = "MainActivity"
const val TEST_DEVICE_ID: String = "30D9300605DD60CE60905D4B3C11B63F"

class MainActivity : AppCompatActivity(), ChangeSourceListener {
    override fun sourceChanged(position: Int) {
        Log.d(tag, "from main = ${adapter[position]}")
        val intent = Intent(applicationContext, QuotesActivity::class.java)
        intent.putExtra(INTENT_NAME_NAME, adapter[position].name)
        intent.putExtra(INTENT_SITE_NAME, adapter[position].site)
        startActivity(intent)
    }

    @BindView(R.id.recyclerView)
    lateinit var recyclerView : RecyclerView

    @BindView(R.id.banner)
    lateinit var adView : AdView

    val compositeDisposable : CompositeDisposable = CompositeDisposable()
    val repository : SearchRepositoty = SearchRepositoryProvider.provideSearchRepository()
    lateinit var adapter : SourceOfQuotesAdapter
    private val list: MutableList<SourceOfQuotes> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        val adRequest: AdRequest = AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)
                .build()
//        adView.loadAd(adRequest)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llm
        compositeDisposable.add(
                repository.searchSourcesOfQuotes()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            result.forEach {
                                list.addAll(it)
                            }
                            adapter = SourceOfQuotesAdapter(list)
                            adapter.addListener(this)
                            recyclerView.adapter = adapter
                        })
        )
    }
}
