package game;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public interface GetWordService {
    final String wordBankURL = "https://agilec.cs.uh.edu/words";

    private static String getRawSet() throws IOException {
        URL url = new URL(wordBankURL);
        Scanner wordService = new Scanner(url.openStream());

        String rawWordSet = wordService.nextLine();
        wordService.close();

        return rawWordSet;
    }

    private static String[] cleanUpInput(String unformatted) {
        String onlyCommas = unformatted.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
        
        return onlyCommas.split(",");
    }

    public static List<String> getWordSet() throws IOException {
        String unformattedWordBank = getRawSet();
        String[] formattedWordBank = cleanUpInput(unformattedWordBank);

        return Arrays.asList(formattedWordBank);
    }
}
