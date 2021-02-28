package com.quipalup.katydid.younghuman.common.primaryadapter.httprest

import com.quipalup.katydid.younghuman.YoungHumanMother.Cristina
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class YoungHumanResourceKtTest {

    @Test
    fun `mapper preserve values unchanged`() {
        Cristina.toResource().let {
            assertThat(it).usingRecursiveComparison().isEqualTo(
                with(Cristina) {
                    YoungHumanResource(
                        id = id.value.toString(),
                        type = "young-human",
                        attributes = YoungHumanResourceAttributes(
                            name = name.value,
                            portraitURL = portraitURL.value.toString(),
                            isPresent = isPresent.value
                        )
                    )
                })
        }
    }
}
