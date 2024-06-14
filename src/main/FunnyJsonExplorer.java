package main;

import composition.Container;
import factory.Factory;
import factory.TreeStyleFactory;
import factory.RectangleStyleFactory;
import icon.*;
import parser.JsonFileParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FunnyJsonExplorer {
    private Factory styleFactory;
    private IconFactory iconFactory;

    public FunnyJsonExplorer(Factory styleFactory, IconFactory iconFactory) {
        this.styleFactory = styleFactory;
        this.iconFactory = iconFactory;
    }

    public void load(String jsonFile) {
        JsonFileParser parser = new JsonFileParser();
        Container root = parser.parse(jsonFile, styleFactory, iconFactory);
        draw(root);
    }

    public void draw(Container root) {
        styleFactory.drawContainer(root, 0, true, new ArrayList<Boolean>());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter command (fje -f <json file> -s <style> -i <icon family> or exit):");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                break;
            }

            String[] cmdArgs = command.split(" ");
            if (cmdArgs.length < 7 || !cmdArgs[0].equals("fje") || !cmdArgs[1].equals("-f") || !cmdArgs[3].equals("-s") || !cmdArgs[5].equals("-i")) {
                System.out.println("Usage: fje -f <json file> -s <style> -i <icon family>");
                continue;
            }

            String jsonFile = cmdArgs[2];
            String style = cmdArgs[4];
            String iconFamily = cmdArgs[6];

            Factory styleFactory;
            switch (style) {
                case "tree":
                    styleFactory = new TreeStyleFactory();
                    break;
                case "rectangle":
                default:
                    styleFactory = new RectangleStyleFactory();
                    break;
            }

            IconFactory iconFactory;
            switch (iconFamily) {
                case "chess":
                    iconFactory = new ChessIconFactory();
                    break;
                case "pokerface":
                    iconFactory = new PokerFaceIconFactory();
                    break;
                default:
                case "simple":
                    iconFactory = new SimpleIconFactory();
                    break;
            }

            FunnyJsonExplorer explorer = new FunnyJsonExplorer(styleFactory, iconFactory);
            explorer.load(jsonFile);
        }
        scanner.close();
    }
}
