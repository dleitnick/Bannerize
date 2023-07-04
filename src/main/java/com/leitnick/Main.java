package com.leitnick;

import com.github.lalyos.jfiglet.FigletFont;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        Banner banner = new Banner(new Color(93), true, new Color(0), true);
//        banner.addGradient(new Color(123));
//        banner.addBorder(new Color(105));
//        String[] bannerArr = banner.makeBanner("Tenmo");
//        System.out.println();
//        for (String s : bannerArr) {
//            System.out.println(s);
//        }
//        System.out.println();
//        String[] bannerPreview = banner.makePreviewBanner("Tenmo");
//        for (String s : bannerPreview) {
//            System.out.println(s);
//        }
//        Color purple = new Color(50);
//        System.out.println("Red : " + purple.getRed());
//        System.out.println("Green : " + purple.getGreen());
//        System.out.println("Blue : " + purple.getBlue());
//        Color bluey = new Color(75, 105, 220);
//        System.out.println("Bluey");
//        System.out.println("Red : " + bluey.getRed());
//        System.out.println("Green : " + bluey.getGreen());
//        System.out.println("Blue : " + bluey.getBlue());
//        Color blueyAnsi = new Color(bluey);
//        System.out.println("Bluey");
//        System.out.println("Red : " + blueyAnsi.getRed());
//        System.out.println("Green : " + blueyAnsi.getGreen());
//        System.out.println("Blue : " + blueyAnsi.getBlue());
//        System.out.println("ANSI: " + blueyAnsi.getAnsiCode());
//
//        ColorUtil addColor = new ColorUtil();
//        System.out.println(addColor.stringColor("Test of bluey!", bluey, "italic, underline"));
//        System.out.println(addColor.backgroundColor(new Color(16)) + addColor.stringColor("Test of bluey as ANSI!", blueyAnsi) + addColor.backgroundColor(new Color(16), 7));
//        System.out.println(addColor.stringStyle("Italic Bold Underline!!", "italic,bold,underline"));
//
//        Color gray = new Color(78, 78, 78, true);
//        System.out.println(addColor.stringColor("This should be gray 239. Result: " + gray.getAnsiCode(), gray));
//
//        System.out.println(new Color(255));
//
//        System.out.println(new Color(35, 0, 60, true));
//
//        int length = 24;
//        String lengthFormat = "%" + length + "s";
//        String lengthTest = String.format(lengthFormat, "");
//        System.out.println(lengthTest.length());

        Banner titleBanner = new Banner(new Color(226), true, new Color(234), true);
        titleBanner.addBorder(new Color(237));
        titleBanner.addGradient(new Color(196));
        String[] bannerArr = titleBanner.makeBanner("Tenmo");
        for (String s : bannerArr) {
            System.out.println(s);
        }
        String[] bannerView = titleBanner.makePreviewBanner("Pink Floyd");
        for (String s : bannerView) {
            System.out.println(s);
        }
    }
}