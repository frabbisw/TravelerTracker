package com.example.frabbi.meem;

/**
 * Created by imm on 4/25/2017.
 */

public interface ClickCallback {
    void onAcceptClick(User request);
    void onIgnoreClick(User request);
    void onOkayClick(Notification no);
    void onViewClick(User user);
}
