package smp.render;

import mindustry.Vars;
import mindustry.core.Renderer;
import mindustry.graphics.MinimapRenderer;
import mindustry.maps.Map;
import mindustry.maps.Maps;
import mindustry.ui.Minimap;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

import javax.imageio.ImageIO;
import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class Render {

    public static File renderMap() throws IOException {
        BufferedImage image = new BufferedImage(Vars.world.width(), Vars.world.height(), TYPE_INT_ARGB);
        for (int x = 0; x < Vars.world.width(); x++){
            for (int y = 0; y < Vars.world.height(); y++){

                Tile tile = Vars.world.tile(x, y);
                Color color;

                if (tile.block().isVisible()) {
                    if (tile.build != null){
                        color = new java.awt.Color(tile.build.team().color.r, tile.build.team().color.g, tile.build.team().color.b, tile.build.team().color.a);
                        image.setRGB(x, y, color.getRGB());
                    } else {
                        color = new java.awt.Color(tile.block().mapColor.r, tile.block().mapColor.g, tile.block().mapColor.b, tile.block().mapColor.a);
                        image.setRGB(x, y, color.getRGB());
                    }
                } else {
                    int pix = Arrays.stream(tile.floor().editorVariantRegions()).findAny().get().texture.getTextureData().getPixmap().get(0,0);
                    color = new java.awt.Color(tile.floor().asFloor().mapColor.r, tile.floor().asFloor().mapColor.g, tile.floor().asFloor().mapColor.b, tile.floor().asFloor().mapColor.a);
                    System.out.println(pix);
                    image.setRGB(x, y, pix);

                }
            }
        }
        File file = new File("rend.png");
        ImageIO.write(image, "PNG", file);
        return file;
    }
}
