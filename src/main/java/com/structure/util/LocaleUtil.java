package com.structure.util;

import java.util.Locale;


public enum LocaleUtil
{
    TR(0, new Locale("tr", "TR"), "Turkce"),
    EN(1, new Locale("en", "US"), "English");

    private int index;
    private Locale locale;
    private String description;

    LocaleUtil(int index, Locale locale, String description)
    {
        this.index = index;
        this.locale = locale;
        this.description = description;
    }

    public int getIndex()
    {
        return index;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public static Locale getLocale(int index)
    {
        for (LocaleUtil locales : LocaleUtil.values())
        {
            if (locales.getIndex() == index)
                return locales.getLocale();
        }
        return null;
    }

    public static LocaleUtil getEnum(Locale locale)
    {
        for (LocaleUtil locales : LocaleUtil.values())
        {
            if (locales.getLocale().equals(locale))
                return locales;
        }
        return null;
    }

    public static LocaleUtil getDefaultEnum(Locale locale)
    {
        for (LocaleUtil locales : LocaleUtil.values())
        {
            if (locales.getLocale().equals(locale))
                return locales;
        }
        return EN;
    }

    public String toString()
    {
        return description;
    }

}
