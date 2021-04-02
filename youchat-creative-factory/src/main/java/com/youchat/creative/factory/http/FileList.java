package com.youchat.creative.factory.http;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileList {
    private List<String> file = new ArrayList<>();
    private List<String> directory = new ArrayList<>();

}
