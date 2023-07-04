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

    private Color stringColor;
    private Color bgColor;
    private Color gradientEnd;
    private boolean isBold;
    private boolean isBgColored;
    private Color borderColor = null;

    public Banner(Color stringColor, boolean isBgColored, Color bgColor, boolean isBold) {
        this.stringColor = stringColor;
        this.isBgColored = isBgColored;
        this.bgColor = bgColor;
        this.isBold = isBold;
        this.gradientEnd = stringColor;
    }
    public Banner(Color stringColor, boolean isBold) {
        this(stringColor, false, null, isBold);
    }

    public Banner(boolean isBgColored, Color bgColor, boolean isBold) {
        this(null, true, bgColor, isBold);
    }

    public Banner(Color stringColor, boolean isBgColored, Color bgColor) {
        this(stringColor, isBgColored, bgColor, false);
    }

    public Banner(Color stringColor) {
        this(stringColor, false, null, false);
    }

    public Banner(boolean isBgColored, Color bgColor) {
        this(null, isBgColored, bgColor, false);
    }

    public Banner(boolean isBold) {
        this(null, false, null, isBold);
    }

    public String[] makeBanner(String message) {
        String[] banner = createBase(message);
        int bannerLength = banner.length;
        if (borderColor != null) {
            bannerLength += 4;
            for (int i = 0; i < banner.length; i++) {
                StringBuilder str = new StringBuilder(banner[i]).insert(0, "   ").append("   ");
                banner[i] = str.toString();
            }
        }
        int stringLength = banner[0].length();
        String[] bannerWithEdits = new String[bannerLength];
        Color[] colorGradient = createColorGradient(stringColor, gradientEnd, banner.length);
        if (borderColor != null) {
            for (int i = 0; i < bannerLength; i++) {
                if (i < 2 || i > bannerLength - 3) {
                    boolean isEdge = i == 0 || i == bannerLength - 1;
                    bannerWithEdits[i] = makeTopAndBottomBorder("", stringLength, isEdge);
                } else {
                    bannerWithEdits[i] = editLine(banner[i - 2], colorGradient[i - 2], bgColor);
                }
            }
        } else {
            for (int i = 0; i < bannerLength; i++) {
                bannerWithEdits[i] = editLine(banner[i], colorGradient[i], bgColor);
            }
        }
        return bannerWithEdits;
    }

    public String[] makePreviewBanner(String message) {
        String[] banner = createBase(message);
        int bannerLength = banner.length;
        if (borderColor != null) {
            bannerLength += 4;
            for (int i = 0; i < banner.length; i++) {
                StringBuilder str = new StringBuilder(banner[i]).insert(0, "   ").append("   ");
                banner[i] = str.toString();
            }
        }
        int stringLength = banner[0].length();
        String[] bannerWithEdits = new String[bannerLength];
        Color[] colorGradient = createColorGradient(stringColor, gradientEnd, banner.length);
        if (borderColor != null) {
            for (int i = 0; i < bannerLength; i++) {
                if (i < 2 || i > bannerLength - 3) {
                    boolean isEdge = i == 0 || i == bannerLength - 1;
                    bannerWithEdits[i] = makeTopAndBottomBorderPreview("", stringLength, isEdge);
                } else {
                    bannerWithEdits[i] = editPreviewLine(banner[i - 2], colorGradient[i - 2], bgColor);
                }
            }
        } else {
            for (int i = 0; i < bannerLength; i++) {
                bannerWithEdits[i] = editPreviewLine(banner[i], colorGradient[i], bgColor);
            }
        }
        return bannerWithEdits;
    }

    public void addGradient(Color gradientEnd) {
        this.gradientEnd = gradientEnd;
    }

    public void addBorder(Color borderColor) {
        this.borderColor = borderColor;
    }

    private String makeTopAndBottomBorderPreview(String line, int stringLength, boolean isEdge) {
        String formatString = "%" + stringLength + "s";
        StringBuilder editedLine;
        if (isEdge) {
            editedLine = new StringBuilder(colorUtil.backgroundColor(borderColor, 6 + stringLength));
        } else {
            editedLine = new StringBuilder(colorUtil.backgroundColor(borderColor, 3))
                    .append(colorUtil.backgroundColor(bgColor, stringLength))
                    .append(colorUtil.backgroundColor(borderColor, 3));
        }
        return editedLine.toString();
    }

    private String makeTopAndBottomBorder(String line, int stringLength, boolean isEdge) {
        String formatString = "%" + stringLength + "s";
        StringBuilder editedLine = new StringBuilder(String.format(formatString, line));
        if (isEdge) {
            editedLine.insert(0, ANSI_BG + borderColor.getAnsiCode() + "}      ").append(BG_DEFAULT);
        } else {
            editedLine
                    .insert(0, ANSI_BG + bgColor.getAnsiCode() + "}")
                    .insert(0, ANSI_BG + borderColor.getAnsiCode() + "}   ")
                    .append(ANSI_BG)
                    .append(borderColor.getAnsiCode())
                    .append("}   ")
                    .append(BG_DEFAULT);
        }
        return editedLine.toString();
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

    private String editLine(String line, Color stringColor, Color bgColor) {
        StringBuilder editedLine = new StringBuilder(line);
        if (stringColor != null) {
            editedLine.insert(0, ANSI_COLOR + stringColor.getAnsiCode() + "}").append(COLOR_DEFAULT);
        }
        if (isBgColored) {
            editedLine.insert(0, ANSI_BG + bgColor.getAnsiCode() + "}");
        }
        if (isBold) {
            editedLine.insert(0, ANSI_STYLE_BOLD).append(STYLE_DEFAULT);
        }
        if (borderColor != null) {
            editedLine.insert(0, ANSI_BG + borderColor.getAnsiCode() + "}   ");
            editedLine.append(ANSI_BG).append(borderColor.getAnsiCode()).append("}   ");
        }
        if (isBgColored || borderColor != null) {
            editedLine.append(BG_DEFAULT);
        }
        return editedLine.toString();
    }

    private String editPreviewLine(String line, Color textColor, Color bgColor) {
        StringBuilder editedLine = new StringBuilder(line);
        if (isBgColored) {
            editedLine.insert(0, colorUtil.backgroundColor(bgColor));
        }
        if (stringColor != null && isBold) {
            editedLine = new StringBuilder(colorUtil.stringColor(editedLine.toString(), textColor, "bold"));
        } else if (stringColor != null) {
            editedLine = new StringBuilder(colorUtil.stringColor(editedLine.toString(), textColor));
        } else if (isBold) {
            editedLine = new StringBuilder(colorUtil.stringStyle(editedLine.toString(), "bold"));
        }
        if (borderColor != null) {
            editedLine.insert(0, colorUtil.backgroundColor(borderColor, 3)).append(colorUtil.backgroundColor(borderColor, 3));
        }
        if (textColor != null) {
            editedLine.append(" : ").append(textColor.getAnsiCode()).append("  R: ").append(textColor.getRed()).append("  G: ").append(textColor.getGreen()).append("  B: ").append(textColor.getBlue());
        }
        return editedLine.toString();
    }

    private Color[] createColorGradient(Color startColor, Color endColor, int steps) {
        Color[] colorGradient = new Color[steps];
        colorGradient[0] = startColor;
        colorGradient[steps - 1] = endColor;
        if (startColor != null && endColor != null) {
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
                colorGradient[i] = new Color(r, g, b, true); // will produce lesser resolution gradients as ANSI
            }
        }
        return colorGradient;
    }

}
