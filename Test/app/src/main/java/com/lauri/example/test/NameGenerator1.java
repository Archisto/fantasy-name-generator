package com.lauri.example.test;

import java.util.List;

public class NameGenerator1 {

    public String generateFullName(List<List<String>> pools, boolean printPartSeparators) {
        StringBuilder fullName = new StringBuilder(40);
        double rand = Math.random();

        // Generates first names
        int firstNameCount = 1 + (int) (rand * 1.4f);
        int firstNameMainParts;
        boolean includeFirstNameEnd;
        for (int i = 0; i < firstNameCount; i++) {
            rand = Math.random();
            firstNameMainParts = 1 + (int) (rand * 3);
            rand = Math.random();
            includeFirstNameEnd = (rand >= 0.5f);

            boolean acceptSingleLetter = (firstNameCount > 1);
            String name = generateName(firstNameMainParts, includeFirstNameEnd,
                    acceptSingleLetter, printPartSeparators, pools);
            fullName.append(name).append(" ");
        }

        // Generates the last name
        rand = Math.random();
        int lastNameMainParts = 1 + (int) (rand * 3);
        rand = Math.random();
        boolean includeLastNameEnd = (rand >= 0.5f);
        String lastName = generateName(lastNameMainParts, includeLastNameEnd,
                false, printPartSeparators, pools);

        return fullName.append(lastName).toString();
    }

    public String generateName(int mainParts,
                               boolean includeEnd,
                               boolean acceptSingleLetter,
                               boolean printPartSeparators,
                               List<List<String>> pools) {
        StringBuilder name = new StringBuilder(mainParts * 5 + 5);
        boolean capitalizeNextLetter = true;

        if (mainParts > 0) {
            for (int i = 0; i < mainParts; i++) {
                String part = getRandomNamePart(i, mainParts, false, pools);
                if (capitalizeNextLetter) {
                    part = capitalizeFirstLetter(part);
                    capitalizeNextLetter = false;
                }
                name.append(part);

                if (part.equals("-")) {
                    capitalizeNextLetter = true;
                }

                // Adds a separator to make it clear
                // where one part ends and another begins
                if (printPartSeparators) {
                    name.append("|");
                }
            }

            if (includeEnd) {
                String part = getRandomNamePart(0, 0, true, pools);
                if (capitalizeNextLetter) {
                    part = capitalizeFirstLetter(part);
                }
                name.append(part);
            }

            // Handles a single letter name
            if ((name.length() == 1 ||
                    (printPartSeparators && name.length() == 2))) {

                // Adds a main part to the name if
                // single letter names aren't accepted
                if (!acceptSingleLetter) {
                    name.append(getRandomNamePart(1, 2, false, pools));
                }
                // Adds a period to the end of the single letter name
                else {
                    name.append(".");
                }
            }
        }

        return name.toString();
    }

    private String getRandomNamePart(int partIndex,
                                            int mainPartCount,
                                            boolean endPart,
                                            List<List<String>> pools) {
        boolean firstPart = (partIndex == 0);
        boolean lastMainPart = (partIndex == mainPartCount - 1);
        int randNamePartIndex = 0;

        // Sets the used part pool (main or end)
        List<String> usedPool = pools.get(0);
        int totalPoolSize = usedPool.size();
        if (endPart) {
            usedPool = pools.get(2);
            totalPoolSize = usedPool.size();
        } else if (!firstPart) {
            totalPoolSize += pools.get(1).size();
        }

        // Determines whether to use a special character
        boolean specialCharacterUsed = false;
        if (!firstPart && !lastMainPart) {
            randNamePartIndex = (int) (Math.random() * (pools.get(3).size() + 35));
            if (randNamePartIndex < pools.get(3).size()) {
                usedPool = pools.get(3);
                specialCharacterUsed = true;
            }
        }

        if (!specialCharacterUsed) {
            // Gets a random name part index.
            // If the index would be out of the bounds
            // of usedPool, poolMiddle is used instead.
            randNamePartIndex = (int) (Math.random() * totalPoolSize);
            if (!firstPart && !endPart &&
                    randNamePartIndex >= usedPool.size()) {
                randNamePartIndex -= usedPool.size();
                usedPool = pools.get(1);
            }
        }

        return usedPool.get(randNamePartIndex);
    }

    private String capitalizeFirstLetter(String str) {
        if (str.length() >= 1) {
            String firstLetter = str.substring(0, 1).toUpperCase();
            if (str.length() >= 2) {
                str = firstLetter + str.substring(1);
            }
            else {
                str = firstLetter;
            }
        }

        return str;
    }
}
