package com.recyclerviewandroid.libs.domain;

import com.recyclerviewandroid.libs.javascript.ReactAsset;
import com.recyclerviewandroid.libs.javascript.ReactSectionDataSource;

public class Asset {

    public  TopHeaderItem topHeaderItem;
    public  Image image;
    public  String type;
    public  String group_name;
    public  long group_id;
    public  Double timestamp;
    public  Long modified;

    public  Location location;

    public  boolean selected;
    public  boolean isSelectionMode;

    public ReactSectionDataSource originalSection;

    public ReactAsset originalAsset;
}
