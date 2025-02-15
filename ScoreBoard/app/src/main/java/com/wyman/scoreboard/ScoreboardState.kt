package com.wyman.scoreboard

sealed class ScoreboardState {
    data class EditModeState(
        val topTeamScorePeriod1: Int =0,
        val bottomTeamScorePeriod1:Int =0,
        val topTeamScorePeriod2: Int =0,
        val bottomTeamScorePeriod2:Int=0,
        val topTeamScorePeriod3: Int=0,
        val bottomTeamScorePeriod3:Int=0,
        val topTeamName: String="",
        val bottomTeamName: String="",
        val title: String = ""
    ): ScoreboardState()
    data class PresenterModeState(
        val topTeamScorePeriod1: Int =0,
        val bottomTeamScorePeriod1:Int =0,
        val topTeamScorePeriod2: Int =0,
        val bottomTeamScorePeriod2:Int=0,
        val topTeamScorePeriod3: Int=0,
        val bottomTeamScorePeriod3:Int=0,
        val topTeamName: String="",
        val bottomTeamName: String="",
        val title: String = ""
    ): ScoreboardState()
    data object Initialize: ScoreboardState()
}