package com.sports.footballstand.services;

import java.util.List;

public interface IOperations {

    <T> List<T> fetch(String name);
}
