package com.quipalup.katydid.child.common.primaryadapter.httprest

import com.quipalup.katydid.child.ChildMother.CRISTINA
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ChildResourceKtTest {

    @Test
    fun `mapper preserve values unchanged`() {
        CRISTINA.toResource().let {
            assertThat(it).usingRecursiveComparison().isEqualTo(
                with(CRISTINA) {
                    ChildResource(
                        id = id.value.toString(),
                        type = "child",
                        attributes = ChildResourceAttributes(
                            name = name.value,
                            portraitURL = portraitURL.value.toString(),
                            isPresent = isPresent.value
                        )
                    )
                })
        }
    }
}
