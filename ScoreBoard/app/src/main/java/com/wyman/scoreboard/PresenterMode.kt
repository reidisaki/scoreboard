import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.wyman.scoreboard.R
import com.wyman.scoreboard.ScoreViewModel
import com.wyman.scoreboard.ScoreboardState
import com.wyman.scoreboard.Utils.LockScreenOrientation

/* regular mode
@Composable
    fun PresenterMode(viewModel: ScoreViewModel, state: ScoreboardState.PresenterModeState) {
        LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        Column(Modifier.padding(12.dp)) {
            Row {
                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(10.dp)
                        .clickable {
                            viewModel.toggleEditMode(state)
                        },
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.padding(top = 25.dp, start = 25.dp),
                    fontSize = 32.sp,
                    lineHeight = 32.sp,
                    text = state.title//"President's Day Showdown\n February 15, 2025 - March 1 \n Oakland Convention Center"
                )
            }
            // first row will be just the periods
            // second row will be the first team
            // third row will be the second team
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeaderRow(
                    "", "1", "2", "3",
                    bgColor = Color("#1E5075".toColorInt()), textColor = Color.White
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ContentRow(
                    state.topTeamName,
                    state.topTeamScorePeriod1.toString(),
                    state.topTeamScorePeriod2.toString(),
                    state.topTeamScorePeriod3.toString(),
                    bgColor = Color.White, textColor = Color.Black
                )
            }
            HorizontalDivider(
                Modifier
                    .width(550.dp)
                    .padding(2.dp),
                thickness = 2.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ContentRow(
                    state.bottomTeamName,
                    state.bottomTeamScorePeriod1.toString(),
                    state.bottomTeamScorePeriod2.toString(),
                    state.bottomTeamScorePeriod3.toString(),
                    bgColor = Color.White, textColor = Color.Black
                )

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
        for(i in 1..3){
            Column(
                modifier = Modifier
                    .width(76.dp)
                    .padding(start = 10.dp, bottom)
                    .background(color = bgColor),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top=5.dp, bottom=5.dp),
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
                    fontSize = 28.sp,
                    lineHeight = 28.sp,
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
                modifier =firstModifier,
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

    fun setInitialScoreValue(score: String): String =
        if (score == "0") {
            "12"
        } else {
            score
        }

    fun scaleTextModifierForScoreCard(initial: String): Modifier =
        if(initial.length > 1) {
            Modifier.padding(top=8.dp, bottom=8.dp)
        } else {
            Modifier.padding(start = 15.dp, end = 15.dp, top=8.dp, bottom=8.dp)
        }
*/

//boxed version
/*
@Composable
fun PresenterMode(viewModel: ScoreViewModel, state: ScoreboardState.PresenterModeState) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    Column(Modifier.padding(start=24.dp, bottom=12.dp, top=12.dp, end=12.dp)) {
        Box(Modifier.fillMaxSize()) {
            Row(Modifier.align(Alignment.TopStart)) {
                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(10.dp)
                        .clickable {
                            viewModel.toggleEditMode(state)
                        },
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.padding(top = 25.dp, start = 25.dp),
                    fontSize = 38.sp,
                    lineHeight = 38.sp,
                    text = "President's Day Showdown\n February 15, 2025 - March 1 \n Oakland Convention Center"
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
                    .padding(top = 130.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ContentRow(
                    state.topTeamName,
                    state.topTeamScorePeriod1.toString(),
                    state.topTeamScorePeriod2.toString(),
                    state.topTeamScorePeriod3.toString(),
                    bgColor = Color.White, textColor = Color.Black
                )
            }
            HorizontalDivider(
                Modifier
                    .width(590.dp)
                    .align(Alignment.CenterStart)
                    .padding(top = 180.dp),
//                        .padding(2.dp),
                thickness = 2.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .padding(top = 260.dp),

                ) {
                ContentRow(
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
                lineHeight = 28.sp,
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

fun setInitialScoreValue(score: String): String =
    if (score == "0") {
        "12"
    } else {
        score
    }

fun scaleTextModifierForScoreCard(initial: String): Modifier =
    if (initial.length > 1) {
        Modifier.padding(top = 8.dp, bottom = 8.dp)
    } else {
        Modifier.padding(start = 15.dp, end = 15.dp, top = 8.dp, bottom = 8.dp)
    }


*/
