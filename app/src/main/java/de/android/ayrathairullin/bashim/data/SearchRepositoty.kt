package de.android.ayrathairullin.bashim.data


class SearchRepositoty(val apiService: BashImApiService) {
    fun searchQuotes(site : String, name : String) : io.reactivex.Observable<List<Quote>> {
        return apiService.searchQuotes(site, name, 100)
    }

    fun searchSourcesOfQuotes() : io.reactivex.Observable<List<List<SourceOfQuotes>>> {
        return apiService.searchSources()
    }
}