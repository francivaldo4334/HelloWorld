@file:JvmName("Lwjgl3Launcher")

package br.com.helloworld.game.lwjgl3

import br.com.helloworld.game.Main
import br.com.helloworld.game.constants.Constants
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

/** Launches the desktop (LWJGL3) application. */
fun main() {
    // This handles macOS support and helps on Windows.
    if (StartupHelper.startNewJvmIfRequired())
        return
    Lwjgl3Application(Main(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("HelloWorld")
        setWindowedMode(
            (Constants.MAP_WIDTH * Constants.TILE_SIZE).toInt(),
            (Constants.MAP_HEIGHT * Constants.TILE_SIZE).toInt()
        )
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
