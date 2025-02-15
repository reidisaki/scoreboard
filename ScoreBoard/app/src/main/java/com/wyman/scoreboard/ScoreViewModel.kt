package com.wyman.scoreboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ScoreViewModel : ViewModel() {
    private val _state = MutableStateFlow<ScoreboardState>(ScoreboardState.PresenterModeState())
    val state: StateFlow<ScoreboardState> = _state

    fun reset() {
        _state.value = ScoreboardState.EditModeState()
    }
    fun updateState(
        title: String,
        top_team: String,
        bottom_team: String,
        topScore1: String,
        topScore2: String,
        topScore3: String,
        bottomScore1: String,
        bottomScore2: String,
        bottomScore3: String
    ) {
        _state.value  = ScoreboardState.PresenterModeState(
            title = title,
            topTeamName = top_team,
            bottomTeamName = bottom_team,
            topTeamScorePeriod1 = topScore1.toInt(),
            topTeamScorePeriod2 = topScore2.toInt(),
            topTeamScorePeriod3 = topScore3.toInt(),
            bottomTeamScorePeriod1 = bottomScore1.toInt(),
            bottomTeamScorePeriod2 = bottomScore2.toInt(),
            bottomTeamScorePeriod3 = bottomScore3.toInt(),

        )
    }
    fun toggleEditMode(state: ScoreboardState){
        if (state is ScoreboardState.PresenterModeState) {
            _state.value = ScoreboardState.EditModeState(
                state.topTeamScorePeriod1,
                state.bottomTeamScorePeriod1,
                state.topTeamScorePeriod2,
                state.bottomTeamScorePeriod2,
                state.topTeamScorePeriod3,
                state.bottomTeamScorePeriod3,
                state.topTeamName,
                state.bottomTeamName,
                state.title
            )
        } else if(state is ScoreboardState.EditModeState) {
            _state.value = ScoreboardState.PresenterModeState(
                state.topTeamScorePeriod1,
                state.bottomTeamScorePeriod1,
                state.topTeamScorePeriod2,
                state.bottomTeamScorePeriod2,
                state.topTeamScorePeriod3,
                state.bottomTeamScorePeriod3,
                state.topTeamName,
                state.bottomTeamName,
                state.title
            )
        }
    }

    fun updateScores(state: ScoreboardState, topScores: List<String>, bottomScores: List<String>){
        //preserving the state from data store to edit mode
        if(state is ScoreboardState.PresenterModeState) {
            _state.value = state.copy(
                topTeamScorePeriod1 = topScores[0].toInt(),
                topTeamScorePeriod2 = topScores[1].toInt(),
                topTeamScorePeriod3 = topScores[2].toInt(),
                bottomTeamScorePeriod1 = bottomScores[0].toInt(),
                bottomTeamScorePeriod2 = bottomScores[1].toInt(),
                bottomTeamScorePeriod3 = bottomScores[2].toInt(),
            )
        }
    }
    fun updateTitle(state: ScoreboardState, title: String) {
        if(state is ScoreboardState.EditModeState) {
            _state.value = state.copy(title = title)
        }
    }
    fun updateTitlePresenter(state: ScoreboardState, title: String) {
        if(state is ScoreboardState.PresenterModeState) {
            _state.value = state.copy(title = title)
        }
    }
    fun updateTeamsPresenter(state: ScoreboardState, topTeam: String, bottomTeam: String) {
        if(state is ScoreboardState.PresenterModeState) {
            _state.value = state.copy(
                topTeamName = topTeam,
                bottomTeamName = bottomTeam
            )
        }
    }
    fun updateTeams(state: ScoreboardState, topTeam: String, bottomTeam: String) {
        if(state is ScoreboardState.EditModeState) {
            _state.value = state.copy(
                topTeamName = topTeam,
                bottomTeamName = bottomTeam
            )
        }
    }
    fun increment(state: ScoreboardState, period: Int, topTeam: Boolean) {
        when (state) {
            is ScoreboardState.EditModeState -> {
                val nextState = when (period) {
                    1 -> {
                        if(topTeam) {
                            state.copy(
                                topTeamScorePeriod1 = state.topTeamScorePeriod1 + 1
                            )
                        } else {
                            state.copy(
                                bottomTeamScorePeriod1 = state.bottomTeamScorePeriod1 + 1
                            )
                        }
                    }

                    2 -> {
                        if(topTeam) {
                            state.copy(
                                topTeamScorePeriod2 = state.topTeamScorePeriod2 + 1
                            )
                        } else {
                            state.copy(
                                bottomTeamScorePeriod2 = state.bottomTeamScorePeriod2 + 1
                            )
                        }
                    }

                    3 -> {
                        if(topTeam) {
                            state.copy(
                                topTeamScorePeriod3 = state.topTeamScorePeriod3 + 1
                            )
                        } else {
                            state.copy(
                                bottomTeamScorePeriod3 = state.bottomTeamScorePeriod3 + 1
                            )
                        }
                    }

                    else -> {
                        state
                    }
                }
                _state.value = nextState
            }

            else -> {
//no op cant increment in presenter mode
            }
        }

    }

    fun decrement(state: ScoreboardState, period: Int, topTeam: Boolean) {
        when (state) {
            is ScoreboardState.EditModeState -> {
                val nextState = when (period) {
                    1 -> {
                        if(topTeam) {
                            state.copy(
                                topTeamScorePeriod1 = state.topTeamScorePeriod1 - 1
                            )
                        } else {
                            state.copy(
                                bottomTeamScorePeriod1 = state.bottomTeamScorePeriod1 - 1
                            )
                        }
                    }

                    2 -> {
                        if(topTeam) {
                            state.copy(
                                topTeamScorePeriod2 = state.topTeamScorePeriod2 - 1
                            )
                        } else {
                            state.copy(
                                bottomTeamScorePeriod2 = state.bottomTeamScorePeriod2 - 1
                            )
                        }
                    }

                    3 -> {
                        if(topTeam) {
                            state.copy(
                                topTeamScorePeriod3 = state.topTeamScorePeriod3 - 1
                            )
                        } else {
                            state.copy(
                                bottomTeamScorePeriod3 = state.bottomTeamScorePeriod3 - 1
                            )
                        }
                    }

                    else -> {
                        state
                    }
                }
                _state.value = nextState
            }

            else -> {
//no op cant increment in presenter mode
            }
        }

    }
}