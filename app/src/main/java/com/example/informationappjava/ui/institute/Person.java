package com.example.informationappjava.ui.institute;

public class Person {

  String docentName;
  String docentDesc;
  int personImage;

  public Person(String docentName, String docentDesc, int personImage) {
    this.docentName = docentName;
    this.docentDesc = docentDesc;
    this.personImage = personImage;
  }

  public String getDocentName() {
    return docentName;
  }

  public String getDocentDesc() {
    return docentDesc;
  }

  public int getPersonImage() {
    return personImage;
  }
}
