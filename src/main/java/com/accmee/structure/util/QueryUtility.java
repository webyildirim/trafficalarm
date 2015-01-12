package com.accmee.structure.util;

import java.util.Date;

public class QueryUtility
{
    public static String betweenDateQueryString(Date fromDate, Date toDate)
    {
        return " BETWEEN TO_DATE ('" + DateUtility.convertDateToString(fromDate, "yyyy-MM-dd HH:mm:ss") + "','yyyy-MM-dd HH24:mi:ss') AND TO_DATE('" +
            DateUtility.convertDateToString(DateUtility.addMilliSecondsToDate(toDate, -1000), "yyyy-MM-dd HH:mm:ss") + "','yyyy-MM-dd HH24:mi:ss')";

    }
}
