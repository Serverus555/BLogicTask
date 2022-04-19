package me.serverus.blogictask.dto;

import me.serverus.blogictask.utils.Filter;
import me.serverus.blogictask.utils.Sort;

import java.util.List;

public class GetEntitiesRequest {

    public List<Filter> filters;
    public Sort sort;
    public int from = 0;
    public int count = 0;
}
