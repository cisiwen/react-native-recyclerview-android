package com.recyclerviewandroid.libs.javascript;

import com.recyclerviewandroid.libs.domain.SectionHeaderStyle;
import com.recyclerviewandroid.libs.domain.TopHeaderItem;

import java.util.List;
import java.util.Map;

public class ReactRecyclerProps {
    public List<ReactSectionDataSource> data;
    public Map<String,String> httpHeaders;
    public SectionHeaderStyle headerStyle;
    public TopHeaderItem topHeaderItem;
}
