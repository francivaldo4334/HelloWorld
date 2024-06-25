package br.com.helloworld.game

import br.com.helloworld.game.constants.Constants
import br.com.helloworld.game.screens.GameScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import kotlin.random.Random

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
class Main : Game() {
    lateinit var camera: OrthographicCamera
    lateinit var batch: SpriteBatch
    lateinit var textureAtlas: Texture
    lateinit var tileRegions: List<TextureRegion>
    lateinit var map: List<Int>

    override fun create() {
        camera = OrthographicCamera()
        camera.setToOrtho(false, Constants.MAP_WIDTH * Constants.TILE_SIZE, Constants.MAP_HEIGHT * Constants.TILE_SIZE)
        camera.update()
        batch = SpriteBatch()
        val pixmap = Pixmap(
            (Constants.MAP_WIDTH * Constants.TILE_SIZE).toInt(),
            (Constants.MAP_WIDTH * Constants.TILE_SIZE).toInt(),
            Pixmap.Format.RGB888
        )
        for (y in 0 until (pixmap.height / Constants.TILE_SIZE).toInt()){
            for (x in 0 until (pixmap.width / Constants.TILE_SIZE).toInt()){
                pixmap.setColor(Random.nextInt(0xff000000.toInt(), 0xffffffff.toInt()))
                pixmap.fillRectangle((x * Constants.TILE_SIZE).toInt(), (y * Constants.TILE_SIZE).toInt(), 32, 32)
            }
        }
        textureAtlas = Texture(pixmap)
        pixmap.dispose()

        tileRegions = buildList {
            for (y in 0 until (textureAtlas.height / Constants.TILE_SIZE).toInt()) {
                for (x in 0 until (textureAtlas.width / Constants.TILE_SIZE).toInt()) {
                    add(
                        index = (y * (textureAtlas.width / Constants.TILE_SIZE) + x).toInt(),
                        element = TextureRegion(
                            textureAtlas,
                            x * Constants.TILE_SIZE,
                            y * Constants.TILE_SIZE,
                            Constants.TILE_SIZE,
                            Constants.TILE_SIZE
                        )
                    )
                }
            }
        }
        map = buildList {
            for (i in 0 until (Constants.MAP_HEIGHT * Constants.MAP_WIDTH).toInt()) {
                if (
                    (i % Constants.MAP_WIDTH).toInt() == 0 ||
                    (i % Constants.MAP_WIDTH) == Constants.MAP_WIDTH -1 ||
                    (i % Constants.MAP_HEIGHT) == Constants.MAP_HEIGHT -1
                    ) {
                    add(1)
                }
                else
                    add(0)
            }
        }
    }

    override fun render() {
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.begin()
        for (y in 0 until Constants.MAP_HEIGHT.toInt()) {
            for (x in 0 until Constants.MAP_WIDTH.toInt()) {
                val tileIndex = map[(y * Constants.MAP_WIDTH + x).toInt()]
                batch.draw(tileRegions[tileIndex], x * Constants.TILE_SIZE, y * Constants.TILE_SIZE)
            }
        }
        batch.end()
        super.render()
    }

    override fun dispose() {
        batch.dispose()
        textureAtlas.dispose()
        super.dispose()
    }
}
