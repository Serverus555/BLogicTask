package me.serverus.blogictask.utils;

import me.serverus.blogictask.model.Assignment;

import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.adapters.XmlAdapter;

@Provider
public class ExecuteStatusAdapter extends XmlAdapter<String, Assignment.ExecuteStatus> {
    @Override
    public Assignment.ExecuteStatus unmarshal(String v) throws Exception {
        return Assignment.ExecuteStatus.values()[Integer.parseInt(v)];
    }

    @Override
    public String marshal(Assignment.ExecuteStatus v) throws Exception {
        return String.valueOf(v.ordinal());
    }
}
