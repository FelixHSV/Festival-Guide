package de.hsba.bi.FestivalGuide.festival;

import java.util.Comparator;

public class FestivalNameComparator implements Comparator<Festival> {

    public int compare(Festival f1, Festival f2) {
        return f1.getName().compareTo(f2.getName());
    }
}