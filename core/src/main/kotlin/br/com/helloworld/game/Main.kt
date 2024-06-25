package br.com.helloworld.game

import br.com.helloworld.game.constants.Constants
import br.com.helloworld.game.screens.GameScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

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
        pixmap.fillRectangle(0,0,pixmap.width,pixmap.height)
        pixmap.setColor(0xFFFFFFF)
        textureAtlas = Texture(pixmap)
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
        val numTiles = (textureAtlas.width / Constants.TILE_SIZE) * (textureAtlas.height / Constants.TILE_SIZE)
        map = buildList {
            for (i in 0 until (Constants.MAP_HEIGHT * Constants.MAP_WIDTH).toInt()) {
                add((i % numTiles).toInt())
            }
        }
        setScreen(GameScreen())
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
