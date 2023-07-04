package com.leitnick;

public class ColorUtil {

    /**
     * Format the style of a String
     * @param str String
     * @param style italic, bold, or underline
     * @return the stylized String
     */

    public String stringStyle(String str, String style) {
        return stringColor(str, null, style);
    }

    /**
     * Format the color of a String
     * @param str String
     * @param color Color to apply
     * @return the colorized String
     */

    public String stringColor(String str, Color color) {
        return stringColor(str, color, null);
    }

    /**
     * Format the color and style of a String
     * @param str String
     * @param color Color to apply
     * @param style italic, bold, or underline
     * @return the colorized and stylized String
     */

    public String stringColor(String str, Color color, String style) {
        String newString;
        StringBuilder styler = new StringBuilder();
        if (style != null) {
            String[] styles = style.split(",");
            for (String styletype : styles) {
                switch (styletype.trim()) {
                    case "italic" -> styler.append(";3");
                    case "underline" -> styler.append(";4");
                    case "bold" -> styler.append(";1");
                }
            }
        }
        if (style == null) {
            newString = String.format("\u001b[38;2;%s;%s;%sm%s\u001b[0m", color.getRed(), color.getGreen(), color.getBlue(), str);
        } else if (color == null) {
            newString = String.format("\u001b[0%sm%s\u001b[0m", styler, str);
        } else {
            newString = String.format("\u001b[38;2;%s;%s;%s%sm%s\u001b[0m", color.getRed(), color.getGreen(), color.getBlue(), styler, str);
        }
        return newString;
    }

    /**
     * Format the background color of a string
     * @param color color of background
     * @return background with color
     */
    public String backgroundColor(Color color) {
        return String.format("\u001b[48;2;%s;%s;%sm", color.getRed(), color.getGreen(), color.getBlue()); // The 48 means background color
    }

    /**
     * Format the background color for a fixed length
     * @param color color of background
     * @param length length of background
     * @return background with color of specified length
     */
    public String backgroundColor(Color color, int length) {
        String str = backgroundColor(color);
        String reset = "\u001b[0m";
        String extension = "%" + length + "s" + reset;
        return str + String.format(extension, " ");
    }

}
