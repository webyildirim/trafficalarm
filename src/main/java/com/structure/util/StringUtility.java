package com.structure.util;

import java.util.Locale;


public class StringUtility
{
    private static char[] turkish = { 'Ç', 'ç', 'Ð', 'ð', 'Ý', 'ý', 'Ö', 'ö', 'Þ', 'þ', 'Ü', 'ü' };
    private static char[] english = { 'C', 'c', 'G', 'g', 'I', 'i', 'O', 'o', 'S', 's', 'U', 'u' };

    public static int FORMAT_ALIGNMENT_RIGHT = 0;
    public static int FORMAT_ALIGNMENT_LEFT = 1;

    public static final Locale DEFAULT_LOCALE = LocaleUtil.TR.getLocale();

    public static boolean isNullOrEmpty(String stringValue)
    {
        if (stringValue == null)
            return true;

        if (stringValue.trim().length() == 0)
            return true;

        return false;
    }

    public static String findSegment(String text, char searchTill)
    {
        String part = text;

        for (int i = 0; i < text.length(); i++)
        {
            if (text.charAt(i) == searchTill)
            {
                part = text.substring(0, i);
                break;
            }
        }

        return part;
    }

    /**
     * @description find specified position in a delimited string; use indexes starting with 0
     */
    public static String findIndexedDataForDelimitedString(String delimitedString, char separatorChar, int index)
    {
        delimitedString = separatorChar + delimitedString + separatorChar;

        byte bytes[] = delimitedString.getBytes();

        byte separator = (byte)separatorChar;

        // number of found separators
        int counter = -1;
        // last index of separator; -1 if not found
        int separatorIndex = -1;

        StringBuilder data = new StringBuilder();

        for (int i = 0; i < bytes.length; i++)
        {
            if (bytes[i] == separator)
            {
                // found separator
                if (counter == index)
                {
                    // found matching index
                    data.append(delimitedString.substring(separatorIndex + 1, i));
                    break;
                } else
                {
                    separatorIndex = i;
                    counter++;
                }
            }
        }

        return data.toString();
    }

    /**
     * @description used to manipulate delimiters on a delimited string
     */
    public static String fixDelimiters(String delimitedString, String separator, String prefix, String postfix)
    {
        return fixDelimiters(delimitedString, separator, prefix, postfix, false);
    }

    public static String fixDelimiters(String delimitedString, String separator, String prefix, String postfix, boolean useSeparator)
    {
        StringBuilder data = new StringBuilder();
        delimitedString = separator + delimitedString + separator;

        if (!isNullOrEmpty(delimitedString))
        {
            String[] tempList = delimitedString.split(separator);
            for (int i = 0; i < tempList.length; i++)
            {
                String str2 = tempList[i];
                if (useSeparator)
                    data.append(separator);
                if (!isNullOrEmpty(str2) && str2.length() > 0)
                {
                    data.append(prefix);
                    data.append(str2);
                    data.append(postfix);
                }
                if (useSeparator)
                    data.append(separator);
            }
        }

        return data.toString();
    }

    public static String numberToEnglishString(long n)
    {
        String[] ones =
        { "", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN",
          "SEVENTEEN", "EIGHTEEN", "NINETEEN" };
        String[] tens = { "", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY" };

        String[] groups = { "", "THOUSAND", "MILLION", "BILLION", "TRILLION", "QUADRILLION", "QUINTILLION" };

        StringBuilder strBuilder = new StringBuilder();

        for (int i = groups.length - 1; i >= 0; i--)
        {
            long cutoff = (long)Math.pow((double)10, (double)(i * 3));

            if (n >= cutoff)
            {
                int thisPart = (int)(n / cutoff);

                if (thisPart >= 100)
                {
                    strBuilder.append(ones[thisPart / 100]).append("HUNDRED");
                    thisPart = thisPart % 100;
                }
                if (thisPart >= 20)
                {
                    strBuilder.append(tens[(thisPart / 10) - 1]);
                    thisPart = thisPart % 10;
                }
                if (thisPart >= 1)
                {
                    strBuilder.append(ones[thisPart]);
                }

                strBuilder.append(groups[i]);
                n = n % cutoff;
            }
        }

        return (strBuilder.length() == 0) ? "ZERO" : strBuilder.toString();
    }

    public static String numberToTurkishString(long n)
    {
        String[] ones = { "", "BiR", "iKi", "UC", "DORT", "BES", "ALTI", "YEDi", "SEKiZ", "DOKUZ" };
        String[] tens = { "ON", "YiRMi", "OTUZ", "KIRK", "ELLi", "ALTMIS", "YETMiS", "SEKSEN", "DOKSAN" };
        String[] groups = { "", "BiN", "MiLYON", "MiLYAR", "TRiLYON", "KATRiLYON", "KENTiLYON" };

        StringBuilder strBuilder = new StringBuilder();

        for (int i = groups.length - 1; i >= 0; i--)
        {
            long cutoff = (long)Math.pow((double)10, (double)(i * 3));

            if (n >= cutoff)
            {
                int thisPart = (int)(n / cutoff);

                if (thisPart >= 100)
                {
                    if ((thisPart / 100) > 1)
                        strBuilder.append(ones[thisPart / 100]).append("YUZ");
                    else if ((thisPart / 100) == 1)
                        strBuilder.append("YUZ");
                    thisPart = thisPart % 100;
                    if ((thisPart == 1 && cutoff == 1000))
                        strBuilder.append(ones[thisPart]);
                }
                if (thisPart >= 10)
                {
                    strBuilder.append(tens[(thisPart / 10) - 1]);
                    thisPart = thisPart % 10;
                    if ((thisPart == 1 && cutoff == 1000))
                        strBuilder.append(ones[thisPart]);
                }
                if ((thisPart >= 1) && (!(thisPart == 1 && cutoff == 1000)))
                {
                    strBuilder.append(ones[thisPart]);
                }

                strBuilder.append(groups[i]);
                n = n % cutoff;
            }
        }

        return (strBuilder.length() == 0) ? "SIFIR" : strBuilder.toString();
    }

    public static String removeIfLastCharacter(String str, String chr)
    {
        if (str.endsWith(chr))
            return str.substring(0, str.length() - 1);
        else
            return str;
    }

    public static String turkishToEnglishCharacterReplacer(String str)
    {
        for (int i = 0; i < turkish.length; i++)
            str = str.replace(turkish[i], english[i]);

        return str;
    }

    public static boolean hasNumericChar(String str)
    {
        String pattern = "(?=.*\\d).{1,}";

        return str.matches(pattern);
    }

    public static void appendWithDelimiter(StringBuilder builder, String text, String delimiter)
    {
        if (builder.length() > 0)
            builder.append(delimiter);

        builder.append(text);
    }

    public static String formatWithMask(String mask, String text)
    {
        return formatWithMask(mask, text, FORMAT_ALIGNMENT_RIGHT);
    }

    public static String formatWithMask(String mask, String text, int alignment)
    {
        char[] textArray = text.toCharArray();
        char[] maskArray = mask.toCharArray();

        for (int i = 0; i < maskArray.length; i++)
        {
            if (alignment == FORMAT_ALIGNMENT_RIGHT)
            {
                if (i < textArray.length)
                    maskArray[maskArray.length - 1 - i] = textArray[textArray.length - 1 - i];
            } else
            {
                if (i < textArray.length)
                    maskArray[i] = textArray[i];
            }
        }

        return String.valueOf(maskArray);
    }

    public static final String unCapitalize(String originalStr, int splitBeginIndex, int splitEndIndex, Locale locale)
    {
        final String result;
        if (isNullOrEmpty(originalStr))
        {
            result = originalStr;
        } else
        {
            final String first = originalStr.substring(splitBeginIndex, splitEndIndex).toLowerCase(locale);
            final String rest = originalStr.substring(splitBeginIndex + 1);
            final StringBuilder uncapStr = new StringBuilder(first).append(rest);
            result = uncapStr.toString();
        }
        return result;
    }

    public static final String unCapitalize(String originalStr)
    {
        return unCapitalize(originalStr, 0, originalStr.length(), DEFAULT_LOCALE);
    }

    public static final String capitalize(String originalStr, int splitBeginIndex, int splitEndIndex, Locale locale)
    {
        final String result;
        if (isNullOrEmpty(originalStr))
        {
            result = originalStr;
        } else
        {
            final String first = originalStr.substring(splitBeginIndex, splitEndIndex).toUpperCase(locale);
            final String rest = originalStr.substring(splitBeginIndex + 1);
            final StringBuilder uncapStr = new StringBuilder(first).append(rest);
            result = uncapStr.toString();
        }
        return result;
    }

    public static final String capitalize(String originalStr)
    {
        return unCapitalize(originalStr, 0, originalStr.length(), DEFAULT_LOCALE);
    }

}
