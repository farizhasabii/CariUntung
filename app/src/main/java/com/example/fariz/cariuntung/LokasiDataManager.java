package com.example.fariz.cariuntung;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Fariz on 10/29/2018.
 */


public class LokasiDataManager {



    public static final int[] pictures = new int[] { R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background };


    public static final String[] titles = new String[] { "Apple", "Banana",
            "Orange", "Mango", "Papaya", "Pomegranate", "Strawberry",
            "Watermelon" };


    public static final String[] messages = new String[] {
            "Apple color is red.",
            "Banana color is yellow.",
            "Nagpur is famous for orange, thats why it is known as orange city.",
            "Mango is king of fruits.", "Papaya is best for skin.",
            "Pomegranate is most useful for increasing blood level.",
            "Strawberry color is red.", "Watermelon is very useful in summer." };





    public static ArrayList<LokasiItem> getFruitItemList(int[]arGB, String[]arKode, String[]arNama, String[]arJenis) {
        ArrayList<LokasiItem> list = new ArrayList<LokasiItem>();
        for (int i = 0; i < arKode.length ; i++) {
            LokasiItem item = new LokasiItem();
            item.setFruitname(arNama[i]);
            item.setMessage(arJenis[i]+" ("+arKode[i]+")");
           // item.setPictureResId(pictures[i]);
            list.add(item);

        }
        return list;
    }

    public static LokasiItem getRandomItem() {
        LokasiItem item = new LokasiItem();
        int randomValue = new Random().nextInt(titles.length);
        item.setFruitname(titles[randomValue]);
        item.setMessage(messages[randomValue]);
        item.setPictureResId(pictures[randomValue]);
        return item;
    }

}
