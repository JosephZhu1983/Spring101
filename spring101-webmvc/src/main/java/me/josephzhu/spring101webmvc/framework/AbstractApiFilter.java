package me.josephzhu.spring101webmvc.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class AbstractApiFilter {
    public void preAction(HttpServletRequest request, HttpServletResponse response) {}
    public void postAction(HttpServletRequest request, HttpServletResponse response) {}
}
