package com.example.informationappjava.ui.institute;

import androidx.cardview.widget.CardView;

public class Person {

  String docentName;
  String docentDesc;
  int personImage;
  CardView cardView;

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

  public CardView getCardView() {
    return cardView;
  }
}
