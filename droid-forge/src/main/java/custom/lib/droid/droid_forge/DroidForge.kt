package custom.lib.droid.droid_forge

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import custom.lib.droid.droid_forge.model.AppColor
import custom.lib.droid.droid_forge.model.Container
import custom.lib.droid.droid_forge.model.PrimaryText
import custom.lib.droid.droid_forge.model.SecondaryText
import custom.lib.droid.droid_forge.model.background
import custom.lib.droid.droid_forge.model.onBackground
import custom.lib.droid.flyer_extension.FlyerExtension
import custom.lib.droid.signal_extenstion.SignalExtension
import custom.web.view.compose.UserWebView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class DroidForgeMain(
    private val activity: ComponentActivity,
    private val context: Context,
) {
    var flyer: String = ""
    var url: String = ""
    var currentData: String = ""
    var appInfo: ShortAppInfo? = null

    private val sPref = activity.getSharedPreferences("user", Context.MODE_PRIVATE)
    private var statusApp by mutableStateOf(StatusApp.LOAD)


    @RequiresApi(Build.VERSION_CODES.O)
    private val activityResultLauncher =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.e("TAG", "registerForActivityResult: call", )
                CoroutineScope(Dispatchers.IO).launch {
                    Log.e("TAG", "registerForActivityResult CoroutineScope: call", )
                    setFlyer()
                }
            }
        }

    private fun setFlyer() {
        Log.e("TAG", "setFlyer: call", )
        FlyerExtension.setExtension(
            value = flyer,
            context = context,
            onError = { statusApp = StatusApp.FAIL },
            onSuccess = { statusApp = StatusApp.SUCCESS }
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun checkPush(){
        Log.e("TAG", "checkPush: call ${currentData.length}", )

        if (currentData.isEmpty()) {
            if (sPref.getString("key", "")?.isNotEmpty() == true) {
                Log.e("TAG", "checkPush: link is save", )
                statusApp = StatusApp.SUCCESS_DOWNLOAD
            }

            when {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    setFlyer()
                }
                activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {

                }
                else -> {
                    activityResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

                }
            }
        } else {
            if (checkDateCondition(currentData)) {
                Log.e("TAG", "checkPush: открывать заглушку", )
                statusApp = StatusApp.FAIL
            } else {
                Log.e("TAG", "checkPush: открывать вьюху", )
                setFlyer()
            }
        }



    }
    @Composable
    fun ShowContent(
        userContent: @Composable AppColor.() -> Unit
    ) {
        when (statusApp) {
            StatusApp.LOAD -> Loader(appInfo)
            StatusApp.SUCCESS -> UserWebView(data = url)
            StatusApp.FAIL -> userContent(AppColor())
            StatusApp.SUCCESS_DOWNLOAD -> UserWebView(data = sPref.getString("key", "")!!)
        }
//        when (statusApp) {
//            StatusApp.LOAD -> Loader(appInfo)
//            else -> Loader(info = appInfo)
//        }
    }


    companion object {

        fun checkSignal(context: Context, value: String) {
            SignalExtension.setSignal(context = context, value = value)
        }
    }

}

enum class StatusApp {
    LOAD, SUCCESS, FAIL, SUCCESS_DOWNLOAD
}

data class ShortAppInfo(
    val name: String,
    @DrawableRes val ic: Int,
)

@Composable
private fun Loader(info: ShortAppInfo?) {
    val view = LocalView.current
    val window = (view.context as Activity).window
    window.statusBarColor = background.toArgb()

    if (info == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(background),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                SecondaryText(
                    value = "Подождите несколько секунд. Идет загрузка материала.",
                    color = onBackground.copy(.5f),
                    textAlign = TextAlign.Center,
                )
            }
        }
    } else {
        val infiniteAnimation = rememberInfiniteTransition(label = "")
        val loadShape = infiniteAnimation.animateFloat(
            initialValue = 0.0f,
            targetValue = 0.2f,
            animationSpec = InfiniteRepeatableSpec(
                animation = tween(1000)
            ), label = ""
        )
        
        Column(
            modifier = Modifier.fillMaxSize().background(background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Container(weight = 1f) {}
            Container(weight = 5f) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(70.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = info.ic),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(modifier = Modifier
                            .background(Color.White.copy(alpha = loadShape.value))
                            .fillMaxSize(),)
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    PrimaryText(value = info.name, color = onBackground)
                }
            }
            Container(weight = 1f) {
                LinearProgressIndicator()
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
private fun checkDateCondition(dateString: String): Boolean {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate = LocalDate.now()
    val providedDate = LocalDate.parse(dateString, dateFormatter)

    return currentDate.minusDays(4) <= providedDate
}

