package jp.tinyport.androidtv3pane.core

import android.app.Application
import java.io.OutputStream
import java.io.PrintStream
import jp.tinyport.androidtv3pane.BuildConfig
import jp.tinyport.logger.ConsoleLogDestination
import jp.tinyport.logger.Logger

val log = Logger()

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val isReleaseBuild = !BuildConfig.DEBUG

        if (!isReleaseBuild) {
            log.addDestination(
                ConsoleLogDestination("ImageDownload").apply {
                    minLevel = Logger.Level.VERBOSE
                }
            )
        }

        if (isReleaseBuild) {
            val emptyStream = PrintStream(object : OutputStream() {
                override fun write(b: Int) {
                    // do nothing.
                }
            })

            System.setOut(emptyStream)
            System.setErr(emptyStream)
        }

        log.info("Hello")
    }
}
