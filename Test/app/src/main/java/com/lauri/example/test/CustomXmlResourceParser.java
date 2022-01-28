package com.lauri.example.test;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class CustomXmlResourceParser {

    public static List<String> parse(Resources resources, int resourceID) {

        // TODO: A custom parser needs to be created for each unique XML resource file.

        List<String> objList = new ArrayList<>();
        XmlResourceParser parser = resources.getXml(resourceID);

        try {
            parser.next();
            int eventType = parser.getEventType();
            String tagName;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    if (tagName.equalsIgnoreCase("pooled_obj")) {
                        String attrValue = parser.getAttributeValue(null, "name");
                        objList.add(attrValue);
                    }
                }
                else if (eventType == XmlPullParser.TEXT) {
                    String text = parser.getText();
                    objList.add(text);
                }
                eventType = parser.next();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return objList;
    }

    public static void parseNameParts(Resources resources,
                                      int resourceID,
                                      List<List<String>> pools,
                                      List<String> poolAll) {
        XmlResourceParser parser = resources.getXml(resourceID);

        try {
            parser.next();
            int eventType = parser.getEventType();
            String tagName = "_";
            int currentId = 0;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    String strId = parser.getAttributeValue(null, "id");
                    if (strId != null && strId.length() > 0) {
                        try {
                            currentId = Integer.parseInt(strId);
                        }
                        catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if (eventType == XmlPullParser.TEXT
                         && tagName.equalsIgnoreCase("name_part")) {
                    String text = parser.getText();
                    if (text != null && text.length() > 0) {
                        pools.get(currentId).add(text);
                        poolAll.add(text);
                    }
                }
                eventType = parser.next();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
