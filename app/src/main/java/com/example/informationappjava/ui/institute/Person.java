package com.example.informationappjava.ui.institute;

import androidx.cardview.widget.CardView;

/**
 * Model Class of Person
 */
public class Person {

  String docentName;
  String docentDesc;
  int personImage;
  CardView cardView;

  /**
   * Constructer of Class Person.
   *
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
   * @return docentName.
   */
  public String getDocentName() {
    return docentName;
  }

  /**
   * @return docentDesc.
   */
  public String getDocentDesc() {
    return docentDesc;
  }

  /**
   * @return personImage.
   */
  public int getPersonImage() {
    return personImage;
  }

  /**
   * @return cardView.
   */
  public CardView getCardView() {
    return cardView;
  }
}
