package de.android.ayrathairullin.bashim.data


object SearchRepositoryProvider {
    fun provideSearchRepository() : SearchRepositoty {
        return SearchRepositoty(BashImApiService.create())
    }
}