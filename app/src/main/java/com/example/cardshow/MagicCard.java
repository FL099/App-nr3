package com.example.cardshow;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MagicCard implements Parcelable {
    private String name;
    private String type;
    private String rarity;
    private String colors ="-";  //anscheinend nicht immer vorhanden

    public MagicCard(String name, String type, String rarity, String colors) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.colors = colors;
    }

    public MagicCard(JSONObject result) {
        try{
            this.name = result.getString("name");
            this.type = result.getString("type");
            this.rarity = result.getString("rarity");
            if (result.getJSONArray("colors") != null){
                int len = result.getJSONArray("colors").length();
                for (int i  = 0; i< len; i++){
                    if (i == 0){
                        this.colors = "";
                    }
                    else {
                        this.colors += ", ";
                    }
                    this.colors += result.getJSONArray("colors").getString(i);
                }

            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    protected MagicCard(Parcel in) {
        name = in.readString();
        type = in.readString();
        rarity = in.readString();
        colors = in.readString();
    }

    public static final Creator<MagicCard> CREATOR = new Creator<MagicCard>() {
        @Override
        public MagicCard createFromParcel(Parcel in) {
            return new MagicCard(in);
        }

        @Override
        public MagicCard[] newArray(int size) {
            return new MagicCard[size];
        }
    };

    public String getCardData(){
        if (colors == "-")
            return name + " \n-Type: " + type + "\n-rarity: " + rarity + "\n\n";
        else
            return name + " |" + colors + "| \n-Type: " + type + "\n-rarity: " + rarity + "\n\n";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(rarity);
        dest.writeString(type);
        dest.writeString(colors);
    }
}
