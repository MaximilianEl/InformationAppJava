package com.example.informationappjava.ui.institute;

import androidx.cardview.widget.CardView;

/**
 *
 */
public class Person {

  String docentName;
  String docentDesc;
  int personImage;
  CardView cardView;

  /**
   * @param docentName
   * @param docentDesc
   * @param personImage
   */
  public Person(String docentName, String docentDesc, int personImage) {
    this.docentName = docentName;
    this.docentDesc = docentDesc;
    this.personImage = personImage;
  }

  /**
   * @return
   */
  public String getDocentName() {
    return docentName;
  }

  /**
   * @return
   */
  public String getDocentDesc() {
    return docentDesc;
  }

  /**
   * @return
   */
  public int getPersonImage() {
    return personImage;
  }

  /**
   * @return
   */
  public CardView getCardView() {
    return cardView;
  }
}
