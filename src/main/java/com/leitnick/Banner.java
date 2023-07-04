package com.leitnick;

import com.github.lalyos.jfiglet.FigletFont;

import java.io.IOException;

public class Banner {

    private static final String FONT_PATH = "src/main/resources/fonts/";
    private static final String ANSI_COLOR = "${AnsiColor.";
    private static final String ANSI_BG = "${AnsiBackground.";
    private static final String ANSI_STYLE_BOLD = "${AnsiStyle.BOLD}";
    private static final String COLOR_DEFAULT = ANSI_COLOR + "DEFAULT}";
    private static final String BG_DEFAULT = ANSI_BG + "DEFAULT}";
    private static final String STYLE_DEFAULT = "${AnsiStyle.NORMAL}";
    private static final ColorUtil colorUtil = new ColorUtil();

    private int stringColor;
    private int bgColor;
    private boolean isBold;
    private boolean isBgColored;

    public Banner(int stringColor, boolean isBgColored, int bgColor, boolean isBold) {
        this.stringColor = stringColor;
        this.isBgColored = isBgColored;
        this.bgColor = bgColor;
        this.isBold = isBold;
    }
    public Banner(int stringColor, boolean isBold) {
        this(stringColor, false, -1, isBold);
    }

    public Banner(boolean isBgColored, int bgColor, boolean isBold) {
        this(-1, true, bgColor, isBold);
    }

    public Banner(int stringColor, boolean isBgColored, int bgColor) {
        this(stringColor, isBgColored, bgColor, false);
    }

    public Banner(int stringColor) {
        this(stringColor, false, -1, false);
    }

    public Banner(boolean isBgColored, int bgColor) {
        this(-1, isBgColored, bgColor, false);
    }

    public Banner(boolean isBold) {
        this(-1, false, -1, isBold);
    }

    public String[] makeBanner(String message) {
        String[] banner = createBase(message);
        String[] bannerWithEdits = new String[banner.length];
        for (int i = 0; i < banner.length; i++) {
            bannerWithEdits[i] = editLine(banner[i]);
        }
        return bannerWithEdits;
    }

    public String[] makePreviewBanner(String message, Color startColor, Color endColor) {
        String[] banner = createBase(message);
        int bannerLength = banner.length;
        String[] bannerWithEdits = new String[bannerLength];
        Color[] colorGradient = createColorGradient(startColor, endColor, bannerLength);
        for (int i = 0; i < bannerLength; i++) {
            bannerWithEdits[i] = editPreviewLine(banner[i], colorGradient[i], new Color(0));
        }
        return bannerWithEdits;
    }

    private String[] createBase(String message) {
        String messageFiglet;
        try {
            messageFiglet = FigletFont.convertOneLine(FONT_PATH + "s-relief.flf", message.toUpperCase());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] base = new String[]{};
        if (messageFiglet != null) {
            base = messageFiglet.split("\n");
        }
        return base;
    }

    private String editLine(String line) {
        StringBuilder editedLine = new StringBuilder(line);
        if (stringColor > - 1) {
            editedLine.insert(0, ANSI_COLOR + stringColor + "}").append(COLOR_DEFAULT);
        }
        if (isBgColored) {
            editedLine.insert(0, ANSI_BG + bgColor + "}").append(BG_DEFAULT);
        }
        if (isBold) {
            editedLine.insert(0, ANSI_STYLE_BOLD).append(STYLE_DEFAULT);
        }
        return editedLine.toString();
    }

    private String editPreviewLine(String line, Color textColor, Color bgColor) {
        StringBuilder editedLine = new StringBuilder(line);
        if (isBgColored) {
            editedLine.insert(0, colorUtil.backgroundColor(bgColor));
        }
        if (stringColor > - 1 && isBold) {
            editedLine = new StringBuilder(colorUtil.stringColor(editedLine.toString(), textColor, "bold"));
        } else if (stringColor > - 1) {
            editedLine = new StringBuilder(colorUtil.stringColor(editedLine.toString(), textColor));
        } else if (isBold) {
            editedLine = new StringBuilder(colorUtil.stringStyle(editedLine.toString(), "bold"));
        }
        return editedLine.toString();
    }

    private Color[] createColorGradient(Color startColor, Color endColor, int steps) {
        Color[] colorGradient = new Color[steps];
        colorGradient[0] = startColor;
        colorGradient[steps - 1] = endColor;
        int r1 = startColor.getRed();
        int g1 = startColor.getGreen();
        int b1 = startColor.getBlue();
        int r2 = endColor.getRed();
        int g2 = endColor.getGreen();
        int b2 = endColor.getBlue();
        for (int i = 1; i < steps - 1; i++) {
            int r = r1 + (r2 - r1) * i / (steps - 1);
            int g = g1 + (g2 - g1) * i / (steps - 1);
            int b = b1 + (b2 - b1) * i / (steps - 1);
            colorGradient[i] = new Color(r, g, b, true);
        }
        return colorGradient;
    }

}
