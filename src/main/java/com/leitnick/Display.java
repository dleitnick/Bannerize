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
    private final Banner userBanner = new Banner();

    public void run() {
        title();
    }

    private void title() {
        Banner title = new Banner(new Color(colorPresets[0][0]), true, new Color(colorPresets[0][1]), true);
        title.addBorder(new Color(colorPresets[0][2]));
        title.addGradient(new Color(colorPresets[0][3]));
        title.setFont("univers.flf");
        title.setMessage(titleName);
        printBanner(title);
        mainMenu();
    }

    private void mainMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println();
            System.out.println("1. Enter a message for your banner");
            System.out.println("2. Add color and borders");
            System.out.println("3. Choose and preview fonts");
            System.out.println("4. Preview current configuration");
            System.out.println("5. Create banner.txt");
            System.out.println("0. Exit");
            int[] menuOptions = {1, 2, 3, 4, 5, 0};
            choice = promptForInteger(colorUtil.stringStyle("Choose an option --> ", "bold"), menuOptions);
            System.out.println();
            if (choice == 1) messageEntry();
            else if (choice == 2) customize();
            else if (choice == 3) previewFonts();
            else if (choice == 4) preview();
            else if (choice == 5) createBannerFile();
        }
    }

    private void messageEntry() {
        String message = promptForString("Enter the message of your banner: ");
        userBanner.setMessage(message);
    }

    //TODO: Add ways to remove customization
    private void customize() {
        System.out.println("Customize!");
        int choice = -1;
        while (choice != 0) {
            System.out.println();
            System.out.println("1. Change text color");
            System.out.println("2. Change background color");
            System.out.println("3. Add border and color");
            System.out.println("4. Make font bold");
            System.out.println("5. Add gradient to text color");
            System.out.println("0. Exit");
            int[] menuOptions = {1, 2, 3, 4, 5, 0};
            choice = promptForInteger(chooseAnOption(), menuOptions);
            if (choice == 1) changeTextColor();
            else if (choice == 2) changeBackgroundColor();
            else if (choice == 3) addBorder();
            else if (choice == 4) makeFontBold();
            else if (choice == 5) addGradient();
        }
    }

    private void changeTextColor() {
        userBanner.setStringColor(
                new Color(promptForInteger(colorUtil.stringStyle(
                        "Select an ANSI color (0 - 255) --> ", "bold"))));
    }

    private void changeBackgroundColor() {
        userBanner.setBgColor(
                new Color(promptForInteger(colorUtil.stringStyle(
                        "Select an ANSI color (0 - 255) --> ", "bold"))));
        userBanner.setBgColored(true);
    }

    private void addBorder() {
        userBanner.setBorderColor(
                new Color(promptForInteger(colorUtil.stringStyle(
                        "Select an ANSI color (0 - 255) --> ", "bold"))));
    }

    private void makeFontBold() {
        userBanner.setBold(promptForYesNo(colorUtil.stringStyle(
                "Make font bold? Yes or No --> ", "bold")));
    }

    private void addGradient() {
        userBanner.setGradientEnd(
                new Color(promptForInteger(colorUtil.stringStyle(
                        "Select an ANSI color (0 - 255) --> ", "bold"))));
    }

    private void previewFonts() {
        System.out.println("Preview Fonts!");
        Banner fontBanner = new Banner();
        fontBanner.setMessage(userBanner.getMessage());
        fontBanner.setBgColor(userBanner.getBgColor());
        fontBanner.setStringColor(userBanner.getStringColor());
        fontBanner.setBorderColor(userBanner.getBorderColor());
        fontBanner.setGradientEnd(userBanner.getGradientEnd());
        fontBanner.setBold(userBanner.isBold());
        fontBanner.setBgColored(userBanner.isBgColored());
        for (int i = 0; i < fonts.length; i++) {
            String fontName = colorUtil.backgroundColor(new Color(233)) + colorUtil.stringColor(String.format("⬇️ %d. %-30s  ", i + 1, fonts[i].split("\\.")[0]), new Color(15), "bold");
            for (int j = 0; j < 8; j++) {
                System.out.print(fontName);
            }
            System.out.println("\n");
            fontBanner.setFont(fonts[i]);
            printBanner(fontBanner);
            if ((i + 1) % 3 == 0) {
                int fontChoice = promptForFontChoice("Select a font # or select 0 to exit to main menu. Press return to see more fonts.\n" + chooseAnOption());
                if (fontChoice == 0) break;
            }
        }
    }

    private int promptForFontChoice(String prompt) {
        int choice = -1;
        while (true) {
            choice = promptForInteger(prompt);
            if (choice == -1 || choice == 0) break;
            if (choice < 1 || choice > fonts.length) {
                promptForFontChoice(prompt);
            } else {
                userBanner.setFont(fonts[choice - 1]);
                System.out.println();
            }
            choice = promptForInteger("Press return to see more fonts or select 0 to exit to main menu.\n" + chooseAnOption());
            if (choice == -1 || choice == 0) break;
        }
        return choice;
    }

    private void preview() {
        printBanner(userBanner);
    }

    private void createBannerFile() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("1. Create banner.txt in root");
            System.out.println("2. Create at custom path");
            System.out.println("0. Exit");
            choice = promptForInteger(chooseAnOption());
            if (choice == 1) {
                FileWriter fileWriter = new FileWriter();
                fileWriter.makeBannerTxt(userBanner.makeBanner());
                System.out.println("Banner.txt created!");
                break;
            } else if (choice == 2) {
                String fileLocation =
                        promptForString(boldMessage("Enter the path you'd like to create the banner.txt in: "));
                FileWriter fileWriter = new FileWriter(fileLocation);
                fileWriter.makeBannerTxt(userBanner.makeBanner());
                System.out.println("Banner.txt created!");
                break;
            }
        }
    }

    private void printBanner(Banner banner) {
        String[] bannerView = banner.makePreviewBanner();
            for (String s : bannerView) {
                System.out.println(s);
            }
        System.out.println();
    }

    private String promptForString(String prompt) {
        System.out.print(prompt);
        String result = input.nextLine();
        if (result == null) result = promptForString(prompt);
        return result;
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
        if (result == null) result = -1;
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

    private boolean promptForYesNo(String prompt) {
        while (true) {
            String reply = promptForString(prompt);
            String upperReply = reply.toUpperCase();
            if (upperReply.startsWith("Y")) {
                return true;
            } else if (upperReply.startsWith("N")) {
                return false;
            } else {
                printErrorMessage("Please enter Y or N");
            }
        }
    }

    private String chooseAnOption() {
        return boldMessage("Choose an option --> ");
    }

    private String boldMessage(String message) {
        return colorUtil.stringStyle(message, "bold");
    }

}
