package com.leitnick;

public class Color {

    //TODO: Add Ansi 7 (Light Gray) c0c0c0

    private int ansiCode;
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private static final int ANSI_SHIFT = 16;
    private static final int ANSI_FIRST = 95;
    private static final int ANSI_INCREASE = 40;
    private static final int ANSI_STEPS = 6;
    private static final int ANSI_GRAYSCALE_SHIFT = 10;
    private static final int ANSI_GRAYSCALE_FIRST = 8;
    private static final int ANSI_GRAYSCALE_START = 232;
    private static final int ANSI_MAINS = 128;

    /**
     * <li>Create color by ANSI code.
     * <li>Automatically updates the RGB values
     * @param ansiCode 0 - 255 ANSI Code value
     */
    public Color(int ansiCode) {
        this.ansiCode = ansiCode;
        convertAnsiToRgb();
    }

    /**
     * Create color by RGB
     * @param red 0 - 255 value
     * @param green 0 - 255 value
     * @param blue 0 - 255 value
     */

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * <li>Convert color to ANSI Color
     * <li>Shifts RGB values to closest values within ANSI colors
     * <li>Assigns ANSI code
     * @param color the color to convert to an ANSI color
     */

    public Color(Color color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        convertRgbToAnsi();
    }

    /**
     * <li>Create color by RGB and shift to an ANSI color
     * <li>Shifts RGB values to closest values within ANSI colors
     * <li>Assigns ANSI code
     * @param red 0 - 255 value
     * @param green 0 - 255 value
     * @param blue 0 - 255 value
     * @param asAnsi set to true if shifting into ANSI color range
     */

    public Color(int red, int green, int blue, boolean asAnsi) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        if (asAnsi) {
            convertRgbToAnsi();
        }
    }

    private void convertAnsiToRgb() {
        if (ansiCode < 16) {
            int topAdjust = 0;
            if (ansiCode > 7) topAdjust = 1;
            int colorValue = ANSI_MAINS * (ansiCode / 8 + 1) - topAdjust;
            if (ansiCode % 8 == 1) red = colorValue;
            else if (ansiCode % 8 == 2) green = colorValue;
            else if (ansiCode % 8 == 3) {
                red = colorValue;
                green = colorValue;
            } else if (ansiCode % 8 == 4) blue = colorValue;
            else if (ansiCode % 8 == 5) {
                red = colorValue;
                blue = colorValue;
            } else if (ansiCode % 8 == 6) {
                green = colorValue;
                blue = colorValue;
            } else if (ansiCode == 7) {
                red = 192;
                green = 192;
                blue = 192;
            } else if (ansiCode == 8) {
                red = ANSI_MAINS;
                green = ANSI_MAINS;
                blue = ANSI_MAINS;
            } else if (ansiCode == 15) {
                red = 255;
                green = 255;
                blue = 255;
            }
        } else if (ansiCode <= 231) {
            int startValue = ansiCode - ANSI_SHIFT;
            int redNormalized = startValue / (ANSI_STEPS * ANSI_STEPS);
            if (redNormalized != 0) {
                red = ANSI_FIRST + ANSI_INCREASE * (redNormalized - 1);
            }
            int greenNormalized = (startValue % (ANSI_STEPS * ANSI_STEPS)) / ANSI_STEPS;
            if (greenNormalized != 0) {
                green = ANSI_FIRST + ANSI_INCREASE * (greenNormalized - 1);
            }
            int blueNormalized = startValue % ANSI_STEPS;
            if (blueNormalized != 0) {
                blue = ANSI_FIRST + ANSI_INCREASE * (blueNormalized - 1);
            }
        } else {
            int colorValue = (ansiCode - ANSI_GRAYSCALE_START) * ANSI_GRAYSCALE_SHIFT + ANSI_GRAYSCALE_FIRST;
            red = colorValue;
            green = colorValue;
            blue = colorValue;
        }
    }

    private void convertRgbToAnsi() {
        if (Math.abs(red - green) < 4 && Math.abs(red - blue) < 4 && Math.abs(green - blue) < 4) {
            convertRgbGrayscaleToAnsi();
        } else if (
                (Math.abs(red - ANSI_MAINS) < 4 || red < 4) &&
                (Math.abs(green - ANSI_MAINS) < 4 || green < 4) &&
                (Math.abs(blue - ANSI_MAINS) < 4 || blue < 4)) {
            convertRgbMainColorsToAnsi();
        } else {
            convertRgbGeneralColorsToAnsi();
        }
    }

    private void convertRgbGeneralColorsToAnsi() {
        red = nearestValue(red);
        green = nearestValue(green);
        blue = nearestValue(blue);
        int redNormalized = (red - ANSI_FIRST) / ANSI_INCREASE + 1;
        if (redNormalized < 0) redNormalized = 0;
        int greenNormalized = (green - ANSI_FIRST) / ANSI_INCREASE + 1;
        if (greenNormalized < 0) greenNormalized = 0;
        int blueNormalized = (blue - ANSI_FIRST) / ANSI_INCREASE + 1;
        if (blueNormalized < 0) blueNormalized = 0;
        ansiCode = redNormalized * ANSI_STEPS * ANSI_STEPS + greenNormalized * ANSI_STEPS + blueNormalized + ANSI_SHIFT;
    }

    private void convertRgbMainColorsToAnsi() {
        int[] possibleValues = {0, ANSI_MAINS};
        red = nearestValue(red, possibleValues);
        green = nearestValue(green, possibleValues);
        blue = nearestValue(blue, possibleValues);
        if (red == ANSI_MAINS) {
            if (green == ANSI_MAINS) {
                ansiCode = 3;
            } else if (blue == ANSI_MAINS) {
                ansiCode = 5;
            } else ansiCode = 1;
        } else if (green == ANSI_MAINS) {
            if (blue == ANSI_MAINS) {
                ansiCode = 6;
            } else ansiCode = 2;
        } else ansiCode = 4;
    }

    private void convertRgbGrayscaleToAnsi() {
        int[] possibleValues = new int[25];
        for (int i = 0; i < 25; i++) {
            if (i != 24) {
                possibleValues[i] = ANSI_GRAYSCALE_FIRST + i * ANSI_GRAYSCALE_SHIFT;
            } else possibleValues[i] = 255;
        }
        red = nearestValue(red, possibleValues);
        green = nearestValue(green, possibleValues);
        blue = nearestValue(blue, possibleValues);
        if (red == 255) {
            ansiCode = 15;
        } else {
            ansiCode = ANSI_GRAYSCALE_START + ((red - ANSI_GRAYSCALE_FIRST) / ANSI_GRAYSCALE_SHIFT);
        }
    }

    private int nearestValue(int currentValue) {
        int[] possibleValues = {0, 95, 135, 175, 215, 255};
        return nearestValue(currentValue, possibleValues);
    }

    private int nearestValue(int currentValue, int[] possibleValues) {
        int closest = possibleValues[0];
        int closestDist = Math.abs(currentValue - closest);
        for (int i = 1; i < possibleValues.length; i++) {
            int dist = Math.abs(currentValue - possibleValues[i]);
            if (dist < closestDist) {
                closestDist = dist;
                closest = possibleValues[i];
            } else break;
        }
        return closest;
    }

    public int getAnsiCode() {
        return ansiCode;
    }

    public void setAnsiCode(int ansiCode) {
        this.ansiCode = ansiCode;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    @Override
    public String toString() {
        return "Color{" +
                "ansiCode=" + ansiCode +
                ", red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }
}
