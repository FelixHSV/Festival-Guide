package de.hsba.bi.FestivalGuide.band;

import java.util.Comparator;

public class BandNameComparator implements Comparator<Band> {

    public int compare(Band b1, Band b2) {
        return b1.getName().compareTo(b2.getName());
    }
}
