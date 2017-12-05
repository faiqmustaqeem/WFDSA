package com.example.shariqkhan.wfdsa.custom;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class ResourcesGroup extends ExpandableGroup<ResourcesSubItems> {
    String id;
    public ResourcesGroup(String id, String title, List<ResourcesSubItems> items) {
        super(title, items);
        this.id = id;
    }
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourcesGroup)) return false;

        ResourcesGroup genre = (ResourcesGroup) o;

        return getId().equals(genre.getId());

    }

}