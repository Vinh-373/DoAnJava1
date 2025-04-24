package com.quanlybanlaptop.gui.mainView;

import java.sql.SQLException;

public interface ContentChangeListener {
    void onContentChange(String menuItem) throws SQLException;
}
