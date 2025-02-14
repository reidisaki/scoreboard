package com.wyman.scoreboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ScoreViewModel : ViewModel() {
    private val _state = MutableStateFlow<ScoreboardState>(ScoreboardState.PresenterModeState())
    val state: StateFlow<ScoreboardState> = _state

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

    fun updateTitle(state: ScoreboardState, title: String) {
        if(state is ScoreboardState.EditModeState) {
            _state.value = state.copy(title = title)
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