package lv.chernishenko.chucknorrisfacts.usecase

import lv.chernishenko.chucknorrisfacts.dao.ChuckNorrisFactsDao
import lv.chernishenko.chucknorrisfacts.model.ChuckNorrisFact
import javax.inject.Inject

class ChuckNorrisSaveFactUseCase @Inject constructor(
    private val dao: ChuckNorrisFactsDao
) {
    suspend operator fun invoke(fact: ChuckNorrisFact) = dao.insertFact(fact)
}