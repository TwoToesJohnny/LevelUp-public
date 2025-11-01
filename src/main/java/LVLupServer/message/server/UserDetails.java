package LVLupServer.message.server;

import LVLupServer.Message;
import LVLupServer.message.server.store.BackgroundItem;
import LVLupServer.message.server.store.BorderItem;

import java.io.Serial;

public class UserDetails extends Message {
    @Serial
    private static final long serialVersionUID = 103L;
    private int userID;
    private String username;
    private String email;
    private String password;
    private int weekly_Score;
    private byte[] profileIcon;

    public BackgroundItem getAppliedBackground() {
        return appliedBackground;
    }

    public void setAppliedBackground(BackgroundItem appliedBackground) {
        this.appliedBackground = appliedBackground;
    }

    private BackgroundItem appliedBackground;

    public BorderItem getAppliedBorderItem() {
        return appliedBorderItem;
    }

    public void setAppliedBorderItem(BorderItem appliedBorderItem) {
        this.appliedBorderItem = appliedBorderItem;
    }

    private BorderItem appliedBorderItem;

    public byte[] getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(byte[] profileIcon) {
        this.profileIcon = profileIcon;
    }

    public int getWeekly_Score() {
        return weekly_Score;
    }

    public void setWeekly_Score(int weekly_Score) {
        this.weekly_Score = weekly_Score;
    }

    private int exp;
    private int gems;
    private boolean isLoggedIn;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    private int lvl;
    public void levelUp(){lvl++;}

    public UserDetails() {}

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getGems() {
        return gems;
    }

    public void setGems(int gems) {
        this.gems = gems;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
    @Override
    public boolean equals(Object obj) {
        UserDetails userDetails = (UserDetails) obj;
        return userDetails.userID == this.userID;
    }

}
