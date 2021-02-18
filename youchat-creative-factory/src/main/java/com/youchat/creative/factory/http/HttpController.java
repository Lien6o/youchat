package com.youchat.creative.factory.http;


@FunctionalInterface
public interface HttpController {

    String execute(HttpHeader httpHeader);
}
