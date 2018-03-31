package com.test;

class Project {

 public String getGreeting() {
        return "Hello, MNT Lab!";
    }
 /*infinity*/

  public static void test() {
    System.out.println("test");
  }

  public static void main(String[] args) {
    System.out.println(new Project().getGreeting());
  
  }
}
