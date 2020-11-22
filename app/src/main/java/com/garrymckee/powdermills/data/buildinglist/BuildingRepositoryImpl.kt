package com.garrymckee.powdermills.data.buildinglist

import com.garrymckee.powdermills.R
import com.garrymckee.powdermills.domain.building.Building
import com.garrymckee.powdermills.domain.building.BuildingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BuildingRepositoryImpl @Inject constructor() :
    BuildingRepository {

    val testBuildings = listOf(
        Building(
            "Powder Mill",
            R.drawable.building3,
            listOf(R.drawable.building3, R.drawable.building2, R.drawable.building1),
            "The original gunpowder mills had 4 Incorporating Mills. This was increased to 12 Incorporating mills by the next owners, the British Board of Ordnance, and finally to 24 Incorporating Mills by the third owners, Tobin & Horsfall.",
            "An Incorporating Mill finely ground a mixture of saltpetre, sulphur and charcoal together to product crude gunpowder. The process used two heavy millstones to carry out the operation. The millstones were rotated by a mechanical system powered by a water wheel.",
            "The original Incorporating Mills buildings were made of wood and have not survived. The building you see today is a modern reconstruction built in 1993. The large stone walls you see in the Incorporating Mills area are blast walls to prevent an explosion in one set of the incorporating mills spreading to neighbouring incorporating mills."
        ),
        Building(
            "Drying House",
            R.drawable.building1,
            listOf(R.drawable.building1, R.drawable.building2, R.drawable.building3),
            "The original gunpowder mills had 4 Incorporating Mills. This was increased to 12 Incorporating mills by the next owners, the British Board of Ordnance, and finally to 24 Incorporating Mills by the third owners, Tobin & Horsfall.",
            "An Incorporating Mill finely ground a mixture of saltpetre, sulphur and charcoal together to product crude gunpowder. The process used two heavy millstones to carry out the operation. The millstones were rotated by a mechanical system powered by a water wheel.",
            "The original Incorporating Mills buildings were made of wood and have not survived. The building you see today is a modern reconstruction built in 1993. The large stone walls you see in the Incorporating Mills area are blast walls to prevent an explosion in one set of the incorporating mills spreading to neighbouring incorporating mills."
        ),
        Building(
            "Incorporating Mill",
            R.drawable.building4,
            listOf(R.drawable.building1, R.drawable.building2, R.drawable.building3),
            "The original gunpowder mills had 4 Incorporating Mills. This was increased to 12 Incorporating mills by the next owners, the British Board of Ordnance, and finally to 24 Incorporating Mills by the third owners, Tobin & Horsfall.",
            "An Incorporating Mill finely ground a mixture of saltpetre, sulphur and charcoal together to product crude gunpowder. The process used two heavy millstones to carry out the operation. The millstones were rotated by a mechanical system powered by a water wheel.",
            "The original Incorporating Mills buildings were made of wood and have not survived. The building you see today is a modern reconstruction built in 1993. The large stone walls you see in the Incorporating Mills area are blast walls to prevent an explosion in one set of the incorporating mills spreading to neighbouring incorporating mills."
        ),
        Building(
            "Storehouse",
            R.drawable.building3,
            listOf(R.drawable.building1, R.drawable.building2, R.drawable.building3),
            "The original gunpowder mills had 4 Incorporating Mills. This was increased to 12 Incorporating mills by the next owners, the British Board of Ordnance, and finally to 24 Incorporating Mills by the third owners, Tobin & Horsfall.",
            "An Incorporating Mill finely ground a mixture of saltpetre, sulphur and charcoal together to product crude gunpowder. The process used two heavy millstones to carry out the operation. The millstones were rotated by a mechanical system powered by a water wheel.",
            "The original Incorporating Mills buildings were made of wood and have not survived. The building you see today is a modern reconstruction built in 1993. The large stone walls you see in the Incorporating Mills area are blast walls to prevent an explosion in one set of the incorporating mills spreading to neighbouring incorporating mills."
        ),
        Building(
            "Water House",
            R.drawable.building2,
            listOf(R.drawable.building1, R.drawable.building2, R.drawable.building3),
            "The original gunpowder mills had 4 Incorporating Mills. This was increased to 12 Incorporating mills by the next owners, the British Board of Ordnance, and finally to 24 Incorporating Mills by the third owners, Tobin & Horsfall.",
            "An Incorporating Mill finely ground a mixture of saltpetre, sulphur and charcoal together to product crude gunpowder. The process used two heavy millstones to carry out the operation. The millstones were rotated by a mechanical system powered by a water wheel.",
            "The original Incorporating Mills buildings were made of wood and have not survived. The building you see today is a modern reconstruction built in 1993. The large stone walls you see in the Incorporating Mills area are blast walls to prevent an explosion in one set of the incorporating mills spreading to neighbouring incorporating mills."
        ),
        Building(
            "Stone House",
            R.drawable.building1,
            listOf(R.drawable.building1, R.drawable.building2, R.drawable.building3),
            "The original gunpowder mills had 4 Incorporating Mills. This was increased to 12 Incorporating mills by the next owners, the British Board of Ordnance, and finally to 24 Incorporating Mills by the third owners, Tobin & Horsfall.",
            "An Incorporating Mill finely ground a mixture of saltpetre, sulphur and charcoal together to product crude gunpowder. The process used two heavy millstones to carry out the operation. The millstones were rotated by a mechanical system powered by a water wheel.",
            "The original Incorporating Mills buildings were made of wood and have not survived. The building you see today is a modern reconstruction built in 1993. The large stone walls you see in the Incorporating Mills area are blast walls to prevent an explosion in one set of the incorporating mills spreading to neighbouring incorporating mills."
        ),
        Building(
            "Barracks",
            R.drawable.building2,
            listOf(R.drawable.building1, R.drawable.building2, R.drawable.building3),
            "The original gunpowder mills had 4 Incorporating Mills. This was increased to 12 Incorporating mills by the next owners, the British Board of Ordnance, and finally to 24 Incorporating Mills by the third owners, Tobin & Horsfall.",
            "An Incorporating Mill finely ground a mixture of saltpetre, sulphur and charcoal together to product crude gunpowder. The process used two heavy millstones to carry out the operation. The millstones were rotated by a mechanical system powered by a water wheel.",
            "The original Incorporating Mills buildings were made of wood and have not survived. The building you see today is a modern reconstruction built in 1993. The large stone walls you see in the Incorporating Mills area are blast walls to prevent an explosion in one set of the incorporating mills spreading to neighbouring incorporating mills."
        )
    )

    override suspend fun observeBuildings(): Flow<List<Building>> = flow {
        emit(testBuildings)
    }

    override suspend fun observeBuildingWithId(id: String): Flow<Building> = flow {
        emit(testBuildings.first { it.name == id })
    }
}