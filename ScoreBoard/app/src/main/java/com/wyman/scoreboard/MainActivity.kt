package com.wyman.scoreboard


import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wyman.scoreboard.Utils.LockScreenOrientation
import com.wyman.scoreboard.Utils.findActivity
import com.wyman.scoreboard.ui.theme.ScoreBoardTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.scoreDataStore by preferencesDataStore(name = "settings")
class MainActivity : ComponentActivity() {

    private val viewModel: ScoreViewModel by viewModels()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {

            val state by viewModel.state.collectAsStateWithLifecycle()
            HideSystemBars()
            ScoreBoardTheme {
                Scaffold(modifier = Modifier.fillMaxWidth()) { _ ->
                    if (state is ScoreboardState.Initialize || state is ScoreboardState.PresenterModeState) {
                        PresenterMode(
                            this,
                            viewModel,
                            state as ScoreboardState.PresenterModeState
                        )
                    } else {
                        EditMode(
                            this,
//                            scoreDataStore,
                            viewModel,
                            state as ScoreboardState.EditModeState
                        )
                    }

                }
            }
        }
    }

    @Composable
    fun HideSystemBars() {
        val context = LocalContext.current

        DisposableEffect(Unit) {
            val window = context.findActivity()?.window ?: return@DisposableEffect onDispose {}
            val insetsController = WindowCompat.getInsetsController(window, window.decorView)

            insetsController.apply {
                hide(WindowInsetsCompat.Type.statusBars())
                hide(WindowInsetsCompat.Type.navigationBars())
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

            onDispose {
                insetsController.apply {
                    show(WindowInsetsCompat.Type.statusBars())
                    show(WindowInsetsCompat.Type.navigationBars())
                    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun PresenterMode(
        context: Context,
        viewModel: ScoreViewModel,
        state: ScoreboardState.PresenterModeState
    ) {
        LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        DisposableEffect(viewModel) {
            val title = getFromStateOrDataStore(TITLE, state.title)
            val top_team = getFromStateOrDataStore(TEAM_1, state.topTeamName)
            val bottom_team = getFromStateOrDataStore(TEAM_2, state.bottomTeamName)
            val topScore1 = getFromStateOrDataStore(SCORE_TOP_1, state.topTeamScorePeriod1.toString())
            val topScore2 = getFromStateOrDataStore(SCORE_TOP_2, state.topTeamScorePeriod2.toString())
            val topScore3 = getFromStateOrDataStore(SCORE_TOP_3, state.topTeamScorePeriod3.toString())
            val bottomScore1 = getFromStateOrDataStore(SCORE_BOTTOM_1, state.bottomTeamScorePeriod1.toString())
            val bottomScore2 = getFromStateOrDataStore(SCORE_BOTTOM_2, state.bottomTeamScorePeriod2.toString())
            val bottomScore3 = getFromStateOrDataStore(SCORE_BOTTOM_3, state.bottomTeamScorePeriod3.toString())
            viewModel.updateState(
                title, top_team,bottom_team,topScore1,topScore2,topScore3, bottomScore1, bottomScore2, bottomScore3
            )
            println("isaki disposable Effect called")
            onDispose {
            }
        }
//        val title = getFromStateOrDataStore(TITLE, state.title)
//        val top_team = getFromStateOrDataStore(TEAM_1, state.topTeamName)
//        val bottom_team = getFromStateOrDataStore(TEAM_2, state.bottomTeamName)
//        val topScore1 = getFromStateOrDataStore(SCORE_TOP_1, state.topTeamScorePeriod1.toString())
//        val topScore2 = getFromStateOrDataStore(SCORE_TOP_2, state.topTeamScorePeriod2.toString())
//        val topScore3 = getFromStateOrDataStore(SCORE_TOP_3, state.topTeamScorePeriod3.toString())
//        val bottomScore1 = getFromStateOrDataStore(SCORE_BOTTOM_1, state.bottomTeamScorePeriod1.toString())
//        val bottomScore2 = getFromStateOrDataStore(SCORE_BOTTOM_2, state.bottomTeamScorePeriod2.toString())
//        val bottomScore3 = getFromStateOrDataStore(SCORE_BOTTOM_3, state.bottomTeamScorePeriod3.toString())
        Column(Modifier.padding(start = 24.dp, bottom = 12.dp, top = 12.dp, end = 12.dp)) {
            Box(Modifier.fillMaxSize()) {
                Row(Modifier.align(Alignment.TopStart)) {
                    Image(
                        modifier = Modifier
                            .size(200.dp)
                            .padding(10.dp)
                            .clickable {
//                                viewModel.updateTitlePresenter(state, title)
//                                viewModel.updateTeamsPresenter(state,top_team, bottom_team)
//                                viewModel.updateScores(state, mutableListOf(topScore1, topScore2, topScore3), mutableListOf(bottomScore1, bottomScore2, bottomScore3))
//                                viewModel.updateValues(
//                                    state,
//                                    mutableListOf(topScore1, topScore2, topScore3),
//                                    mutableListOf(bottomScore1, bottomScore2, bottomScore3)
//                                )
                                //pass in all the data here one by one and then update the state
                                //instead of using state values use these "other values"
                                viewModel.toggleEditMode(state)
                            },
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(top = 25.dp, start = 25.dp),
                        fontSize = 38.sp,
                        lineHeight = 40.sp,
                        text = state.title
                        //"President's Day Showdown\nFebruary 15, 2025 - March 1 \nOakland Convention Center"
                    )
                }


                // first row will be just the periods
                // second row will be the first team
                // third row will be the second team
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(top = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HeaderRow(
                        "", "1", "2", "3",
                        bgColor = Color("#1E5075".toColorInt()), textColor = Color.White
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(top = 140.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ContentRow(
//                        top_team,
//                        topScore1,
//                        topScore2,
//                        topScore3,
                        state.topTeamName,
                        state.topTeamScorePeriod1.toString(),
                        state.topTeamScorePeriod2.toString(),
                        state.topTeamScorePeriod3.toString(),
                        bgColor = Color.White, textColor = Color.Black
                    )
                }
                HorizontalDivider(
                    Modifier
                        .width(560.dp)
                        .align(Alignment.CenterStart)
                        .padding(top = 230.dp),
//                        .padding(2.dp),
                    thickness = 2.dp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                        .padding(top = 310.dp),

                    ) {
                    ContentRow(
//                        bottom_team,
//                        bottomScore1,
//                        bottomScore2,
//                        bottomScore3,
                        state.bottomTeamName,
                        state.bottomTeamScorePeriod1.toString(),
                        state.bottomTeamScorePeriod2.toString(),
                        state.bottomTeamScorePeriod3.toString(),
                        bgColor = Color.White, textColor = Color.Black
                    )

                }
            }
        }
    }

    @Composable
    fun HeaderRow(
        team: String, first: String, second: String, third: String,
        bgColor: Color, textColor: Color
    ) {

        Box(
            Modifier
                .height(height = 40.dp)
                .fillMaxWidth(.70f)

//            .widthIn(min = 560.dp, max = 560.dp)

        ) {
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
//                .background(color = initalColor)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
//                color = textColor,
                    text = team
                )
            }
        }
        for (i in 1..3) {
            Column(
                modifier = Modifier
                    .width(76.dp)
                    .padding(start = 10.dp, bottom = 5.dp)
                    .background(color = bgColor),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp),
                    fontSize = 24.sp,
                    text = i.toString(),
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

    @Composable
    fun ContentRow(
        team: String, first: String, second: String, third: String,
        bgColor: Color, textColor: Color
    ) {
        val initialThird = setInitialScoreValue(third)
        val initialFirst = setInitialScoreValue(first)
        val initialSecond = setInitialScoreValue(second)
        val firstModifier = scaleTextModifierForScoreCard(initialFirst)
        val secondModifier = scaleTextModifierForScoreCard(initialSecond)
        val thirdModifier = scaleTextModifierForScoreCard(initialThird)
        Box(
            Modifier
//                .size(width = 560.dp, height = 75.dp)
                .fillMaxWidth(.70f),
//            .widthIn(min = 600.dp, max = 600.dp),
            contentAlignment = Alignment.CenterStart

        ) {
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
//                .background(color = initalColor)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .height(IntrinsicSize.Max),
                    fontSize = 38.sp,
                    lineHeight = 38.sp,
                    fontWeight = FontWeight.Bold,
//                color = textColor,
                    text = team
                )
            }
        }
        Column(
            modifier = Modifier
                .width(76.dp)
                .padding(start = 10.dp)
                .background(color = bgColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = firstModifier,
                fontSize = 38.sp,
                text = initialFirst,
                color = textColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Column(
            modifier = Modifier
                .width(76.dp)
                .padding(start = 10.dp)
                .background(color = bgColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = secondModifier,
                fontSize = 38.sp,
                text = initialSecond,
                color = textColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Column(
            modifier = Modifier
                .width(76.dp)
                .padding(start = 10.dp)
                .background(color = bgColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = thirdModifier,
                fontSize = 38.sp,
                text = initialThird,
                color = textColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }

    private fun setInitialScoreValue(score: String): String =
        if (score == "0") {
            "-"
        } else {
            score
        }

    private fun scaleTextModifierForScoreCard(initial: String): Modifier =
        if (initial.length > 1) {
            Modifier.padding(top = 8.dp, bottom = 8.dp)
        } else {
            Modifier.padding(start = 15.dp, end = 15.dp, top = 8.dp, bottom = 8.dp)
        }


    private fun getFromDataStore(key: Key<String>): Flow<String> = scoreDataStore.data
        .map { preferences ->
            preferences[key] ?: ""
        }

    val TITLE = stringPreferencesKey("TITLE")
    val TEAM_1 = stringPreferencesKey("TEAM_1")
    val TEAM_2 = stringPreferencesKey("TEAM_2")
    val SCORE_TOP_1 = stringPreferencesKey("SCORE_TOP_1")
    val SCORE_BOTTOM_1 = stringPreferencesKey("SCORE_BOTTOM_1")
    val SCORE_BOTTOM_2 = stringPreferencesKey("SCORE_BOTTOM_2")
    val SCORE_BOTTOM_3 = stringPreferencesKey("SCORE_BOTTOM_3")
    val SCORE_TOP_2 = stringPreferencesKey("SCORE_TOP_2")
    val SCORE_TOP_3 = stringPreferencesKey("SCORE_TOP_3")

    suspend fun writeToDataStore(key: Key<String>, value: String) {
        scoreDataStore.edit { preferences ->
            if(value.isNotBlank()){
                preferences[key] = value
            }

        }   
    }

//    @Composable
 fun getFromStateOrDataStore(key: Key<String>, value: String) =
     //check if the key is a score value the default value will be 0 instead of ""
        if (value.trim() == "" || value == "-" || key.name.contains("SCORE")) {
//            getFromDataStore(key).collectAsState(initial = "").value
            runBlocking {
                getFromDataStore(key).first().toString()
            }
        } else {
            value
        }

}

