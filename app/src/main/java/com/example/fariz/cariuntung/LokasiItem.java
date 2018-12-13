package com.example.fariz.cariuntung;

import java.io.Serializable;

/**
 * Created by Fariz on 10/29/2018.
 */

public class LokasiItem implements Serializable {

    private String fruitname;
    private String message;
    private int pictureResId;
    private boolean isCheckboxChecked;

    /**
     * @return the pictureResId
     */
    public int getPictureResId() {
        return pictureResId;
    }

    /**
     * @param pictureResId
     *            the pictureResId to set
     */
    public void setPictureResId(int pictureResId) {
        this.pictureResId = pictureResId;
    }

    /**
     *
     * @return fruitname
     */

    public String getFruitname() {
        return fruitname;
    }

    /**
     *
     * @param fruitname
     */
    public void setFruitname(String fruitname) {
        this.fruitname = fruitname;
    }

    /**
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Method to check the check box is checked or not.
     *
     * @return isCheckboxChecked
     */
    public boolean isCheckboxChecked() {
        return isCheckboxChecked;
    }

    /**
     *
     * @param isCheckboxChecked
     */
    public void setCheckboxChecked(boolean isCheckboxChecked) {
        this.isCheckboxChecked = isCheckboxChecked;
    }
}
