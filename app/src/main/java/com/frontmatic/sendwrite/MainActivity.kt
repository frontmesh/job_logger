package com.frontmatic.sendwrite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.frontmatic.sendwrite.ui.theme.SendWriteTheme
import com.kedia.ogparser.OpenGraphCacheProvider
import com.kedia.ogparser.OpenGraphCallback
import com.kedia.ogparser.OpenGraphParser
import com.kedia.ogparser.OpenGraphResult


class MainActivity : ComponentActivity(), OpenGraphCallback {

    private val LINKS_TO_TEST = mutableListOf(
        "https://www.linkedin.com/jobs/view/3268254896/",
        "https://www.linkedin.com/posts/madhusmita-padhy_machinelearning-datascience-activity-6886390508722163712-yhQ0",
        "https://www.youtube.com/watch?v=n3zsoX7bRlc",
        "https://twitter.com/levelsio/status/1481942293108359168",
        "https://stackoverflow.com/questions/44515769/conda-is-not-recognized-as-internal-or-external-command",
        "https://github.com/Priyansh-Kedia/OpenGraphParser",
        "https://chat.whatsapp.com/DdWAKRkt2VfAmd4OS47y7P",
        "https://www.reddit.com/r/MachineLearning/comments/s3mjqf/deep_learning_interviews_hundreds_of_fully_solved/?utm_medium=android_app&utm_source=share",
        "https://instagram.com/fcbarcelona?utm_medium=copy_link",
        "https://www.facebook.com/groups/777946865955982/permalink/1385110621906267/",
    )

    private val openGraphParser by lazy {
        OpenGraphParser(
            listener = this,
            showNullOnEmpty = true,
            cacheProvider = OpenGraphCacheProvider(this)
        )
    }

    override fun onPostResponse(openGraphResult: OpenGraphResult) {
        Log.d("TAG!!!!", "response $openGraphResult")
        Log.d("openGraphResult",openGraphResult.toString())
        if (LINKS_TO_TEST.isNotEmpty()) {
            openGraphParser.parse(LINKS_TO_TEST.first())
            LINKS_TO_TEST.removeFirstOrNull()
        }
    }

    override fun onError(error: String) {
        Log.e("ERROR TAG!!!!", "$error")
//        tview.text = error
        if (LINKS_TO_TEST.isNotEmpty()) {
            openGraphParser.parse(LINKS_TO_TEST.first())
            LINKS_TO_TEST.removeFirstOrNull()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openGraphParser.parse(LINKS_TO_TEST.first())
        LINKS_TO_TEST.removeFirstOrNull()

        if (Intent.ACTION_SEND == intent.action && intent.type != null) {
            if ("text/plain" == intent.type) {
                val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (sharedText != null) {
                    Log.d("MainActivity", "sharedText: $sharedText")
                    // Handle the share d text (LinkedIn job posting)
                    // Here, you can parse and process the shared content as needed.
                }
            }
        }

        setContent {
            SendWriteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SendWriteTheme {
        Greeting("Android")
    }
}