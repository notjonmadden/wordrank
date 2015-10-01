package com.jmadden;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage();
            return;
        }

        RankedWord word = new RankedWord(args[0]);

        if (!word.isValid()) {
            String msg = "Cannot get the rank of an invalid word.\n";
            msg += "Valid words are not null and contain only uppercase alphabetic characters.\n";
            msg += "For example, 'ANCILLARY', 'TUSK', and 'PUJN' are all valid, but '', 'Hello', and 'RANK ME' are not.";
            System.err.println(msg);
            return;
        }

        long rank = word.getRank();
        System.out.println(rank);
    }

    public static void printUsage() {
        System.out.println("Usage: java wordrank.jar <word>");
    }
}
