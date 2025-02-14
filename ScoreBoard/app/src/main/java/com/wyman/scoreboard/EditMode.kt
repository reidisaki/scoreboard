package com.wyman.scoreboard

import android.content.pm.ActivityInfo
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wyman.scoreboard.Utils.LockScreenOrientation


@Composable
fun EditMode(viewModel: ScoreViewModel, state: ScoreboardState.EditModeState) {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(text = "Title")
        OutlinedTextField(
            onValueChange = {
                viewModel.updateTitle(state, it)
            },
            minLines = 5, value = state.title
        )
        Button(onClick = {
            viewModel.toggleEditMode(state)
        }) {
            Text("View Score Board")
        }
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Team 1:")
            OutlinedTextField(value = state.topTeamName, onValueChange = {
                viewModel.updateTeams(state, it, state.bottomTeamName)
            })
        }
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Team 2:")
            OutlinedTextField(
                value = state.bottomTeamName,
                onValueChange = { viewModel.updateTeams(state, state.topTeamName, it) })
        }
        Row(
            Modifier
                .fillMaxSize()
                .widthIn(max = 200.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(modifier = Modifier.widthIn(max = 100.dp), text = state.topTeamName)
            Text(modifier = Modifier.widthIn(max = 100.dp), text = state.bottomTeamName)
        }
        Row {
            PopulateSets(viewModel, state = state, periodSet = "1")
        }
        Row {
            PopulateSets(viewModel, state = state, periodSet = "2")
        }
        Row {
            PopulateSets(viewModel, state = state, periodSet = "3")
        }


    }
}

@Composable
fun PopulateSets(
    viewModel: ScoreViewModel,
    state: ScoreboardState.EditModeState,
    periodSet: String
) {
    Row(
        Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PopulateBox(viewModel, state = state, periodSet)
    }
}

@Composable
fun PopulateBox(
    viewModel: ScoreViewModel,
    state: ScoreboardState.EditModeState,
    periodSet: String
) {
    //add logic to figure out which period this is
    var team1Score: String = "-"
    var team2Score: String = "-"
    when (periodSet) {
        "1" -> {
            team1Score = state.topTeamScorePeriod1.toString()
            team2Score = state.bottomTeamScorePeriod1.toString()
        }

        "2" -> {
            team1Score = state.topTeamScorePeriod2.toString()
            team2Score = state.bottomTeamScorePeriod2.toString()
        }

        "3" -> {
            team1Score = state.topTeamScorePeriod3.toString()
            team2Score = state.bottomTeamScorePeriod3.toString()
        }

    }
    RenderSetBox(viewModel, state, team1Score, periodSet.toInt(), true)
    Text(text = "Set $periodSet", fontSize = 32.sp, fontWeight = FontWeight.Bold)
    RenderSetBox(viewModel, state, team2Score, periodSet.toInt(), false)
}

@Composable
fun RenderSetBox(
    viewModel: ScoreViewModel,
    state: ScoreboardState.EditModeState,
    teamScore: String,
    period: Int,
    topTeam: Boolean
) {
    Box(
        Modifier
            .width(150.dp)
            .height(100.dp)
            .border(color = Color.Companion.Gray, width = 1.dp, shape = RoundedCornerShape(1.dp))
    ) {
        Text(
            text = teamScore,
            fontSize = 28.sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
        )
        Text(
            text = "+",
            fontSize = 36.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 5.dp)
                .clickable {
                    viewModel.increment(state, period, topTeam)
                })
        Text(
            text = "-",
            fontSize = 46.sp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 5.dp)
                .clickable {
                    viewModel.decrement(state, period, topTeam)
                }
        )
    }

}
