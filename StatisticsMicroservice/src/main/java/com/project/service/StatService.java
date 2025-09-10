package com.project.service;

import com.project.model.Stat;
import java.util.List;

public interface StatService {
    List<Stat> getStat(String jwt,String email);
}
