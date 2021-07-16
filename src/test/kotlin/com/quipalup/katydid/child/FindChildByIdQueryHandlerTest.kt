package com.quipalup.katydid.child

import com.quipalup.katydid.child.ChildMother.BLANCA
import com.quipalup.katydid.child.common.domain.Child
import com.quipalup.katydid.child.common.domain.ChildRepository
import com.quipalup.katydid.child.search.application.ChildResult
import com.quipalup.katydid.child.search.application.FindChildByIdQuery
import com.quipalup.katydid.child.search.application.FindChildByIdQueryHandler
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class FindChildByIdQueryHandlerTest {
    @Test
    fun `find child by id`() {

        print(FindChildByIdQueryHandler(childRepository))
        FindChildByIdQueryHandler(childRepository).execute(findChildByIdQuery).fold(
            { Assertions.fail(it.toString()) },
            { assertThat(it).isEqualTo(expectedResult) }
        )
    }

    companion object {
        private val id = "5ee62461-adb8-4618-a110-06290a787223"

        private val findChildByIdQuery = FindChildByIdQuery(
            id = id
        )

        private fun Child.toResult(): ChildResult =
            ChildResult(
                id = id.value.toString(),
                name = name.value,
                portraitURL = portraitURL.value.toString(),
                isPresent = isPresent.value
            )

        private val expectedResult: ChildResult = BLANCA.toResult()

        private val childRepository: ChildRepository = mockk()
    }
}
