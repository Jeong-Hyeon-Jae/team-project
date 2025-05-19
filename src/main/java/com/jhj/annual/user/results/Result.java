package com.jhj.annual.user.results;

public interface Result {
    String toString();

    String name();

    default String toStringLower(){
        return this.toString().toLowerCase();
    }
}
