package com.leitnick;

import java.util.Scanner;

public class Display {

    private static final String titleName = "Bannerize!";

    private static final String[] fonts = {
            "3-d.flf", "3d.flf", "3D-ASCII.flf", "4max.flf", "abraxas.flf", "alligator.flf", "Alpha.flf",
            "alphabet.flf", "amcaaa01.flf", "amcslder.flf", "ANSI_Shadow.flf", "asc_____.flf", "banner3.flf",
            "basic.flf", "Big_Money-ne.flf", "Big_Money-nw.flf", "Big_Money-se.flf", "Big_Money-sw.flf", "block.flf",
            "Bloody.flf", "bolger.flf", "broadway.flf", "calgphy2.flf", "caligraphy.flf", "Calvin_S.flf",
            "char3___.flf", "chunky.flf", "clb8x10.flf", "colossal.flf", "defleppard.flf", "Delta_Corps_Priest_1.flf",
            "doh.flf", "doom.flf", "DOS_Rebel.flf", "Electronic.flf", "flowerpower.flf", "fraktur.flf", "georgi16.flf",
            "graffiti.flf", "hollywood.flf", "kban.flf", "Larry_3D_2.flf", "lean.flf", "letters.flf", "maxiwi.flf",
            "merlin1.flf", "nancyj-improved.flf", "nancyj-underlined.flf", "nscript.flf", "ogre.flf", "reverse.flf",
            "rounded.flf", "s-relief.flf", "shadow.flf", "shimrod.flf", "slant.flf", "Slant_Relief.flf", "standard.flf",
            "sub-zero.flf", "swan.flf", "univers.flf"};
    private final int[][] colorPresets = {{20, 233, 26, 44}, {226, 234, 0, 196}};

    private final Scanner input = new Scanner(System.in);
    private final ColorUtil colorUtil = new ColorUtil();

    public void run() {
        title();
    }

    private void title() {
        Banner title = new Banner(new Color(colorPresets[0][0]), true, new Color(colorPresets[0][1]), true);
        title.addBorder(new Color(colorPresets[0][2]));
        title.addGradient(new Color(colorPresets[0][3]));
        title.setFont("univers.flf");
        printBanner(title, titleName);
        mainMenu();
    }

    private void mainMenu() {
        System.out.println("1. Enter a message for your banner");
        System.out.println("2. Add color and borders");
        System.out.println("3. Preview fonts");
        System.out.println("4. Preview current configuration");
        System.out.println("5. Create banner.txt");
        System.out.println("0. Exit");
        int[] menuOptions = {1, 2, 3, 4, 5, 0};
        int choice = promptForInteger(colorUtil.stringStyle("Choose an option --> ", "bold"), menuOptions);
        if (choice == 1)      messageEntry();
        else if (choice == 2) customize();
        else if (choice == 3) previewFonts();
        else if (choice == 4) preview();
        else if (choice == 5) createBannerFile();
//        else if (choice != 0) {
//            printErrorMessage(String.format("%d is not an option. Please choose one of these options.", choice));
//            pauseOutput();
//            mainMenu();
//        }
    }

    private void messageEntry() {
        System.out.println("Message Entry!");
    }

    private void customize() {
        System.out.println("Customize!");
    }

    private void previewFonts() {
        System.out.println("Preview Fonts!");
    }

    private void preview() {
        System.out.println("Preview!");
    }

    private void createBannerFile() {
        System.out.println("Create File!");
    }

    private void printBanner(Banner banner, String message) {
        String[] bannerView = banner.makePreviewBanner(message);
            for (String s : bannerView) {
                System.out.println(s);
            }
        System.out.println();
    }

    private String promptForString(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    private Integer promptForInteger(String prompt) {
        return promptForInteger(prompt, null);
    }

    private Integer promptForInteger(String prompt, int[] acceptableValues) {
        Integer result = null;
        String entry = promptForString(prompt);
        while (!entry.isBlank() && result == null) {
            try {
                result = Integer.parseInt(entry);
                if (acceptableValues != null) {
                    boolean resultFound = false;
                    for (int acceptableValue : acceptableValues) {
                        if (result == acceptableValue) {
                            resultFound = true;
                            break;
                        }
                    }
                    if (!resultFound) throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                printErrorMessage("Enter a valid number, please");
                result = promptForInteger(prompt, acceptableValues);
            }
        }
        return result;
    }

    private void printErrorMessage(String message) {
        System.out.println(colorUtil.stringColor("*** " + message + " ***", new Color(255,107, 104)));
    }

    private void pauseOutput() {
        System.out.print("(Press return to continue...)");
        input.nextLine();
        System.out.println();
    }

//    public static void main(String[] args) {
//        Banner titleBanner = new Banner(new Color(226), true, new Color(234), true);
////        titleBanner.addBorder(new Color(237));
////        titleBanner.addGradient(new Color(196));
//        for (String font : fonts) {
//            titleBanner.setFont(font);
////            String[] bannerArr = titleBanner.makeBanner("Tenmo");
////            for (String s : bannerArr) {
////                System.out.println(s);
////            }
//            String[] bannerView = titleBanner.makePreviewBanner("Pink Floyd");
//            System.out.println(font);
//            for (String s : bannerView) {
//                System.out.println(s);
//            }
//        }
//    }
}
