package io.github.insideranh.talemessage;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorRegistry {

    private static final Map<String, Color> COLORS = new HashMap<>();

    static {
        COLORS.put("black", new Color(0, 0, 0));
        COLORS.put("dark_blue", new Color(0, 0, 170));
        COLORS.put("dark_green", new Color(0, 170, 0));
        COLORS.put("dark_aqua", new Color(0, 170, 170));
        COLORS.put("dark_red", new Color(170, 0, 0));
        COLORS.put("dark_purple", new Color(170, 0, 170));
        COLORS.put("gold", new Color(255, 170, 0));
        COLORS.put("gray", new Color(170, 170, 170));
        COLORS.put("dark_gray", new Color(85, 85, 85));
        COLORS.put("blue", new Color(85, 85, 255));
        COLORS.put("green", new Color(85, 255, 85));
        COLORS.put("aqua", new Color(85, 255, 255));
        COLORS.put("red", new Color(255, 85, 85));
        COLORS.put("light_purple", new Color(255, 85, 255));
        COLORS.put("yellow", new Color(255, 255, 85));
        COLORS.put("white", new Color(255, 255, 255));

        COLORS.put("orange", new Color(255, 165, 0));
        COLORS.put("pink", new Color(255, 192, 203));
        COLORS.put("purple", new Color(128, 0, 128));
        COLORS.put("cyan", new Color(0, 255, 255));
        COLORS.put("magenta", new Color(255, 0, 255));
        COLORS.put("lime", new Color(0, 255, 0));
        COLORS.put("brown", new Color(139, 69, 19));
    }

    public static Color getColor(String colorString) {
        if (colorString == null || colorString.isEmpty()) {
            return null;
        }

        String normalized = colorString.toLowerCase().trim();

        if (COLORS.containsKey(normalized)) {
            return COLORS.get(normalized);
        }

        if (normalized.contains(",")) {
            Color rgb = parseRGB(normalized);
            if (rgb != null) {
                return rgb;
            }
        }

        return parseHex(normalized);
    }

    /**
     * Parse RGB color values in format "R,G,B" or "R,G,B,A".
     *
     * <p>Examples:</p>
     * <ul>
     *   <li>"255,85,85" - RGB format</li>
     *   <li>"255, 85, 85" - RGB with spaces</li>
     *   <li>"255,85,85,255" - RGBA format</li>
     *   <li>"0,0,0" - Black</li>
     *   <li>"255,255,255" - White</li>
     * </ul>
     *
     * @param rgb RGB string in format "R,G,B" or "R,G,B,A"
     * @return the Color object, or null if invalid
     */
    public static Color parseRGB(String rgb) {
        if (rgb == null || rgb.isEmpty()) {
            return null;
        }

        try {
            String[] parts = rgb.split(",");

            if (parts.length != 3 && parts.length != 4) {
                return null;
            }

            int r = Integer.parseInt(parts[0].trim());
            int g = Integer.parseInt(parts[1].trim());
            int b = Integer.parseInt(parts[2].trim());

            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
                return null;
            }

            if (parts.length == 4) {
                int a = Integer.parseInt(parts[3].trim());
                if (a < 0 || a > 255) {
                    return null;
                }
                return new Color(r, g, b, a);
            }

            return new Color(r, g, b);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Color parseHex(String hex) {
        if (hex == null || hex.isEmpty()) {
            return null;
        }

        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        if (hex.length() != 6 && hex.length() != 8) {
            return null;
        }

        try {
            int rgb = Integer.parseInt(hex.substring(0, 6), 16);
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = rgb & 0xFF;

            if (hex.length() == 8) {
                int a = Integer.parseInt(hex.substring(6, 8), 16);
                return new Color(r, g, b, a);
            }

            return new Color(r, g, b);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Color interpolate(Color color1, Color color2, float factor) {
        if (factor <= 0) return color1;
        if (factor >= 1) return color2;

        int r = (int) (color1.getRed() + factor * (color2.getRed() - color1.getRed()));
        int g = (int) (color1.getGreen() + factor * (color2.getGreen() - color1.getGreen()));
        int b = (int) (color1.getBlue() + factor * (color2.getBlue() - color1.getBlue()));
        int a = (int) (color1.getAlpha() + factor * (color2.getAlpha() - color1.getAlpha()));

        return new Color(r, g, b, a);
    }

}