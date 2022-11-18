package com.example.seoulpublicswimmingpool.data

data class ListProgramByPublicSportsFacilitiesService(
    val RESULT: RESULT,
    val list_total_count: Int,
    val row: List<Row>
)