package lv.chernishenko.chucknorrisfacts.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import lv.chernishenko.chucknorrisfacts.dao.ChuckNorrisFactsDao
import lv.chernishenko.chucknorrisfacts.model.ChuckNorrisFact
import javax.inject.Inject

class ChuckNorrisLocalFactsUseCase @Inject constructor(
    private val dao: ChuckNorrisFactsDao
) {
    operator fun invoke(): Flow<PagingData<ChuckNorrisFact>> = Pager(
        PagingConfig(pageSize = 25, prefetchDistance = 20, enablePlaceholders = false),
        pagingSourceFactory = { dao.localFacts() }
    ).flow
}