package chanceCubes.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.List;
import java.util.stream.Collectors;

public class RomanNumerals {

    private static final BiMap<Integer, String> romanNumerals = ImmutableBiMap.<Integer, String>builder().put(1000, "M")
            .put(900, "LM").put(500, "D").put(400, "CD").put(100, "C").put(90, "XC").put(50, "L").put(40, "XL")
            .put(10, "X").put(5, "V").put(4, "IV").put(1, "I").build();

    private RomanNumerals() {

    }

    public static int toInt(String roman) {
        if (roman.length() == 0)
            throw new NumberFormatException("Roman numeral cannot be empty.");

        roman = roman.toUpperCase();
        int i = 0;
        int num = 0;
        while (i < roman.length()) {
            String letter = new String(new char[]{roman.charAt(i)});
            BiMap<String, Integer> inverseRomanNumerals = romanNumerals.inverse();

            int number = inverseRomanNumerals.getOrDefault(letter, 0);
            if (number <= 0)
                throw new NumberFormatException("Invalid character in Roman numeral: " + letter);

            i++;
            if (i == roman.length())
                num += number;
            else {
                int nextNumber = inverseRomanNumerals.getOrDefault(new String(new char[]{roman.charAt(i)}), 0);
                if (nextNumber <= 0)
                    throw new NumberFormatException("Invalid character in Roman numeral: " + letter);

                if (nextNumber > number) {
                    num += (nextNumber - number);
                    i++;
                }
                else
                    num += number;
            }
        }

        if (num > 3999)
            throw new NumberFormatException("Roman numeral must have a value of 3999 or less");

        return num;
    }

    public static String toString(int number) {
        if (number < 1 || number > 3999)
            throw new NumberFormatException("Roman numeral must have a value of between 1 and 3999");

        StringBuilder sb = new StringBuilder();
        List<Integer> numbers = romanNumerals.keySet().stream().collect(Collectors.toList());
        List<String> letters = romanNumerals.values().stream().collect(Collectors.toList());
        for (int i = 0; i < numbers.size(); i++) {
            while (number >= numbers.get(i)) {
                sb.append(letters.get(i));
                number -= numbers.get(i);
            }
        }

        return sb.toString();
    }
}
