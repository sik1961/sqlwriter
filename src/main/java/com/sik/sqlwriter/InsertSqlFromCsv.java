package com.sik.sqlwriter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InsertSqlFromCsv {

    private static final String filePath = "/Users/sik/Java/temp/ntp.csv";
    private static final String INSERT_FMT = "INSERT INTO PNET.NETWK_TRKG_PNT (ID,TEXT,WAYPT_BCDE,ACTN,DIRN,CTNR_TYP,CNTRYS,EXT_TRKG_PNT) " +
            "VALUES (%s,'%s','%s','%s','%s','%s','%s','%s') ;";
    private static final int WAYPT_BCDE = 0;
    private static final int EXT_TRKG_PNT = 1;
    private static final int TEXT = 2;
    private static final int ACTN = 3;
    private static final int DIRN = 6;
    private static final int CTNR_TYP = 7;
    private static final int CNTRYS = 8;

    private static final String REGEX = "[,]{1}";

    public static void main(String[] args) {

        List<String> csvLines = getCsvLines(filePath);

        int id = 0;
        for (String l:csvLines) {
            //System.out.println(l);
            String[] elements = l.split(REGEX);
            if (elements.length >= 9) {
                System.out.println(csvToSql(id,elements));
            } else {
                System.out.println(" ***** Could not write sql for: " + l);
                for (String x:l.split(REGEX)) {
                    System.out.println(" ***** elm: " + x);
                }
            }
            id++;

        }


    }

    private static List<String> getCsvLines(String filePath) {
        List<String> csvLines = new ArrayList<String>();

        try {

            BufferedReader b = new BufferedReader(new FileReader(filePath));

            String readLine = "";

            while ((readLine = b.readLine()) != null) {
                String fixedLine = readLine.replace(",,", ", ,");
                csvLines.add(fixedLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return csvLines;

    }

    private static String csvToSql(Integer id, String[] s) {

        return String.format(INSERT_FMT,
                id.toString(),
                s[TEXT].trim(),
                s[WAYPT_BCDE].trim(),
                s[ACTN].trim(),
                s[DIRN].trim(),
                s[CTNR_TYP].trim(),
                s[CNTRYS].trim(),
                s[EXT_TRKG_PNT].trim());

    }

}

