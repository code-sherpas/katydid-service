package com.quipalup.katydid.child.common.secondaryadapter.database

import arrow.core.Either
import arrow.core.right
import com.quipalup.katydid.child.common.domain.Child
import com.quipalup.katydid.child.search.domain.FindChildByIdError
import com.quipalup.katydid.common.id.Id
import java.util.UUID
import javax.inject.Named
import javax.persistence.Entity
import javax.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository

@Named
interface JpaChildRepository : JpaRepository<JpaChild, UUID>

@Entity
@Table(name = "CHILD")
open class JpaChild(
    @javax.persistence.Id
    val id: UUID,
    val name: Child.Name,
    val potraitURL: Child.PortraitURL,
    val isPresent: Child.IsPresent
) {
    fun toDomain(): Either<FindChildByIdError, Child> = Child(
        id = Id(id),
        name = name,
        portraitURL = potraitURL,
        isPresent = isPresent
    ).right()
}
