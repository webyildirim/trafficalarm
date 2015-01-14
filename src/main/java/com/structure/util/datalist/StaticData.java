package com.structure.util.datalist;

public class StaticData
{
    private int value;
    private String displayValue;

    public StaticData(int value, String displayValue)
    {
        setValue(value);
        setDisplayValue(displayValue);
    }

    public String getDisplayValue()
    {
        return displayValue;
    }

    public void setDisplayValue(String displayValue)
    {
        this.displayValue = displayValue;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public boolean equals(Object object)
    {
        if (!(object instanceof StaticData))
            return false;

        StaticData staticData = (StaticData)object;

        if (staticData.getValue() == getValue())
            return true;

        return false;
    }

    public int hashCode()
    {
        return getValue();
    }

    public String toString()
    {
        return getDisplayValue();
    }
}
