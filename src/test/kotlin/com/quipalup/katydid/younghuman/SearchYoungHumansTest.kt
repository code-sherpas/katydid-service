package com.quipalup.katydid.younghuman

import arrow.core.right
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SearchYoungHumansTest {

    private val expectedYoungHumans: List<YoungHuman> = mutableListOf(YoungHuman(UUID.randomUUID()))
    private val youngHumanRepository: YoungHumanRepository = mockk()

    @Test
    fun `searches young humans`() {
        `GIVEN young humans does exist`()

        SearchYoungHumans(youngHumanRepository).execute().fold(
            { Assertions.fail("It must be right") },
            { assertThat(it).containsExactlyInAnyOrderElementsOf(expectedYoungHumans) }
        )
    }

    private fun `GIVEN young humans does exist`() {
        every { youngHumanRepository.search() } returns expectedYoungHumans.right()
    }
}
