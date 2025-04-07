package lv.chernishenko.chucknorrisfacts.usecase

import lv.chernishenko.chucknorrisfacts.dao.ChuckNorrisFactsDao
import javax.inject.Inject

class ChuckNorrisGetSingleFactUseCase @Inject constructor(
    private val dao: ChuckNorrisFactsDao
) {

    operator fun invoke(id: String) = dao.getFact(id)
}