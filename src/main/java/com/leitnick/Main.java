package com.leitnick;

import com.github.lalyos.jfiglet.FigletFont;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Banner banner = new Banner(54, true, 0, true);
        Banner banner2 = new Banner(54);
        String[] bannerArr = banner.makeBanner("TEST");
        System.out.println();
        for (String s : bannerArr) {
            System.out.println(s);
        }
        System.out.println();
        String[] bannerPreview = banner.makePreviewBanner("Test", new Color(121), new Color(127));
        for (String s : bannerPreview) {
            System.out.println(s);
        }
//        Color purple = new Color(234);
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
    }
}