package mikaoctofrentzen.com.mkmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mikaoctofrentzen.com.mkmovie.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}